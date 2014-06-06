package cc.concurrent.mango.benchmark.model;


/**
 * @author ash
 */
public class Stat {

    private int successNum;
    private int exceptionNum;
    private long totalCost;

    public Stat(int successNum, int exceptionNum, long totalCost) {
        this.successNum = successNum;
        this.exceptionNum = exceptionNum;
        this.totalCost = totalCost;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public int getExceptionNum() {
        return exceptionNum;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public double getAvg() {
        return totalCost / (successNum + exceptionNum);
    }

    @Override
    public String toString() {
        return String.valueOf(getAvg());
    }
}
