package org.jfaster.mango.benchmark;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.jdbc.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    //@Mapper(A.class)
    public User getUserById(int id) throws Exception;

    static class A extends AbstractRowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            return user;
        }
    }

}
