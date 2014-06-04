package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.DB;
import cc.concurrent.mango.SQL;
import cc.concurrent.mango.benchmark.model.User;

/**
 * @author ash
 */
@DB
public interface MangoUserDao {

    @SQL("insert into user_m(uid, name, money, create_time) values(:1.uid, :1.name, :1.money, :1.createTime)")
    public int insert(User user);

    @SQL("select uid, name, money, create_time from user_m where id=:1")
    public User getUserById(int id);

    @SQL("select max(id) from user_m")
    public int getMaxId();

}
