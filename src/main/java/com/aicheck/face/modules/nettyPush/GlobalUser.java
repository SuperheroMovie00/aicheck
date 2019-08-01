/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.nettyPush;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:39 AM 2019/1/23
 */
public class GlobalUser {

    /**
     *保存全局的 全部 连接上服务器的客户
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor
            .INSTANCE);
    /**
     * 保存全局 连接ip 及机器识别码
     */
    public static Map<String,String> map = new ConcurrentHashMap<String, String>();
}
