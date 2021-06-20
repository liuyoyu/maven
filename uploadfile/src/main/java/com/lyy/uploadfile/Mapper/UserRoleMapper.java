package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    @Insert("insert into user_role_uf(id, account, roleId, createDate, status) " +
            "values(#{id}, #{account}, #{roleId}, #{createDate}, #{status})")
    int insert(UserRole role);

    @Update("update user_role_uf set roleId = #{roleId} where id = #{id}")
    int update(long id, long roleId);

    @Update("update user_role_uf set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") long id, @Param("status") int status);

    @Update("update user_role_uf set status = #{status} where account = #{account}")
    int updateStatusByAccount(@Param("account") String account, @Param("status") int status);

    @Update("update user_role_uf set status = #{status} where account = #{account} and roleId = #{roleId}")
    int updateStatusByAccountAndRoleId(@Param("account") String account, @Param("roleId") long roleId, @Param("status") int status);

    @Update("update user_role_uf set account = #{account}, roleId = #{roleId}, status = #{status} where id = #{id}")
    int updateUserRole(UserRole userRole);

    @Select("select * from (select rownum as rn, ur.id, u.name as userName, u.account, u.sex, u.telephone as tel, u.email, " +
            " r.id as roleId, r.roleName, ur.status as roleStatus from user_role_uf ur " +
            "left join user_uf u on ur.account = u.account " +
            "left join role_uf r on ur.roleId = r.id " +
            "where rownum <= #{pageEnd}) t where t.rn > #{pageStart}")
    @ResultType(UserRoleDTO.class)
    List<UserRoleDTO> getAllByPage(@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd);

    @Select("select * from (select rownum as rn, ur.id, u.name as userName, u.account, u.sex, u.telephone as tel, u.email, " +
            " r.id as roleId, r.roleName, ur.status as roleStatus from user_role_uf ur " +
            "left join user_uf u on ur.account = u.account " +
            "left join role_uf r on ur.roleId = r.id " +
            "where rownum <= #{end} and ur.account like '%'||#{account}||'%' and ur.roleId like '%'||#{roleId}||'%' and ur.status like '%'||#{status}||'%') t where t.rn > #{start}")
    @ResultType(UserRoleDTO.class)
    List<UserRoleDTO> search(@Param("account") String account, @Param("roleId") String roleId, @Param("status") String status, @Param("start")int start, @Param("end")int end);

    @Select("select count(id) from user_role_uf ur " +
            "where account like '%'||#{account}||'%' and roleId like '%'||#{roleId}||'%' and status like '%'||#{status}||'%'")
    int countSearch(@Param("account") String account, @Param("roleId") String roleId, @Param("status") String status);

    @Select("select count(id) from user_role_uf")
    int countAll();

    @Select("select id, account, roleId from user_role_uf where id = #{id}")
    @ResultType(UserRoleDTO.class)
    UserRoleDTO getOne(long id);

    @Select("select count(id) from user_role_uf where account = #{account} and id = #{id}")
    int countByAccountAndRoleId(@Param("account") String account, @Param("id") long id);

    @Select("select count(id) from user_role_uf where roleId = #{roleId}")
    int countByRoleId(long roleId);

    @Delete("delete from user_role_uf where id = #{id}")
    int deleteOne(long id);

    @Select("select ur.id, ur.account, u.name as userName, u.sex, u.telephone, u.email, r.id as roleId, r.roleName, ur.status as roleStatus " +
            "from user_role_uf ur left join user_uf u on ur.account = u.account left join role_uf r on ur.roleId = r.id " +
            "where ur.account = #{account}")
    @ResultType(UserRoleDTO.class)
    List<UserRoleDTO> getByAccount(String account);

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
                DELETE_FROM("user_role_uf");
                WHERE("id in (" + sb.toString() + ")");
            }}.toString();
        }
    }
}
