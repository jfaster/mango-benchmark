package cc.concurrent.mango.benchmark.util;

import cc.concurrent.mango.Mango;

/**
 * @author ash
 */
public class MangoUtil {

    private static Mango mango = new Mango(DataSourceUtil.getDataSource());

    public static Mango getMango() {
        return mango;
    }

}
