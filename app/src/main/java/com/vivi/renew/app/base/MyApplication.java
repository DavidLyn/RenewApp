package com.vivi.renew.app.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vivi.renew.app.entity.DaoMaster;
import com.vivi.renew.app.entity.DaoSession;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lvweiwei on 18/5/6.
 * 为随时获得context提供支持
 * usage:
 * 在AndroidManifest.xml的<application>中添加
 * android:name=".base.MyApplication"
 *
 * 添加对DaoSession的支持
 */

public class MyApplication extends Application {
    private static Context context;

    private static MyApplication instance;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 设置全局context
        context = getApplicationContext();

        // 设置全局DaoSession
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"renew-db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();

        // 初始化OkHttpClient

        // 设置可访问所有的https网站
        //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,null,null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                //.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        // 初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}