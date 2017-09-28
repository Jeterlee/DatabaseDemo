package cn.jeterlee.database.util.ormlite.dao;

import java.sql.SQLException;

/**
 * 数据访问接口
 *
 * @param <T> 操作的实体类型
 * @param <Z> 表对应的主键类型
 * @author Jeterlee
 */
public interface IDao<T, Z> {
    /**
     * 添加
     *
     * @param t 操作的实体类型
     * @return
     * @throws SQLException 抛出异常
     */
    public int save(T t) throws SQLException;

    /**
     * 删除
     *
     * @param t 操作的实体类型
     * @return
     * @throws SQLException 抛出异常
     */
    public int delete(T t) throws SQLException;

    /**
     * 修改
     *
     * @param t 操作的实体类型
     * @return
     * @throws SQLException 抛出异常
     */
    public int update(T t) throws SQLException;

    /**
     * 查询
     *
     * @param z 操作的实体类型
     * @return
     * @throws SQLException 抛出异常
     */
    public T queryById(Z z) throws SQLException;
}
