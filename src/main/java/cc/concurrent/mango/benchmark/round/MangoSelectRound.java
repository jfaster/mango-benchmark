package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.Mango;
import cc.concurrent.mango.benchmark.dao.JdbcUserDao;
import cc.concurrent.mango.benchmark.dao.MangoUserDao;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;
import cc.concurrent.mango.benchmark.util.MangoUtil;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class MangoSelectRound extends BenchmarkTemplate {

    private Mango mango;

    public MangoSelectRound(Mango mango) {
        this.mango = mango;
    }

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        MangoUserDao userDao = mango.create(MangoUserDao.class);
        int maxId = new JdbcUserDao(DataSourceUtil.getDataSource()).getMaxId(); // 这样不会影响mango内部的性能统计
        System.out.println("mangoMaxId=" + maxId);
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

