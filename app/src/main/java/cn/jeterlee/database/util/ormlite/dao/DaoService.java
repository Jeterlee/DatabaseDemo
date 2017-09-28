package cn.jeterlee.database.util.ormlite.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.jeterlee.database.util.ormlite.service.DataBaseHelper;

/**
 * 使用ORMLite实现sqlite数据访问
 *
 * @author Jeterlee
 */
public class DaoService<T, Z> implements IDao<T, Z> {
    private Context mContext;
    private Class<T> clazz;
    private Map<Class<T>, Dao<T, Z>> mDaoMap = new HashMap<>();// 缓存泛型Dao

    public DaoService(Context context, Class<T> clazz) {
        this.clazz = clazz;
        this.mContext = context;
    }

    /**
     * 连接上下文
     *
     * @return
     * @throws SQLException 抛出异常
     */
    public Dao<T, Z> getDao() throws SQLException {
        Dao<T, Z> dao = mDaoMap.get(clazz);
        if (null == dao) {
            dao = DataBaseHelper.getInstance(mContext).getDao(clazz);
            mDaoMap.put(clazz, dao);
        }
        return dao;
    }

    /**
     * 关闭所有操作
     */
    public void close() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        dataBaseHelper.close();
    }

    /**
     * 增,带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    @Override
    public int save(T t) throws SQLException {
        Dao<T, Z> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);// 开启事务
            int save = dao.create(t);// 创建实体
            dao.commit(databaseConnection);// 提交事务
            return save;
        } catch (Exception e) {
            dao.rollBack(databaseConnection);// 异常回滚,即不更新
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删除，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    @Override
    public int delete(T t) throws SQLException {
        Dao<T, Z> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(t);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 改,带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    @Override
    public int update(T t) throws SQLException {
        Dao<T, Z> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int update = dao.update(t);
            dao.commit(databaseConnection);
            return update;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 查,带事务操作
     *
     * @param z 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    @Override
    public T queryById(Z z) throws SQLException {
        Dao<T, Z> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            T t = dao.queryForId(z);
            dao.commit(databaseConnection);
            return t;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }
}
