package org.jfaster.mango.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author ash
 */
@Warmup(iterations = 15)
@Measurement(iterations = 15)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class QueryBench extends BenchBase {

    @Benchmark
    @CompilerControl(CompilerControl.Mode.INLINE)
    public static Object cycleQuery() throws Exception {
        User u = DAO.getUserById(1);
        if (u == null) {
            throw new IllegalStateException();
        }
        return u;
    }

}
