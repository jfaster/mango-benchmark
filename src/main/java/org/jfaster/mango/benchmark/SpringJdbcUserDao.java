package org.jfaster.mango.benchmark;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ash
 */
public class SpringJdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcUserDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void addUser(int id, String name, int age) throws Exception {
        String sql = "insert into user(id, name, age) values(?, ?, ?)";
        jdbcTemplate.update(sql, id, name, age);
    }

    @Override
    public User getUserById(int id) throws Exception {
        String sql = "select id, name, age from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<User>(User.class, false));
    }

}
