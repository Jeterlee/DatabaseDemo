package com.github.jeterlee.database;

import android.app.Application;

public class MyApplication extends Application {

    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;

        setGreenDao();
    }

    public static MyApplication getInstances() {
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setGreenDao() {
        DaoManager.getInstance(this).setDebug(true).init();
    }
}
