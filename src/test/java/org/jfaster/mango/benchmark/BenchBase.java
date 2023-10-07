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
public abstract class BenchBase {

    @Param({"spring-jdbc", "mybatis", "mango", "jdbc"})
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
    public void teardown() {
        switch (framework) {
            case "mango":
            case "mybatis":
            case "jdbc":
            case "spring-jdbc":
                DS.close();
                break;
        }
    }

    private void setupMango() {
        DS = createDataSource();
        DAO = Mango.newInstance(DS).create(MangoUserDao.class);
        init();
    }

    private void setupJdbc() {
        DS = createDataSource();
        DAO = new JdbcUserDao(DS);
        init();
    }

    private void setupMybatis() {
        DS = createDataSource();
        DAO = new MybatisUserDao(DS);
        init();
    }

    private void setupSpringJdbc() {
        DS = createDataSource();
        DAO = new SpringJdbcUserDao(DS);
        init();
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:test");
        config.setUsername("sa");
        config.setPassword("");
        config.setMinimumIdle(16);
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(8000);
        config.setAutoCommit(true);
        return new HikariDataSource(config);
    }

    private void init() {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = DS.getConnection();
            stat = conn.createStatement();
            stat.execute(Joiner.on("\n").join(tables));
            initData();
        } catch (Exception e) {
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

    private static String[] tables = new String[] {
            "DROP TABLE IF EXISTS user;",
            "CREATE TABLE user",
            "(",
            "    id INTEGER,",
            "    name VARCHAR(25),",
            "    age INTEGER,",
            "    PRIMARY KEY (id)",
            ");"
    };

    abstract void initData() throws Exception;

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
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
        DAO.addUser(1, "ash", 25);
        System.out.println(DAO.getUserById(1));
    }

}
