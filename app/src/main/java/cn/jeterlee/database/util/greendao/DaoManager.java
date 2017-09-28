package cn.jeterlee.database.util.greendao;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.jeterlee.database.config.Constants;
import cn.jeterlee.greendao.gen.DaoMaster;
import cn.jeterlee.greendao.gen.DaoSession;

/**
 * 1、创建数据库
 * 2、创建数据库表
 * 3、包含对数据库的CRUD
 * 4、对数据库的升级
 * 5、可选择加密数据库
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static volatile DaoManager manager;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;
    private boolean encrypted = true;// 是否加密数据库（默认加密数据库）
    private String defPassword = "greendao";// 默认的sql密码
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
            helper = new DaoMaster.DevOpenHelper(mContext, isEncrypted() ? Constants.GREENDAO_ENCRYPT_DB : Constants.GREENDAO_DB, null);
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            daoMaster = new DaoMaster(isEncrypted() ? helper.getEncryptedWritableDb(getDefPassword()) : helper.getWritableDb());
        }
        return daoMaster;
    }

    /**
     * 数据处理
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
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
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
     * 关闭session
     */
    public void closeSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getDefPassword() {
        return defPassword;
    }

    public void setDefPassword(String defPassword) {
        this.defPassword = defPassword;
    }
}
