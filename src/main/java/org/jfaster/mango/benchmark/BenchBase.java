/*
 * Copyright (C) 2014 Brett Wooldridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jfaster.mango.benchmark;

import com.google.common.base.Joiner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jfaster.mango.operator.Mango;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@State(Scope.Benchmark)
public class BenchBase {

    @Param({"mango", "jdbc", "spring-jdbc", "mybatis"})
    public String framework;

    public static HikariDataSource DS;
    public static UserDao DAO;

    @Setup(Level.Trial)
    public void setup(BenchmarkParams params) {
        switch (framework) {
            case "mango":
                setupMango();
                break;
            case "jdbc":
                setupJdbc();
                break;
            case "mybatis":
                setupMybatis();
                break;
            case "spring-jdbc":
                setupSpringJdbc();
                break;
        }
    }

    @TearDown(Level.Trial)
    public void teardown() throws SQLException {
        switch (framework) {
            case "mango":
                DS.close();
                break;
            case "jdbc":
                DS.close();
                break;
            case "mybatis":
                DS.close();
                break;
            case "spring-jdbc":
                DS.close();
                break;
        }
    }

    protected void setupMango() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        DS = new HikariDataSource(config);
        DAO = Mango.newInstance(DS).create(MangoUserDao.class);

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            DAO.addUser(1, "ash", 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setupJdbc() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        DS = new HikariDataSource(config);
        DAO = new JdbcUserDao(DS);

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            DAO.addUser(1, "ash", 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setupMybatis() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        DS = new HikariDataSource(config);
        DAO = new MybatisUserDao(DS);

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setupSpringJdbc() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        DS = new HikariDataSource(config);
        DAO = new SpringJdbcUserDao(DS);

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            DAO.addUser(1, "ash", 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String[] tables = new String[] {
            "DROP TABLE IF EXISTS user;",
            "CREATE TABLE user",
            "(",
            "    id INTEGER,",
            "    name VARCHAR(25),",
            "    age INTEGER,",
            "    PRIMARY KEY (id)",
            ");"
    };

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        DS = new HikariDataSource(config);
        DAO = new SpringJdbcUserDao(DS);

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DAO.addUser(1, "ash", 25);
        System.out.println(DAO.getUserById(1).getName());
    }

}
