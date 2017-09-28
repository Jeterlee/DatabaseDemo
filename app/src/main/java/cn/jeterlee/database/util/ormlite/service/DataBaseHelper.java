package cn.jeterlee.database.util.ormlite.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import cn.jeterlee.database.config.Constants;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    /* 类没有实例化，是不能用作父类构造器的参数，必须声明为静态 */
    private static final String TABLE_NAME = Constants.ORMLITE_DB;// 数据库名称，创建数据以.db结尾
    private static final CursorFactory factory = null;// 指定在执行查询时获得一个游标实例的工厂类，设置为null，代表使用系统默认的工厂类
    private static final int VERSION_CODE = 1;// 数据库版本号

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, factory, VERSION_CODE);// 位置://<包>/databases/
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        // 完成对数据库的创建,以及表的建立
        // try {
        //     // 按照预定义属性创建数据库,并关联到相应的类上
        //     TableUtils.createTableIfNotExists(connectionSource, );
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // 完成对数据库的更新
        // try {
        //     TableUtils.dropTable(connectionSource, , true);
        //     onCreate(sqLiteDatabase, connectionSource);
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

    /**
     * 封装
     */
    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getInstance(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DataBaseHelper.class) {
                if (instance == null)
                    instance = new DataBaseHelper(context);
            }
        }
        return instance;
    }

    /**
     * 获得数据库的访问对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = DaoManager.createDao(getConnectionSource(), clazz);
        dao.setObjectCache(true);
        return (D) dao;
    }

    /**
     * Dao异常捕获
     *
     * @param clazz 类
     * @return 返回捕获Dao的异常
     */
    public synchronized <T> RuntimeExceptionDao<T, ?> getExceptionDao(Class<T> clazz) {
        RuntimeExceptionDao<T, ?> exceptionDao = null;
        try {
            exceptionDao = RuntimeExceptionDao.createDao(getConnectionSource(), clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exceptionDao;
    }

    /**
     * 关闭所有操作
     */
    @Override
    public void close() {
        super.close();
        DaoManager.clearCache();
        connectionSource.close();
        OpenHelperManager.releaseHelper();
    }
}
