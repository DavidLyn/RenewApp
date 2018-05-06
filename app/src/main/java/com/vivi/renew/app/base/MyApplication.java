package com.vivi.renew.app.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vivi.renew.app.entity.DaoMaster;
import com.vivi.renew.app.entity.DaoSession;

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
        context = getApplicationContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"renew-db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
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