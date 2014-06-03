package cc.concurrent.mango.benchmark.dao;

import java.util.Date;

/**
 * @author ash
 */
public interface UserDao {

    public int insert(int uid, String name, long money, Date createTime) throws Exception;

}
