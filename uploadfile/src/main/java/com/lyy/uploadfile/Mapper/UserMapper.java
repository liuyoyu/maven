package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.UserUF;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from userUF where id=#{id}")
    UserUF getOne(Long id);

    @Insert("insert into userUF(id, account, password, name, sex, telephone, email, createDate) values(#{id}, #{uid}, #{name}, #{sex}, #{telephone}, #{email}, #{createDate})")
    int insert(UserUF userUF);

    @Select("select * from userUF where account=#{account}")
    UserUF getOneByAccount(String account);

    @Update("update userUF set password=#{password}, name=#{name}, sex=#{sex}, telephone=#{telephone}, email=#{email} where id=#{id}")
    int update(UserUF userUF);
}
