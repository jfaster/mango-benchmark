package org.jfaster.mango.benchmark;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;

/**
 * @author ash
 */
@DB
public interface MangoUserDao extends UserDao {

    @Override
    @SQL("insert into user(id, name, age) values(:1, :2, :3)")
    public void addUser(int id, String name, int age) throws Exception;

    @Override
    @SQL("select id, name, age from user where id = :1")
    public User getUserById(int id) throws Exception;

    @Override
    @SQL("update user set age = :2 where id = :1")
    public void updateUser(int id, int age) throws Exception;

}
