package com.hyper.http;

import android.text.TextUtils;

import com.hyper.http.core.HttpTask;
import com.hyper.http.core.callback.OnHttpCallback;
import com.hyper.http.core.callback.OnStringCallback;
import com.hyper.logger.JJLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * author xander on  2017/5/31.
 * function 处理字符串
 */

public class HttpString extends HttpTask<HttpString,String> {
    private  String mCharset = "utf-8";
    @SuppressWarnings("unchecked")
    public HttpString setCharset(String  charset){
        mCharset  = charset;
        return   this;
    }

    public HttpString setStringCallback(final OnStringCallback stringCallback){
        setOnHttpCallback(new OnHttpCallback<String>() {
            @Override
            public String onChildThread(InputStream inputStream) {
                InputStreamReader isr;
                if (inputStream == null) {
                    return null;
                }
                try {
                    isr = new InputStreamReader(inputStream, mCharset);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        if(interceptFlag ){
                            //TODO need to test
                            JJLogger.logInfo("interceptFlag","requestURL:" +mUrl +"canceled");
                            //跳出任务
                            return null;
                        }
                        stringBuilder.append(line).append("\n");
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
            @Override
            public void onUISuccess(String s) {
                if (!TextUtils.isEmpty(s)) {
                    stringCallback.onStringSuccess(s);
                }else {

                    stringCallback.onRequestFailure(new NullPointerException(""), "");
                    JJLogger.logInfo("onUISuccess","HttpString:onUISuccess :"+"没有得到数据 :" +"");
                }
            }
            @Override
            public void onFailure(Exception e, String errorCode) {
                stringCallback.onRequestFailure(e, errorCode);
                JJLogger.logInfo("onFailure","HttpString:onFailure :请求的url 为："+mUrl);
            }
        }).startConcurrence();
        return this;
    }
}
