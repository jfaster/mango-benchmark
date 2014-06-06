package cc.concurrent.mango.benchmark;

import cc.concurrent.mango.benchmark.util.Config;
import cc.concurrent.mango.util.concurrent.atomic.LongAddable;
import cc.concurrent.mango.util.concurrent.atomic.LongAdder;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class AtomicRunner {

    private static AtomicLong addableNum = new AtomicLong();
    private static AtomicLong addableCost = new AtomicLong();
    private static int threadNum = Config.getThreadNum();
    private static int taskNumPerThread = Config.getTaskNumPerThread();

    public static void main(String[] args) throws Exception {
        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch latch = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    waitForBegin(barrier);
                    doRun();
                    notifyEnd(latch);
                }
            }).start();
        }

        long t = System.nanoTime();
        latch.await();

        System.out.println("cost=" + (System.nanoTime() - t));
        System.out.println("addableNum=" + addableNum);
        System.out.println("addableCost=" + addableCost);
    }

    private static void doRun() {
        for (int i = 0; i < taskNumPerThread; i++) {
            addableNum.incrementAndGet();
            addableCost.addAndGet(10);
        }
    }

    private static void waitForBegin(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private static void notifyEnd(CountDownLatch latch) {
        latch.countDown();
    }


}
