package cn.jeterlee.database;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import cn.jeterlee.greendao.gen.DaoMaster;
import cn.jeterlee.greendao.gen.DaoSession;

public class GlobalApp extends Application {

    public static GlobalApp instances;
    private DaoMaster.DevOpenHelper mHelper;
    private Database mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

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
        boolean encrypted = true; // 是否加密数据库（默认加密数据库）
        String password = "greendao"; // sql密码
        String name = encrypted ? "greendao-encrypt-db" : "greendao-db"; // 数据库名称

        /**
         * 通过 DaoMaster 的内部类 DevOpenHelper，可以获得 SQLiteOpenHelper 对象。
         * [注：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，这将导致数据的丢失。
         * 因此，在正式的项目中，还应做一层封装，来实现数据库的安全升级。]
         */
        mHelper = new DaoMaster.DevOpenHelper(this, name, null);
        mDatabase = encrypted ? mHelper.getEncryptedWritableDb(password) : mHelper.getWritableDb();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public Database getDatabase() {
        return mDatabase;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
