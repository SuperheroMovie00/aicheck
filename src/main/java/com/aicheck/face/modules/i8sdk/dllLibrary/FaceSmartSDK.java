/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.i8sdk.dllLibrary;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public interface FaceSmartSDK extends Library {

    FaceSmartSDK INSTANCE = (FaceSmartSDK)  Native.loadLibrary(System.getProperty("user.dir") + "\\src\\main\\resources\\dll\\SmartCount",
            FaceSmartSDK.class);
    String Test();

    IntByReference SmartCount(String dllPath, String pemPath,String ipStr,String portStr,String username,String password);

}
