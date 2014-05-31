package cc.concurrent.mango.benchmark.util;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * @author ash
 */
public class DataSourceUtil {

    public static DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        String host = Config.getJdbcHost();
        int port = Config.getJdbcPort();
        String database = Config.getJdbcDatabase();
        int connectTimeout = 100;
        int socketTimeout = 100;
        String username = Config.getJdbcUserName();
        String password = Config.getJdbcPassword();

        String url = String.format("jdbc:mysql://%s:%s/%s?connectTimeout=%s&socketTimeout=%s",
                host, port, database, connectTimeout, socketTimeout);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(8);
        ds.setMaxActive(100);
        ds.setMaxIdle(10);
        ds.setMaxWait(10);
        ds.setTestOnReturn(false);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(90 * 1000);
        ds.setNumTestsPerEvictionRun(50);
        ds.setTimeBetweenEvictionRunsMillis(1000);
        ds.setValidationQuery("SELECT 1");

        return ds;
    }

}
