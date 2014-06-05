package cc.concurrent.mango.benchmark;

import cc.concurrent.mango.benchmark.dao.JdbcUserDao;
import cc.concurrent.mango.benchmark.dao.UserDao;
import cc.concurrent.mango.benchmark.model.User;
import cc.concurrent.mango.benchmark.util.Config;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class JdbcBatchInsertRound extends BenchmarkTemplate {

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        UserDao userDao = new JdbcUserDao(DataSourceUtil.getDataSource());
        for (int i = 0; i < taskNumPerThread; i++) {
            List<User> users = getUsers(Config.getBatchNum());
            long t = System.nanoTime();
            boolean ok = false;
            try {
                userDao.batchInsert(users);
                ok = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                totalCost.addAndGet(System.nanoTime() - t);
                if (ok) {
                    successNum.incrementAndGet();
                } else {
                    exceptionNum.incrementAndGet();
                }
            }
        }
    }

    private List<User> getUsers(int num) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < num; i++) {
            users.add(new User(100, "test", 1000, new Date()));
        }
        return users;
    }

}
