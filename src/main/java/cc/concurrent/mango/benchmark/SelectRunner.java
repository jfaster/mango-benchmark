package cc.concurrent.mango.benchmark;

import cc.concurrent.mango.benchmark.model.Stat;
import cc.concurrent.mango.benchmark.round.JdbcSelectRound;
import cc.concurrent.mango.benchmark.round.MangoSelectRound;
import cc.concurrent.mango.benchmark.util.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class SelectRunner {

    public static void main(String[] args) throws Exception {
        List<Stat> jdbcStats = new ArrayList<Stat>();
        List<Stat> mangoStats = new ArrayList<Stat>();
        int round = Config.getRound();
        for (int i = 0; i < round; i++) {
            Stat jdbcStat = new JdbcSelectRound().run();
            Stat mangoStat = new MangoSelectRound().run();
            jdbcStats.add(jdbcStat);
            mangoStats.add(mangoStat);
            System.out.println("round " + (i + 1) + " over!");
        }
        System.out.println("SelectRunner");
        System.out.println("jdbcStats=" + jdbcStats);
        System.out.println("mangoStats=" + mangoStats);
    }
}
