package cc.concurrent.mango.benchmark.round;

import cc.concurrent.mango.benchmark.model.Stat;
import cc.concurrent.mango.benchmark.util.Config;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public abstract class BenchmarkTemplate {

    private final AtomicInteger successNum = new AtomicInteger();
    private final AtomicInteger exceptionNum = new AtomicInteger();
    private final AtomicLong totalCost = new AtomicLong();

    public Stat run() throws Exception {
        int threadNum = Config.getThreadNum();
        final int taskNumPerThread = Config.getTaskNumPerThread();


        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch latch = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    waitForBegin(barrier);
                    doRun(taskNumPerThread, successNum, exceptionNum, totalCost);
                    notifyEnd(latch);
                }
            }).start();
        }

        latch.await();
        return new Stat(successNum.intValue(), exceptionNum.intValue(), totalCost.longValue());
    }

    abstract void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost);

    private void waitForBegin(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void notifyEnd(CountDownLatch latch) {
        latch.countDown();
    }

}
