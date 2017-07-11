package com.hyper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hyper.logger.JJLogger;

import java.io.IOException;


/**

 */

public final class InitSDK {

    /**
     * mContext 上下文
     */
    @SuppressLint("StaticFieldLeak")

    private static Context mContext;
    /**
     * 初始化工具类
     * 必须在application中初始化
     * @param context   上下文
     * @throws IOException
     */
    public static void init(Context context)  {
        if (context == null) {
            JJLogger.logErrorCode("请检查 确保 init(Context context) 参数不为空!!!","");
        }
        InitSDK.mContext = context.getApplicationContext();

    }

    /**
     * 获取Context
     */
    public static Context getContext() {
        return mContext;
    }
}
