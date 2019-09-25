package com.netsdk.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.netsdk.demo.util.CaseMenu;
import com.netsdk.lib.NetSDKLib;
import com.netsdk.lib.NetSDKLib.*;
import com.netsdk.lib.ToolKits;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class VideoStatistic {
	static NetSDKLib netsdkApi = NetSDKLib.NETSDK_INSTANCE;
	static NetSDKLib configApi = NetSDKLib.CONFIG_INSTANCE;

	private NET_DEVICEINFO_Ex deviceInfo = new NET_DEVICEINFO_Ex();
	private static LLong loginHandle = new LLong(0);

	String address = "192.168.1.108";
	int port = 37777;
	String username = "admin";
	String password = "admin123456";

	private static class DisconnectCallback implements fDisConnect {
		private static DisconnectCallback instance = new DisconnectCallback();

		private DisconnectCallback() {
		}

		public static DisconnectCallback getInstance() {
			return instance;
		}

		public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s:%d] Disconnect!\n", pchDVRIP, nDVRPort);
		}
	}

	private static class HaveReconnectCallback implements fHaveReConnect {
		private static HaveReconnectCallback instance = new HaveReconnectCallback();

		private HaveReconnectCallback() {
		}

		public static HaveReconnectCallback getInstance() {
			return instance;
		}

		public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s:%d] HaveReconnected!\n", pchDVRIP, nDVRPort);
		}
	}

	private static class VideoStatSumCallback implements fVideoStatSumCallBack {
		private static VideoStatSumCallback instance = new VideoStatSumCallback();

		private VideoStatSumCallback() {
		}

		public static VideoStatSumCallback getInstance() {
			return instance;
		}

		public void invoke(LLong lAttachHandle, NET_VIDEOSTAT_SUMMARY stVideoState, int dwBufLen, Pointer dwUser) {

			System.out.println("people in" + stVideoState.stuEnteredSubtotal.nTotal);
			System.out.println("people out" + stVideoState.stuExitedSubtotal.nTotal);

			System.out.printf(
					"Channel[%d] GetTime[%s]\n" + "People In  Information[Total[%d] Hour[%d] Today[%d]]\n"
							+ "People Out Information[Total[%d] Hour[%d] Today[%d]]\n",
					stVideoState.nChannelID, stVideoState.stuTime.toStringTime(),
					stVideoState.stuEnteredSubtotal.nToday, stVideoState.stuEnteredSubtotal.nHour,
					stVideoState.stuEnteredSubtotal.nTotal, stVideoState.stuExitedSubtotal.nToday,
					stVideoState.stuExitedSubtotal.nHour, stVideoState.stuExitedSubtotal.nTotal);
		}
	}

	public void EndTest() {
		System.out.println("End Detest");
		if (loginHandle.longValue() != 0) {
			netsdkApi.CLIENT_Logout(loginHandle);
		}
		System.out.println("See You...");

		//netsdkApi.CLIENT_Cleanup();
		//System.exit(0);             //将程序停止
	}

	public void InitTest() {
		// 初始化SDK库
		netsdkApi.CLIENT_Init(DisconnectCallback.getInstance(), null);

		// 设置断线自动重练功能
		netsdkApi.CLIENT_SetAutoReconnect(HaveReconnectCallback.getInstance(), null);

		// 向设备登入
		/// login device by TCP
		final int nSpecCap = 0;
		final IntByReference error = new IntByReference();

		loginHandle = netsdkApi.CLIENT_LoginEx2(address, (short) port, username, password, nSpecCap, null, deviceInfo,
				error);

		if (loginHandle.longValue() == 0) {
			System.err.printf("Login Device [%s:%d] Failed ! Last Error[%x]\n", address, port,
					netsdkApi.CLIENT_GetLastError());
			return;
		}
		
		System.out.printf("Login Device [%s:%d] Success. \n", address, port);
	}

	/** 订阅视频统计 句柄 */
	private static LLong videoStatHandle = new LLong(0);

	/**
	 * 订阅
	 */
	public void attachVideoStatSummary() {
		if (loginHandle.longValue() == 0) {
			return;
		}

		NET_IN_ATTACH_VIDEOSTAT_SUM videoStatIn = new NET_IN_ATTACH_VIDEOSTAT_SUM();
		videoStatIn.nChannel = 0;
		videoStatIn.cbVideoStatSum = VideoStatSumCallback.getInstance();

		NET_OUT_ATTACH_VIDEOSTAT_SUM videoStatOut = new NET_OUT_ATTACH_VIDEOSTAT_SUM();

		videoStatHandle = netsdkApi.CLIENT_AttachVideoStatSummary(loginHandle, videoStatIn, videoStatOut, 5000);
		if (videoStatHandle.longValue() == 0) {
			System.err.printf("Attach Failed!LastError = %x\n", netsdkApi.CLIENT_GetLastError());
			return;
		}

		System.out.printf("Attach Success!Wait Device Notify Information\n");
	}

	/**
	 * 退订
	 */
	public void detachVideoStatSummary() {
		if (videoStatHandle.longValue() != 0) {
			netsdkApi.CLIENT_DetachVideoStatSummary(videoStatHandle);
			videoStatHandle.setValue(0);
		}
	}

	// 查询句柄
	private static LLong findHandle = new LLong(0);

	/**
	 * 重写的 startFindNumberStat 方法
	 * @param begintime
	 * @param endtime
	 */
	public static Map<String, Integer> startFindNumberStatrewrite(Date begintime, Date endtime,int GranularityType) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		
		NET_IN_FINDNUMBERSTAT inParam = new NET_IN_FINDNUMBERSTAT();
		// 通道号
		inParam.nChannelID = 0;

		Calendar begincalendar = Calendar.getInstance();
		begincalendar.setTime(begintime);
		
		
/*		SystemParameter.out.println("开始"+begincalendar.get(Calendar.YEAR));
		SystemParameter.out.println("开始"+begincalendar.get(Calendar.MONTH));
		SystemParameter.out.println("开始"+begincalendar.get(Calendar.DATE));
		SystemParameter.out.println("开始"+begincalendar.get(Calendar.HOUR)); */
	
		
		// 开始时间
		
		int MONTH =begincalendar.get(Calendar.MONTH);  //获取的月份是从0开始 所以需要+1
		inParam.stStartTime.dwYear = begincalendar.get(Calendar.YEAR);
		inParam.stStartTime.dwMonth =MONTH+1;
		inParam.stStartTime.dwDay = begincalendar.get(Calendar.DATE);
		inParam.stStartTime.dwHour = begincalendar.get(Calendar.HOUR_OF_DAY);

		Calendar endcalendar = Calendar.getInstance();
		endcalendar.setTime(endtime);
		
/*		SystemParameter.out.println("结束"+endcalendar.get(Calendar.YEAR));
		SystemParameter.out.println("结束"+endcalendar.get(Calendar.MONTH));
		SystemParameter.out.println("结束"+endcalendar.get(Calendar.DATE));
		SystemParameter.out.println("结束"+endcalendar.get(Calendar.HOUR));*/
		
		
		// 结束时间
		int ENDMONTH= endcalendar.get(Calendar.MONTH);
		inParam.stEndTime.dwYear = endcalendar.get(Calendar.YEAR);
		inParam.stEndTime.dwMonth = ENDMONTH+1;
		inParam.stEndTime.dwDay = endcalendar.get(Calendar.DATE);
		inParam.stEndTime.dwHour = endcalendar.get(Calendar.HOUR_OF_DAY);

		// 颗粒度
		inParam.nGranularityType = GranularityType;
		// 接口超时时间5s
		inParam.nWaittime = 5000;

		NET_OUT_FINDNUMBERSTAT outParam = new NET_OUT_FINDNUMBERSTAT();

		/// 获取查询句柄
		findHandle = netsdkApi.CLIENT_StartFindNumberStat(loginHandle, inParam, outParam);
		if (findHandle.longValue() == 0) {
			System.err.printf("StartFindNumberStat Failed! LastError = 0x%x\n", netsdkApi.CLIENT_GetLastError());
			return null;
		}

		System.out.println("dwTotalCount: " + outParam.dwTotalCount);
		/// 查询
		NET_IN_DOFINDNUMBERSTAT inDoFind = new NET_IN_DOFINDNUMBERSTAT();
		// 从0开始查询
		inDoFind.nBeginNumber = 0;
		// 每次查询10条
		inDoFind.nCount = 10;
		// 接口超时时间 5s
		inDoFind.nWaittime = 5000;

		NET_OUT_DOFINDNUMBERSTAT outDofind = new NET_OUT_DOFINDNUMBERSTAT();
		NET_NUMBERSTAT[] numberstat = (NET_NUMBERSTAT[]) new NET_NUMBERSTAT().toArray(inDoFind.nCount);
		outDofind.nBufferLen = inDoFind.nCount * numberstat[0].dwSize;
		outDofind.pstuNumberStat = new Memory(outDofind.nBufferLen);
		outDofind.pstuNumberStat.clear(outDofind.nBufferLen);
		// Note： 初始化内存中 dwSize 字段的值，否则查询的结果为空	
		ToolKits.SetStructArrToPointerData(numberstat, outDofind.pstuNumberStat);

		int bFound = 0;
		int index = 1;
		do {
			bFound = netsdkApi.CLIENT_DoFindNumberStat(findHandle, inDoFind, outDofind);
			if (0 == bFound) {
				System.err.printf("DoFindNumberStat Failed! LastError = 0x%x\n", netsdkApi.CLIENT_GetLastError());
				return null;
			}
			
			int Enterpeoplenum = 0;
			int Exitpeoplenum = 0;
			// 查询结果
			ToolKits.GetPointerDataToStructArr(outDofind.pstuNumberStat, numberstat);
			for (int i = 0; i < outDofind.nCount; i++, index++) {
				System.out.println("index = " + index + " Start Time: " + numberstat[i].stuStartTime.toStringTime()
						+ " Stop Time: " + numberstat[i].stuEndTime.toStringTime() + " Enter Total: "
						+ numberstat[i].nEnteredSubTotal + " Exit Total: " + numberstat[i].nExitedSubtotal + "\n");

				Enterpeoplenum = numberstat[i].nEnteredSubTotal;
				Exitpeoplenum = numberstat[i].nExitedSubtotal;
																
			}
			map.put("IN", Enterpeoplenum);
			map.put("OUT", Exitpeoplenum);
			System.out.println("进入的人数" + Enterpeoplenum);
			System.out.println("出去的人数" + Exitpeoplenum);
			System.out.println("map=>"+map);

			// 查询下一次
			// 从上一次结束地方开始查询
			inDoFind.nBeginNumber	 += inDoFind.nCount;
		} while (outDofind.nCount >= inDoFind.nCount);
		return map;
	}

	public void startFindNumberStat() {
		NET_IN_FINDNUMBERSTAT inParam = new NET_IN_FINDNUMBERSTAT();
		
		// 通道号
		inParam.nChannelID = 0;

		// 开始时间
		inParam.stStartTime.dwYear = 2019;
		inParam.stStartTime.dwMonth = 9;
		inParam.stStartTime.dwDay = 5;
		inParam.stStartTime.dwHour = 20;

		// 结束时间
		inParam.stEndTime.dwYear = 2019;
		inParam.stEndTime.dwMonth = 9;
		inParam.stEndTime.dwDay = 5;
		inParam.stEndTime.dwHour = 21;

		// 颗粒度
		inParam.nGranularityType = 1;
		// 接口超时时间5s
		inParam.nWaittime = 5000;
		
		NET_OUT_FINDNUMBERSTAT outParam = new NET_OUT_FINDNUMBERSTAT();
		
		/// 获取查询句柄
		findHandle = netsdkApi.CLIENT_StartFindNumberStat(loginHandle, inParam, outParam);
		if (findHandle.longValue() == 0) {
			System.err.printf("StartFindNumberStat Failed! LastError = 0x%x\n", netsdkApi.CLIENT_GetLastError());
			return;
		}

		System.out.println("dwTotalCount: " + outParam.dwTotalCount);
		/// 查询
		NET_IN_DOFINDNUMBERSTAT inDoFind = new NET_IN_DOFINDNUMBERSTAT();
		// 从0开始查询
		inDoFind.nBeginNumber = 0;
		// 每次查询10条
		inDoFind.nCount = 10;
		// 接口超时时间 5s
		inDoFind.nWaittime = 5000;

		NET_OUT_DOFINDNUMBERSTAT outDofind = new NET_OUT_DOFINDNUMBERSTAT();
		NET_NUMBERSTAT[] numberstat = (NET_NUMBERSTAT[]) new NET_NUMBERSTAT().toArray(inDoFind.nCount);
		outDofind.nBufferLen = inDoFind.nCount * numberstat[0].dwSize;
		outDofind.pstuNumberStat = new Memory(outDofind.nBufferLen);
		outDofind.pstuNumberStat.clear(outDofind.nBufferLen);
		// Note： 初始化内存中 dwSize 字段的值，否则查询的结果为空
		ToolKits.SetStructArrToPointerData(numberstat, outDofind.pstuNumberStat);

		int bFound = 0;
		int index = 1;
		do {
			bFound = netsdkApi.CLIENT_DoFindNumberStat(findHandle, inDoFind, outDofind);
			if (0 == bFound) {
				System.err.printf("DoFindNumberStat Failed! LastError = 0x%x\n", netsdkApi.CLIENT_GetLastError());
				return;
			}

			int Enterpeoplenum = 0;
			int Exitpeoplenum = 0;
			// 查询结果
			ToolKits.GetPointerDataToStructArr(outDofind.pstuNumberStat, numberstat);
			for (int i = 0; i < outDofind.nCount; i++, index++) {
				System.out.println("index = " + index + " Start Time: " + numberstat[i].stuStartTime.toStringTime()
						+ " Stop Time: " + numberstat[i].stuEndTime.toStringTime() + " Enter Total: "
						+ numberstat[i].nEnteredSubTotal + " Exit Total: " + numberstat[i].nExitedSubtotal + "\n");

				Enterpeoplenum = numberstat[i].nEnteredSubTotal;
				Exitpeoplenum = numberstat[i].nExitedSubtotal;

			}
			System.out.println("进入的人数" + Enterpeoplenum);
			System.out.println("出去的人数" + Exitpeoplenum);

			// 查询下一次
			// 从上一次结束地方开始查询
			inDoFind.nBeginNumber += inDoFind.nCount;
		} while (outDofind.nCount >= inDoFind.nCount);
	}

	public void stopFindNumberStat() {
		if (findHandle.longValue() != 0) {
			netsdkApi.CLIENT_StopFindNumberStat(findHandle);
			System.out.println("stop Find Number Stat Success. ");
			findHandle.setValue(0);
		}
	}

	public void RunTest() {
		System.out.println("Run Detest");

		CaseMenu menu = new CaseMenu();

		menu.addItem(new CaseMenu.Item(this, "订阅", "attachVideoStatSummary"));
		menu.addItem(new CaseMenu.Item(this, "退订", "detachVideoStatSummary"));
		menu.addItem(new CaseMenu.Item(this, "开始查询", "startFindNumberStat"));
		menu.addItem(new CaseMenu.Item(this, "停止查询", "stopFindNumberStat"));

		menu.run();
	}

	public static void main(String[] args) {

		VideoStatistic demo = new VideoStatistic();
		demo.InitTest();
		demo.RunTest();
		demo.EndTest();

	}

}
