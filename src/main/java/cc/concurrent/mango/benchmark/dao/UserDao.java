package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.benchmark.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author ash
 */
public interface UserDao {

    public int insert(int uid, String name, long money, Date createTime) throws Exception;

    public User getUserById(int id) throws Exception;

    public int getMaxId();

    public int[] batchInsert(List<User> users) throws Exception;

    public int updateUserNamesByIds(List<Integer> ids, long money) throws Exception;

    public List<User> getUsersByIds(List<Integer> ids) throws Exception;

}
