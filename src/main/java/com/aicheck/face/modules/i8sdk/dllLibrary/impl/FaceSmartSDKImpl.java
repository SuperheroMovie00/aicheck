/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.i8sdk.dllLibrary.impl;

import com.sun.jna.ptr.IntByReference;
import com.aicheck.face.modules.i8sdk.dllLibrary.FaceSmartSDK;

/**
 * @author  super
 * 聪明统计
 */
public class FaceSmartSDKImpl {

    public static IntByReference SmartCount(String dllPath, String pemPath, String ipStr, String portStr, String username, String password) {

        return FaceSmartSDK.INSTANCE.SmartCount(dllPath, pemPath, ipStr, portStr, username, password);
    }
}
