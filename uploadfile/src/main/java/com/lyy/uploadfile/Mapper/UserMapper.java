package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.UserUF;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from user_uf where id=#{id}")
    UserUF getOne(Long id);

    @Insert("insert into user_uf(id, account, password, name, sex, telephone, email, createDate) values(#{id}, #{name}, #{account}, #{password}, #{sex}, #{telephone}, #{email}, #{createDate})")
    int insert(UserUF userUF);

    @Select("select * from user_uf where account=#{account}")
    UserUF getOneByAccount(String account);

    @Update("update user_uf set password=#{password}, name=#{name}, sex=#{sex}, telephone=#{telephone}, email=#{email} where id=#{id}")
    int update(UserUF userUF);

    @Select("select * from user_uf where account = #{account} and password = #{password}")
    UserUF findByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}
