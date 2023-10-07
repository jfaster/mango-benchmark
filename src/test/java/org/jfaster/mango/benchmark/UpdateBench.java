package org.jfaster.mango.benchmark;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
* @author ash
*/
@Warmup(iterations = 20)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UpdateBench extends BenchBase {

    private final static int MAX_NUM = 16;

    @Benchmark
    @CompilerControl(CompilerControl.Mode.INLINE)
    public Object cycleUpdate() throws Exception {
        int id = RandomUtils.nextInt(1, MAX_NUM + 1);
        int age = RandomUtils.nextInt(1, 1000);
        DAO.updateUser(id, age);
        return 1;
    }

    @Override
    void initData() throws Exception{
        for (int id = 1; id <= MAX_NUM; id++) {
            DAO.addUser(id, "ash", 25);
        }
    }

}
