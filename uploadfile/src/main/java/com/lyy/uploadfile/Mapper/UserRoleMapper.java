package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    @Insert("insert into user_role_uf(id, account, roleId, createDate) " +
            "values(#{id}, #{account}, #{roleId}, #{createDate})")
    int insert(UserRole role);

    @Update("update user_role_uf set roleId = #{roleId} where id = #{id}")
    int update(long id, long roleId);

    @Select("select * from (select rownum as rn, u.id, u.name as userName, u.account, u.sex, u.telephone as tel, u.email, " +
            " r.id as roleId, r.name as roleName from user_role_uf ur " +
            "left join user_uf u on ur.account = u.account " +
            "left join role_uf r on ur.roleId = r.id " +
            "where rownum <= #{pageEnd}) t where t.rn > {pageStart}")
    @ResultType(UserRoleDTO.class)
    List<UserRoleDTO> getAllByPage(int pageStart, int pageEnd);

    @Select("select id, account, roleId from user_role_uf where id = #{id}")
    @ResultType(UserRoleDTO.class)
    UserRoleDTO getOne(long id);

    @Select("select count(id) from user_role_uf where account = #{account} and id = #{id}")
    int countByAccountAndRoleId(@Param("account") String account, @Param("id") long id);

    @Select("select count(id) from user_role_uf where roleId = #{roleId}")
    int countByRoleId(long roleId);

    @Delete("delete from user_role_uf where id = #{id}")
    int deleteOne(long id);
}
