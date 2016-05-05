package org.jfaster.mango.benchmark;

import org.apache.ibatis.annotations.Select;

/**
 * @author ash
 */
public interface UserMapper {

    @Select("select id, name, age from user where id = #{id}")
    public User getUserById(int id) throws Exception;

}
