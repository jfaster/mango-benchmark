package cc.concurrent.mango.benchmark.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author ash
 */
public class Config {

    private static Configuration CONFIG;

    static {
        try {
            CONFIG = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getJdbcHost() {
        return CONFIG.getString("jdbc.host");
    }

    public static int getJdbcPort() {
        return CONFIG.getInt("jdbc.port");
    }

    public static String getJdbcDatabase() {
        return CONFIG.getString("jdbc.database");
    }

    public static String getJdbcUserName() {
        return CONFIG.getString("jdbc.username");
    }

    public static String getJdbcPassword() {
        return CONFIG.getString("jdbc.password");
    }

    public static int getThreadNum() {
        return CONFIG.getInt("threadNum");
    }

    public static int getTaskNumPerThread() {
        return CONFIG.getInt("taskNumPerThread");
    }

}
