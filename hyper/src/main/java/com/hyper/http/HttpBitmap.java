package com.hyper.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hyper.http.core.HttpTask;
import com.hyper.http.core.callback.OnBitmapCallback;
import com.hyper.http.core.callback.OnHttpCallback;

import java.io.InputStream;

/**
 * author xander on  2017/5/31.
 * function 处理图片
 */

public class HttpBitmap extends HttpTask<HttpBitmap,Bitmap> {
    @SuppressWarnings("unchecked")
    public HttpBitmap setBitmapCallback(final OnBitmapCallback bitmapCallback){
        setOnHttpCallback(new OnHttpCallback<Bitmap>() {
            @Override
            public Bitmap onChildThread(InputStream inputStream) {
                if (!interceptFlag) {
                    return BitmapFactory.decodeStream(inputStream);
                }else {
                    return null;
                }
            }

            @Override
            public void onUISuccess(Bitmap bitmap) {
                if (interceptFlag) {
                    return;
                }
                if (bitmap != null) {
                    bitmapCallback.onBitmapSuccess(bitmap);
                }else {
                    bitmapCallback.onBitmapFailure(new NullPointerException("bitmap 为空"), "");
                }
            }

            @Override
            public void onFailure(Exception e, String errorCode) {
                /*
                * 如果获取失败
                * */
                bitmapCallback.onBitmapFailure(e, errorCode);
            }
        }).startConcurrence();
        return this;
    }
}
