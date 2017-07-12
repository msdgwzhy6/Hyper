package com.dragon.hyper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdk.core.InitSDK;
import com.sdk.core.ad.callback.OnAdManagerCallback;
import com.sdk.core.ad.callback.OnShowAdDetailCallback;
import com.sdk.core.ad.exception.AdException;
import com.sdk.core.ad.splash.manager.AdManagerSplash;
import com.sdk.util.logger.JJLogger;

import java.io.IOException;

import static com.sdk.core.ad.common.Config.POS_SPLASH;

public class SplashActivity extends AppCompatActivity implements OnAdManagerCallback,OnShowAdDetailCallback{

    private RelativeLayout mRelativeLayout;//广告位
    private AdManagerSplash mAdManagerSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.ad_container);
        try {
            InitSDK.init(this);
            JJLogger.debug(true);
        } catch (AdException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mAdManagerSplash = AdManagerSplash.getManagerSplash().setOnAdManagerCallback(this).checkLocalAd(POS_SPLASH);
        } catch (AdException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdExist() {
        try {
            mAdManagerSplash.loadAd(mRelativeLayout).setOnShowAdDetailCallback(this);
        } catch (AdException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdNotExist(String s) {
        startActivity(new Intent(this,MainActivity.class));

        finish();
    }

    @Override
    public void onShowAdDetail(WebView webView) {
        Toast.makeText(this, "没做跳转", Toast.LENGTH_SHORT).show();
        Log.i(this.getClass().getSimpleName(),"跳转链接 ：" + webView.getUrl());
        Log.v(this.getClass().getSimpleName(),"跳转链接 ：" + webView.getUrl());
        Log.d(this.getClass().getSimpleName(),"跳转链接 ：" + webView.getUrl());
        Log.e(this.getClass().getSimpleName(),"跳转链接 ：" + webView.getUrl());
        Log.w(this.getClass().getSimpleName(),"跳转链接 ：" + webView.getUrl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAdManagerSplash.release();
    }
}
