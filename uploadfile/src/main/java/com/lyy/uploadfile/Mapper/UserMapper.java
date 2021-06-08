package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.UserUF;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from user_uf where id=#{id}")
    UserUF getOne(Long id);

    @Insert("insert into user_uf(id, account, password, name, sex, telephone, email, createDate) values(#{id}, #{account}, #{password}, #{name}, #{sex}, #{telephone}, #{email}, #{createDate})")
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

    @Select("select * from (select rownum as rn, u.id, u.name, u.account, u.sex, u.telephone, u.email, u.createDate from user_uf u where rownum <= #{end}) t where rn > #{start}")
    List<UserUF> getAllByList(@Param("start") int start, @Param("end") int end);

    @Select("select count(id) from user_uf")
    int countByPage();

    @Select("select * from (" +
            " select rownum as rn, u.id, u.name, u.account, u.sex, u.telephone, u.email, u.createDate from user_uf u where name like '%'||#{name}||'%' and account like '%'||#{account}||'%' and sex like '%'||#{sex}||'%' and (telephone like '%'||#{telemail}||'%' or email like '%'||#{telemail}||'%') and createDate like '%'||#{createDate}||'%' " +
            " and rownum <= #{end} " +
            ") t where t.rn > #{start}")
    List<UserUF> search(@Param("account") String account, @Param("name") String name, @Param("sex")String sex, @Param("telemail")String telemail, @Param("createDate") String createDate ,@Param("start") int start, @Param("end") int end);

    @Select("select count(id) from user_uf where name like '%'||#{name}||'%' and account like '%'||#{account}||'%' and sex like '%'||#{sex}||'%' and (telephone like '%'||#{telemail}||'%' or email like '%'||#{telemail}||'%') and createDate like '%'||#{createDate}||'%'")
    int searchCount(@Param("account") String account, @Param("name") String name, @Param("sex")String sex, @Param("telemail")String telemail, @Param("createDate") String createDate );

    @Update("update user_uf set password = #{password} where id = #{id}")
    int updatePwd(@Param("password") String password, @Param("id")long id);

    @Delete("delete from user_uf where id = #{id}")
    int delete(@Param("id") long id);

    @DeleteProvider(type = Provider.class, method = "batchDelete")
    int deleteBatch(@Param("idList") List<Long> idList);


    class Provider{
        //批量删除
        public String batchDelete(@Param("idList") List<Long> idList){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < idList.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(idList.get(i));
            }
            return new SQL(){{
                DELETE_FROM("user_uf");
                WHERE("id in (" + sb.toString() + ")");
            }}.toString();
        }
    }
}
