package cc.concurrent.mango.benchmark.dao;

import cc.concurrent.mango.jdbc.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

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

}
