package cc.concurrent.mango.benchmark.model;

import java.util.Date;

/**
 * @author ash
 */
public class User {

    private int uid;
    private String name;
    private long money;
    private Date createTime;

    public User() {
    }

    public User(int uid, String name, long money, Date createTime) {
        this.uid = uid;
        this.name = name;
        this.money = money;
        this.createTime = createTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
