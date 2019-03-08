package com.github.jeterlee.database;

import android.content.Context;

import com.github.jeterlee.greendao.gen.DaoMaster;
import com.github.jeterlee.greendao.gen.DaoSession;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * <pre>
 * Title: DaoHelper
 * Description: 对数据库操作 增、删、改、查
 * </pre>
 *
 * @author <a href="https://www.github.com/jeterlee"></a>
 * @date 2019/3/8 0008
 */
public class DaoHelper<T> {
    private DaoManager manager;
    private Class<T> clazz;

    /**
     * 实例化
     *
     * @param context 上下稳
     * @param clazz   要操作的类
     */
    public DaoHelper(Context context, Class<T> clazz) {
        manager = DaoManager.getInstance(context);
        this.clazz = clazz;
    }

    /**
     * 直接使用，会报下面错误：
     * java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
     *
     * @return Class
     */
    private Class<T> getClazz() {
        if (clazz == null) {
            // 获取泛型的 Class 对象
            // noinspection unchecked
            clazz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0]));
        }
        return clazz;
    }

    /**
     * 插入增加
     *
     * @param t 泛型对象
     * @return 插入的结果
     */
    public boolean insert(T t) {
        try {
            return manager.getDaoSession().insert(t) != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 插入集合
     *
     * @param datas 泛型集合
     * @return 插入的结果
     */
    public boolean insertList(final List<T> datas) {
        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T t : datas) {
                        manager.getDaoSession().insertOrReplace(t);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param t 泛型对象
     * @return 删除的结果
     */
    public boolean delete(T t) {
        try {
            manager.getDaoSession().delete(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有
     *
     * @return 删除的结果
     */
    public boolean deleteAll() {
        try {
            manager.getDaoSession().deleteAll(clazz);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 列出所有
     *
     * @return 所有对象集合
     */
    public List<T> listAll() {
        try {
            return manager.getDaoSession().loadAll(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过指定的 id 查询对象
     *
     * @param id id
     * @return 指定的 id 查询对象
     */
    public T find(long id) {
        try {
            return manager.getDaoSession().load(clazz, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新
     *
     * @param t 泛型
     * @return 更新的结果
     */
    public boolean update(T t) {
        try {
            manager.getDaoSession().update(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * queryRaw 查询
     *
     * @param where         查询条件
     * @param selectionArgs 查询参数
     * @return 查询数据集
     */
    public List<T> queryAll(String where, String... selectionArgs) {
        try {
            return manager.getDaoSession().queryRaw(clazz, where, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * build 查询
     *
     * @return 查询数据集
     */
    public List<T> queryBuilder() {
        try {
            return manager.getDaoSession().queryBuilder(clazz).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询全部，dao 查询
     *
     * @param clazz 类
     * @return 查询数据集
     */
    public List<T> queryDaoAll(Class clazz) {
        try {
            DaoMaster daoMaster = manager.getDaoMaster();
            DaoSession session = daoMaster.newSession();
            // noinspection unchecked
            return session.loadAll(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
