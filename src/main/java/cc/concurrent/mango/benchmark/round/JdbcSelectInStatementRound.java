package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.benchmark.dao.JdbcUserDao;
import cc.concurrent.mango.benchmark.dao.UserDao;
import cc.concurrent.mango.benchmark.util.Config;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;
import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class JdbcSelectInStatementRound extends BenchmarkTemplate {

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        UserDao userDao = new JdbcUserDao(DataSourceUtil.getDataSource());
        int maxId = userDao.getMaxId();
        System.out.println("mangoMaxId=" + maxId);
        for (int i = 0; i < taskNumPerThread; i++) {
            List<Integer> ids = getIds(maxId, Config.getBatchNum());
            long t = System.nanoTime();
            boolean ok = false;
            try {
                userDao.getUsersByIds(ids);
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

    private List<Integer> getIds(int maxId, int num) {
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < num; i++) {
            ids.add(RandomUtils.nextInt(maxId));
        }
        return ids;
    }

}

