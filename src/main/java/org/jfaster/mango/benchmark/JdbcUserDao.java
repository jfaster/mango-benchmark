package org.jfaster.mango.benchmark;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author ash
 */
public class JdbcUserDao implements UserDao {

    private final DataSource ds;

    public JdbcUserDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void addUser(int id, String name, int age) throws Exception {
        Connection conn = null;
        PreparedStatement pstat = null;
        try {
            conn = ds.getConnection();
            pstat = conn.prepareStatement("insert into user(id, name, age) values(?, ?, ?)");
            pstat.setInt(1, id);
            pstat.setString(2, name);
            pstat.setInt(3, age);
            pstat.executeUpdate();
            return;
        } finally {
            if (pstat != null) {
                pstat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public User getUserById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pstat = conn.prepareStatement("select id, name, age from user where id = ?");
            pstat.setInt(1, id);
            rs = pstat.executeQuery();
            User u = null;
            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setAge(rs.getInt("age"));
            }
            return u;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstat != null) {
                pstat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void updateUser(int id, int age) throws Exception {
        Connection conn = null;
        PreparedStatement pstat = null;
        try {
            conn = ds.getConnection();
            pstat = conn.prepareStatement("update user set age = ? where id = ?");
            pstat.setInt(1, age);
            pstat.setInt(2, id);
            pstat.executeUpdate();
            return;
        } finally {
            if (pstat != null) {
                pstat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
