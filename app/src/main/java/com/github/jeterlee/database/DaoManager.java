package com.github.jeterlee.database;

import android.content.Context;

import com.github.jeterlee.greendao.gen.DaoMaster;
import com.github.jeterlee.greendao.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * <pre>
 * Title: DaoManager
 * Description:
 * 1、创建数据库
 * 2、创建数据库表
 * 3、包含对数据库的 CRUD
 * 4、对数据库的升级
 * 5、可选择加密数据库
 * </pre>
 *
 * @author <a href="https://www.github.com/jeterlee"></a>
 * @date 2019/3/8 0008
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String GREENDAO_DB = "greendao-db";
    private static final String GREENDAO_ENCRYPT_DB = "greendao-encrypt-db";
    private static volatile DaoManager manager;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;
    /**
     * 是否加密数据库（默认不加密数据库，即不引入加密包；加密数据库，引入加密包即可）
     */
    private boolean encrypted = false;
    /**
     * 默认的 sql 密码
     */
    private String defPassword = "greendao";
    private Context mContext;

    private DaoManager(Context context) {
        this.mContext = context;
    }

    public static DaoManager getInstance(Context context) {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager(context);
                }
            }
        }
        return manager;
    }

    public void init() {
        getDaoSession();
    }

    private static DaoMaster daoMaster;

    /**
     * 判断是否存在数据库，没有就创建
     */
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            /**
             * 通过 DaoMaster 的内部类 DevOpenHelper，可以获得 SQLiteOpenHelper 对象。
             * [注：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，这将导致数据的丢失。
             * 因此，在正式的项目中，还应做一层封装，来实现数据库的安全升级。]
             */
            helper = new DaoMaster.DevOpenHelper(mContext, isEncrypted() ? GREENDAO_ENCRYPT_DB : GREENDAO_DB, null);
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            daoMaster = new DaoMaster(isEncrypted() ? helper.getEncryptedWritableDb(getDefPassword()) : helper.getWritableDb());
        }
        return daoMaster;
    }

    /**
     * 数据处理
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 输出日志
     *
     * @param debug true 打开，false 关闭
     * @return this
     */
    public DaoManager setDebug(boolean debug) {
        QueryBuilder.LOG_SQL = debug;
        QueryBuilder.LOG_VALUES = debug;
        return this;
    }

    public void close() {
        closeHelper();
        closeSession();
    }

    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    /**
     * 关闭 session
     */
    public void closeSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    /**
     * 是否加密（默认不加密数据库，即不引入加密包；加密数据库，引入加密包即可）
     * implementation 'net.zetetic:android-database-sqlcipher:3.5.6'
     *
     * @return
     */
    public boolean isEncrypted() {
        try {
            Class.forName("net.zetetic:android-database-sqlcipher");
            encrypted = true;
        } catch (ClassNotFoundException e) {
            encrypted = false;
        }
        return encrypted;
    }

    // public void setEncrypted(boolean encrypted) {
    //     this.encrypted = encrypted;
    // }

    public String getDefPassword() {
        return defPassword;
    }

    public void setDefPassword(String defPassword) {
        this.defPassword = defPassword;
    }
}
