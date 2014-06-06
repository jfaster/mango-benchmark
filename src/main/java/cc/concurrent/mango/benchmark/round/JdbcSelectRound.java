package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.benchmark.dao.JdbcUserDao;
import cc.concurrent.mango.benchmark.dao.UserDao;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class JdbcSelectRound extends BenchmarkTemplate {

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        UserDao userDao = new JdbcUserDao(DataSourceUtil.getDataSource());
        int maxId = userDao.getMaxId();
        System.out.println("jdbcMaxId=" + maxId);
        for (int i = 0; i < taskNumPerThread; i++) {
            int id = RandomUtils.nextInt(maxId);
            long t = System.nanoTime();
            boolean ok = false;
            try {
                userDao.getUserById(id);
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

}
