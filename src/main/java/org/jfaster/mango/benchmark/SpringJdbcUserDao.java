package org.jfaster.mango.benchmark;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ash
 */
public class SpringJdbcUserDao implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SpringJdbcUserDao(DataSource ds) {
        jdbcTemplate = new NamedParameterJdbcTemplate(new JdbcTemplate(ds));
    }

    @Override
    public void addUser(int id, String name, int age) throws Exception {
        String sql = "insert into user(id, name, age) values(:id, :name, :age)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        paramMap.put("age", age);
        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public User getUserById(int id) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "select id, name, age from user where id = :id";
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<User>(User.class, false));
    }

}
