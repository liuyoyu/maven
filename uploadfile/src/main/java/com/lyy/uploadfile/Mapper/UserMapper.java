package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.UserUF;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from user_uf where id=#{id}")
    UserUF getOne(Long id);

    @Insert("insert into user_uf(id, account, password, name, sex, telephone, email, createDate) values(#{id}, #{name}, #{account}, #{password}, #{sex}, #{telephone}, #{email}, #{createDate})")
    int insert(UserUF userUF);

    @Select("select * from user_uf where account=#{account}")
    UserUF getOneByAccount(String account);

    @Select("select count(id) from user_uf where email = #{email}")
    int countByEmail(@Param("email") String email);

    @Update("update user_uf set password=#{password}, name=#{name}, sex=#{sex}, telephone=#{telephone}, email=#{email} where id=#{id}")
    int update(UserUF userUF);

    @Select("select * from user_uf where account = #{account} and password = #{password}")
    UserUF findByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    @Select("select count(id) from user_uf where account = #{account} or email = #{email}")
    int countAccountOrEmail(@Param("account") String account, @Param("email") String email);

    @Select("select * from (select rownum as rn, u.* from user_uf u where rownum <= #{end}) t where rn > #{start}")
    List<UserUF> getAllByList(int start, int end);

    @Select("select count(id) from user_uf")
    int countByPage();
}
