package org.jfaster.mango.benchmark;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @author ash
 */
public class MybatisUserDao implements UserDao {

    private final SqlSessionFactory sqlSessionFactory;

    public MybatisUserDao(DataSource ds) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, ds);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }


    @Override
    public void addUser(int id, String name, int age) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.addUser(id, name, age);
            session.commit();
            return;
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserById(int id) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getUserById(id);
            return user;
        } finally {
            session.close();
        }
    }

}
