package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.DB;
import cc.concurrent.mango.SQL;
import cc.concurrent.mango.benchmark.User;

/**
 * @author ash
 */
@DB
public interface MangoUserDao {

    @SQL("insert into user_r(uid, name, money, create_time) values(:1.uid, :1.name, :1.money, :1.createTime)")
    public int insert(User user);

}
