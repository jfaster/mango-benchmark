package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.DB;
import cc.concurrent.mango.SQL;

import java.util.Date;

/**
 * @author ash
 */
@DB
public interface UserDao {

    @SQL("insert into user_m(uid, name, money, create_time) values(:1, :2, :3, :4)")
    public int insert(int uid, String name, long money, Date createTime) throws Exception;

}
