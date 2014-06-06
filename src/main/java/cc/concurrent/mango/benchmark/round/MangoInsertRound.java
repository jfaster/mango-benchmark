package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.Mango;
import cc.concurrent.mango.benchmark.dao.MangoUserDao;
import cc.concurrent.mango.benchmark.model.User;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class MangoInsertRound extends BenchmarkTemplate {

    private Mango mango;

    public MangoInsertRound(Mango mango) {
        this.mango = mango;
    }

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        MangoUserDao userDao = mango.create(MangoUserDao.class);
        for (int i = 0; i < taskNumPerThread; i++) {
            User user = new User(100, "test", 1000, new Date());
            long t = System.nanoTime();
            boolean ok = false;
            try {
                userDao.insert(user);
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

