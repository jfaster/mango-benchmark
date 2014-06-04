package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.benchmark.model.User;

import java.util.Date;

/**
 * @author ash
 */
public interface UserDao {

    public int insert(int uid, String name, long money, Date createTime) throws Exception;

    public User getUserById(int id) throws Exception;

    public int getMaxId();

}
