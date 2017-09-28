package cn.jeterlee.database.util.greendao;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import cn.jeterlee.greendao.gen.DaoMaster;
import cn.jeterlee.greendao.gen.DaoSession;

/**
 * 对数据库操作增删改查
 */
class DaoHelper<T> {
    private DaoManager manager;
    private Class<T> clazz;

    public DaoHelper(Context context) {
        manager = DaoManager.getInstance(context);
    }

    private Class<T> getClazz() {
        if (clazz == null) {
            // 获取泛型的Class对象
            // noinspection unchecked
            clazz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
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
        return manager.getDaoSession().insert(t) != -1;
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
        return (List<T>) manager.getDaoSession().loadAll(getClazz());
    }

    /**
     * 通过指定的id查询对象
     *
     * @param id id
     * @return 指定的id查询对象
     */
    public T find(long id) {
        return manager.getDaoSession().load(clazz, id);
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
     * queryRaw查询
     *
     * @param where         查询条件
     * @param selectionArgs 查询参数
     * @return 查询数据集
     */
    public List<T> queryAll(String where, String... selectionArgs) {
        return manager.getDaoSession().queryRaw(clazz, where, selectionArgs);
    }

    /**
     * build查询
     *
     * @return 查询数据集
     */
    public List<T> queryBuilder() {
        return manager.getDaoSession().queryBuilder(clazz).list();
    }

    /**
     * 查询全部，dao查询
     *
     * @param clazz 类
     * @return 查询数据集
     */
    public List<T> queryDaoAll(Class clazz) {
        DaoMaster daoMaster = manager.getDaoMaster();
        DaoSession session = daoMaster.newSession();
        // noinspection unchecked
        return session.loadAll(clazz);
    }
}
