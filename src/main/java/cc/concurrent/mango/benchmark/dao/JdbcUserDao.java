package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.benchmark.model.User;
import cc.concurrent.mango.jdbc.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * @author ash
 */
public class JdbcUserDao implements UserDao {

    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int insert(int uid, String name, long money, Date createTime) throws Exception {
        Connection conn = JdbcUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into user_j(uid, name, money, create_time) values(?, ?, ?, ?)");
            ps.setInt(1, uid);
            ps.setString(2, name);
            ps.setLong(3, money);
            ps.setTimestamp(4, new Timestamp(createTime.getTime()));
            return ps.executeUpdate();
        } finally {
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
    }

    @Override
    public User getUserById(int id) throws Exception {
        Connection conn = JdbcUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select uid, name, money, create_time from user_j where id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                long money = rs.getLong("money");
                Date createTime = rs.getDate("create_time");
                return new User(uid, name, money, createTime);
            }
            return null;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
    }

    @Override
    public int getMaxId() {
        Connection conn = JdbcUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select max(id) from user_j");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
    }

    @Override
    public int[] batchInsert(List<User> users) throws Exception {
        Connection conn = JdbcUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into user_j(uid, name, money, create_time) values(?, ?, ?, ?)");
            for (User user : users) {
                ps.setInt(1, user.getUid());
                ps.setString(2, user.getName());
                ps.setLong(3, user.getMoney());
                ps.setTimestamp(4, new Timestamp(user.getCreateTime().getTime()));
                ps.addBatch();
            }
            return ps.executeBatch();
        } finally {
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
    }
}
