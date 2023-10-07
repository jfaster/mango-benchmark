package org.jfaster.mango.benchmark;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ash
 */
public interface UserMapper {

    @Insert("insert into user(id, name, age) values(#{id}, #{name}, #{age})")
    public void addUser(@Param("id") int id, @Param("name") String name, @Param("age") int age) throws Exception;

    @Select("select id, name, age from user where id = #{id}")
    public User getUserById(int id) throws Exception;

    @Update("update user set age = #{age} where id = #{id}")
    public void updateUser(@Param("id") int id, @Param("age") int age) throws Exception;

}
