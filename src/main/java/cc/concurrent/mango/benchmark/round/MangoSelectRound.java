package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.Mango;
import cc.concurrent.mango.benchmark.dao.MangoUserDao;
import cc.concurrent.mango.benchmark.util.MangoUtil;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class MangoSelectRound extends BenchmarkTemplate {

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        Mango mango = MangoUtil.getMango();
        MangoUserDao userDao = mango.create(MangoUserDao.class);
        int maxId = userDao.getMaxId();
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

