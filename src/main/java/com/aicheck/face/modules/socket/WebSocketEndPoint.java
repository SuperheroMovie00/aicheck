/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:52 AM 2018/12/27
 */
@Component
@Slf4j
public class WebSocketEndPoint extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static Map<WebSocketSession, String> sessionMap2 = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("客户端连接：" + session.getRemoteAddress());
        String path = session.getUri().getPath();

        String uuid = path.substring(path.lastIndexOf("/") + 1);

        sessionMap.put(uuid, session);
        sessionMap2.put(session, uuid);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        @SuppressWarnings("unused")
		String msg = message.getPayload();
        @SuppressWarnings("unused")
		String ipAddress = session.getRemoteAddress().toString();
//        JSONObject requestData = JSON.parseObject(msg);
//        Integer code = requestData.getInteger("code");
//        JSONObject result = new JSONObject();
//        String uuid	= sessionMap2.get(session);
//        result.put("code", 200);
//        result.put("uuid", uuid);
//        switch (code) {
//            case ReqCode.REQ_QR_CODE:
//                logger.info("WebSocketHandler:客户端{}发送消息{}...", ipAddress, msg);
//                if (session.isOpen())
//                    session.sendMessage(new TextMessage(result.toString()));
//                logger.info("WebSocketHandler:客户端{}发送消息{}完成", ipAddress, msg);
//                break;
//            default:
//                break;
//        }
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        @SuppressWarnings("unused")
		String ipAddress = session.getRemoteAddress().toString();
//        logger.info("WebSocketHandler:客户端{}下线", ipAddress);
//        logger.info("WebSocketHandler:删除客户端{}的session...", ipAddress);
//        logger.info("WebSocketHandler:删除sessionMap的客户端{}连接...", ipAddress);
        String uuid = sessionMap2.get(session);
        sessionMap.remove(uuid);
        sessionMap2.remove(session);
//        logger.info("WebSocketHandler:删除sessionMap的客户端{}连接完成", ipAddress);
//        logger.info("WebSocketHandler:删除WebSocket客户端{}连接...", ipAddress);
//		logger.info("{}", sessionMap);
        sessionMap.remove(session);
//		logger.info("{}", sessionMap);
//        logger.info("WebSocketHandler:删除WebSocket客户端{}连接完成", ipAddress);
//        logger.info("WebSocketHandler:删除客户端{}的session完成", ipAddress);
        if (session.isOpen())
            session.close();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        logger.info("WebSocketHandler:客户端{}异常", session.getRemoteAddress(), exception);
    }

    //发送消息
    public static void sendMessage(String msg, String uuid) throws Exception {
        WebSocketSession session = sessionMap.get(uuid);
        if (session == null) {
//            logger.info("app发送给PC的登录信息：{}参数不正确！",userInfo);
            log.info("uuid不存在");
        } else {
//            logger.info("app发送给PC的登录信息：{}",userInfo);
            session.sendMessage(new TextMessage(msg));
        }
    }

    public static void sendMessageAll(String msg) throws IOException {
        if (CollectionUtils.isEmpty(sessionMap)) {
            return;
        }
        for (Map.Entry<String, WebSocketSession> entry: sessionMap.entrySet()) {

            entry.getValue().sendMessage(new TextMessage(msg));

        }
    }
}
