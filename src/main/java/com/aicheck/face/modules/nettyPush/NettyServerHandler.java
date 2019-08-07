/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.nettyPush;

import com.aicheck.face.common.constant.AuthorizationEnum;
import com.aicheck.face.common.exception.FaceException;
import com.aicheck.face.common.utils.MD5Util;
import com.aicheck.face.common.utils.SpringContextUtils;
import com.aicheck.face.modules.customer.service.CustomerService;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.TodoPush;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.device.service.TodoPushService;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:35 AM 2019/1/23
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {
	
	@Autowired
	private DeviceService DeviceService;
	

	private WebSocketServerHandshaker handShaker;

	/**
	 * 连接上服务器
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.info("【handlerAdded】id====> {}", ctx.channel().id());
		log.info("【handlerAdded】remoteAddress====> {}", ctx.channel().remoteAddress());

		GlobalUser.channels.add(ctx.channel());
	}

	/**
	 * 断开连接
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.info("【handlerRemoved】====> {}", ctx.channel().id());
		GlobalUser.channels.remove(ctx);
	}

	/**
	 * 连接异常 需要关闭相关资源
	 * 
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("【系统异常】======> {}", cause.toString());
		ctx.close();
		ctx.channel().close();
	}

	/**
	 * 活跃的通道 也可以当作用户连接上客户端进行使用
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("【channelActive】=====> {}", ctx.channel());
	}

	/**
	 * 不活跃的通道 就说明用户失去连接
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	}

	/**
	 * 这里只要完成 flush
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 这里是保持服务器与客户端长连接 进行心跳检测 避免连接断开
	 * 
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent stateEvent = (IdleStateEvent) evt;
			PingWebSocketFrame ping = new PingWebSocketFrame();
			switch (stateEvent.state()) {
			// 读空闲（服务器端）
			case READER_IDLE:
				log.info("【" + ctx.channel().remoteAddress() + "】读空闲（服务器端）");
				ctx.writeAndFlush(ping);
				break;
			// 写空闲（客户端）
			case WRITER_IDLE:
				log.info("【" + ctx.channel().remoteAddress() + "】写空闲（客户端）");
				ctx.writeAndFlush(ping);
				break;
			case ALL_IDLE:
				log.info("【" + ctx.channel().remoteAddress() + "】读写空闲");
				break;
			}
		}
	}

	/**
	 * 收发消息处理
	 * 
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("messageReceived -> {}", msg);
		if (msg instanceof HttpRequest) {
			doHandlerHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			doHandlerWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	// 新增设备
	public void savedevice(Device device) {
		DeviceService.save(device);
	}

	/**
	 * wetsocket第一次连接握手
	 * 
	 * @param ctx
	 * @param req
	 */
	private void doHandlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// 如果HTTP解码失败，返回HHTP异常

		if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}

		String uri = req.getUri();
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
		Map<String, List<String>> parameters = queryStringDecoder.parameters();

		List<String> timestampList = parameters.get("timestamp");
		List<String> nonceList = parameters.get("nonce");
		List<String> signList = parameters.get("sign");
		List<String> platformList = parameters.get("platform");

		String timestamp = timestampList.get(0);

		// 请求时间间隔
		@SuppressWarnings("unused")
		long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);

		// if (requestInterval >=
		// Integer.parseInt(AuthorizationEnum.EXPIRE_TIME.getValue())) {
		//
		// throw new FaceException("请求超时，请重新请求", 400);
		// }

		String nonce = nonceList.get(0);
		String sign = signList.get(0);

		String platform = platformList.get(0);





		String str = "platform=" + platform + "&" + AuthorizationEnum.TOKEN.getValue() + timestamp + nonce;

		String md5Sign = MD5Util.getMD5(str);

		if (!md5Sign.equals(sign)) {
			GlobalUser.channels.remove(ctx.channel());

			throw new FaceException("签名错误", 400);
		}

		String s = uri.substring(uri.indexOf("/") + 1, uri.indexOf("?"));

		System.out.println("macdihi" + s);

		if (!StringUtils.isEmpty(s)) {
			// 存储设备序列号
			String ip = ctx.channel().remoteAddress().toString();
			GlobalUser.map.put(ip, uri);
			DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");
			// Device device = deviceService.findByMacAddress(s);
			Device device = deviceService.finddeviceforplatformandmacaddress(platform, s);
			if (device == null) {
				device = new Device();
			}
			device.setIpAddress(ip);
			device.setCreateTime(new Date());
			device.setMacAddress(s);
			device.setPlatform(platform);
			deviceService.save(device);
		}
		// 构造握手响应返回，本机测试
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://" + req.headers().get(HttpHeaders.Names.HOST) + uri, null, false);
		handShaker = wsFactory.newHandshaker(req);
		if (handShaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handShaker.handshake(ctx.channel(), req);
		}

		// 扫描待办信息
		log.info("连接设备s -> {}", s);
		//十秒钟同步一次替换
		/*if(false) {
		TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");

		List<TodoPush> todoPushList = todoPushService.findByDeviceId(s);
		if (!CollectionUtils.isEmpty(todoPushList)) {
			log.info("待发送消息设备 -> {},数量:{}", s, todoPushList.size());
			todoPushList.forEach(todoPush -> {
				ctx.channel().writeAndFlush(new TextWebSocketFrame(todoPush.getMessage()));

				todoPush.setStatus(1); // 标志已推送状态
				todoPushService.update(todoPush);

				log.info("待发送消息 id :{} ,发送成功", todoPush.getId());
			});
		}

		}*/
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * websocket消息处理
	 * 
	 * @param ctx
	 * @param msg
	 */
	private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
		// 判断msg 是哪一种类型 分别做出不同的反应
		if (msg instanceof CloseWebSocketFrame) {
			log.info("【关闭】");
			handShaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
			return;
		}
		if (msg instanceof PingWebSocketFrame) {
			log.info("【ping】");
			PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
			ctx.channel().writeAndFlush(pong);
			return;
		}
		if (msg instanceof PongWebSocketFrame) {
			log.info("【pong】");
			PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
			ctx.channel().writeAndFlush(ping);
			return;
		}
		if (!(msg instanceof TextWebSocketFrame)) {
			log.info("【不支持二进制】");
			throw new UnsupportedOperationException("不支持二进制");
		}

		if (msg instanceof TextWebSocketFrame) {

			log.info("socket消息返回******");
			TodoPushService todopushservice = (TodoPushService) SpringContextUtils.getBean("todoPushService");
			String text = ((TextWebSocketFrame) msg).text();

			JSONObject jsonObject = JSONObject.fromObject(text);
			socketreturn stu = (socketreturn) JSONObject.toBean(jsonObject, socketreturn.class);
			if(stu!=  null){
				log.info("socket消息转化对象为******"+stu.toString());
			}
			TodoPush todoPush = todopushservice.findById(stu.getId());

			if (todoPush !=  null) {
				log.info("socket消息返回有效******");
				switch (todoPush.getType()){

					case 4:
						log.info("socket消息返回有效******执	行删除（todopush）");
						updatetodupushbytype4(stu,todoPush);
						log.info("socket消息返回有效******执行删除（todopush）完成");
						break;
					case 8:
						log.info("socket消息返回有效******执行查詢人臉处理（todopush）");
						updatetodupushbytype3(stu,todoPush);
						log.info("socket消息返回有效******执行查詢人臉处理（todopush）完成");
						break;
					case 3:
						log.info("socket消息返回有效******执行修改处理（todopush）");
						updatetodupushbytype4(stu,todoPush);
						log.info("socket消息返回有效******执行修改处理（todopush）完成");
						break;
					case 2:
						log.info("socket消息返回有效******执行新增处理（todopush）");
						updatetodupushbytype2(stu,todoPush);
						log.info("socket消息返回有效******执行新增处理（todopush）完成");
						break;
						default:
				}
			}
			if ("heartbeat".equals(text)) {
				ctx.channel().writeAndFlush(new TextWebSocketFrame(text));
			}
		}

		log.info("接收消息:{} ", new TextWebSocketFrame(((TextWebSocketFrame) msg).text()).text());
		// //可以对消息进行处理
		// //群发
		// for (Channel channel : GlobalUser.channels) {
		// channel.writeAndFlush(new TextWebSocketFrame(((TextWebSocketFrame)
		// msg).text()));
		// }

	}

	/**
	 * 修改todupush
	 * 
	 * @param sr
	 */
	public static void updatetodupushbytype3(socketreturn sr,TodoPush todopush) {
		TodoPushService todopushservice = (TodoPushService) SpringContextUtils.getBean("todoPushService");
		todopush.setStatus(1);
		todopush.setResult_time(new Date());
		if (sr.getStatus().equals("success")) {
			todopush.setResult(sr.getUserId());
		}
		if (sr.getStatus().equals("fail")) {
			todopush.setResult("noface");
		}
		todopushservice.update(todopush);
	}

	public static void updatetodupushbytype4(socketreturn sr,TodoPush todopush) {
		TodoPushService todopushservice = (TodoPushService) SpringContextUtils.getBean("todoPushService");

		todopush.setStatus(1);
		todopush.setResult_time(new Date());
		if (sr.getStatus().equals("success")) {
			todopush.setResult("success");
		}
		if (sr.getStatus().equals("fail")) {
			todopush.setResult("fail");
		}
		todopushservice.update(todopush);
	}

	public static void updatetodupushbytype2(socketreturn sr,TodoPush todopush) {
		TodoPushService todopushservice = (TodoPushService) SpringContextUtils.getBean("todoPushService");
		todopush.setResult_time(new Date());
		if (sr.getStatus().equals("success")) {
			todopush.setStatus(1);
			todopush.setResult("success");
		}else {
			todopush.setStatus(2);
			todopush.setResult("fail");
		}
		todopushservice.update(todopush);
	}
}
