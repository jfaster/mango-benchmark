package org.jfaster.mango.benchmark;

/**
 * @author ash
 */
public interface UserDao {

    public void addUser(int id, String name, int age) throws Exception;

    public User getUserById(int id) throws Exception;

}
