package cn.jeterlee.database;

import android.app.Application;

import cn.jeterlee.database.util.greendao.DaoManager;

public class GlobalApp extends Application {

    public static GlobalApp instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;

        setGreenDao();
    }

    public static GlobalApp getInstances() {
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setGreenDao() {
        DaoManager.getInstance(this);
    }
}
