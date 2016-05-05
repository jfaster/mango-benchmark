package org.jfaster.mango.benchmark;

import com.google.common.collect.Maps;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
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
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", id);
        paramMap.put("name", name);
        paramMap.put("age", age);
        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public User getUserById(int id) throws Exception {
        String sql = "select id, name, age from user where id = :id";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<User>(User.class, false));
    }

    @Override
    public void updateUser(int id, int age) throws Exception {
        String sql = "update user set age = :age where id = :id";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", id);
        paramMap.put("age", age);
        jdbcTemplate.update(sql, paramMap);
    }

}
