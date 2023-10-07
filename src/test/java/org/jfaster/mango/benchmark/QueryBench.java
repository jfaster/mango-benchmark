package org.jfaster.mango.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author ash
 */
@Warmup(iterations = 20)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class QueryBench extends BenchBase {

    @Benchmark
    @CompilerControl(CompilerControl.Mode.INLINE)
    public Object cycleQuery() throws Exception {
        User u = DAO.getUserById(1);
        if (u == null) {
            throw new IllegalStateException();
        }
        return u;
    }

    @Override
    void initData() throws Exception {
        DAO.addUser(1, "ash", 25);
    }

}
