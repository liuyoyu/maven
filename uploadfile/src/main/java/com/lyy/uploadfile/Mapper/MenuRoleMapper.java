package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.ibatis.jdbc.SelectBuilder.WHERE;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;

@Repository
public interface MenuRoleMapper {

    @Insert("insert into menu_role (Id, roleId, menuId, status, createDate) " +
            "values (#{id}, #{roleId}, #{menuId}, #{status}, #{createDate})")
    int insert(MenuRole menuRole);

    @Delete("delete from menu_role where id = #{id}")
    int delete(long id);

    @Update("update menu_role set roleId = #{roleId}, menuId = #{menuId} where id = #{id}")
    int update(MenuRole menuRole);

    @Select("select t.*, r.id as roleId, r.roleName as roleName, m.id as menuId, m.name as menuName " +
            "from (select rownum as rn, mr.* from menu_role mr where rownum <= #{end}) t " +
            "left join role_uf r on r.id = t.roleId " +
            "left join menu m on m.id = t.menuId " +
            "where t.rn > #{start}")
    @ResultType(MenuRole.class)
    List<MenuRole> getByPage(@Param("start") int start, @Param("end") int end);

    @Select("select count(id) from menu_role")
    int countByPage();

    @Select("select mr.id, mr.menuId, mr.roleId, mr.status, mr.createDate, m.name as menuName, m.url, m.parentId as menuParentId, m.seq, r.roleName as roleName " +
            "from menu_role mr left join menu m on mr.menuId = m.id left join role_uf r on mr.roleId = r.id " +
            "where mr.roleId = #{roleId}")
    @ResultType(MenuRole.class)
    List<MenuRole> getByRoleId(long roleId);

    @Select("select * from (" +
            " select rownum as rn, mr.id, mr.menuId, m.name as menuName, mr.roleId, r.roleName, mr.status, mr.createDate " +
            " from menu_role mr left join role_uf r on mr.roleId = r.id left join menu m on mr.menuId = m.id " +
            " where m.id like '%'||#{menuId}||'%' and m.name like '%'||#{menuName}||'%' and r.id like '%'||#{roleId}||'%' and r.roleName like '%'||#{roleName}||'%' " +
            " and mr.status like '%'||#{status}||'%' and rownum <= #{end} " +
            ") t where t.rn > #{start}")
    List<MenuRole> search(@Param("menuId") String menuId, @Param("menuName") String menuName,@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("status") String status,@Param("start") int start,@Param("end") int end);

    @Select(" select count(mr.id) from menu_role mr left join role_uf r on mr.roleId = r.id left join menu m on mr.menuId = m.id " +
            " where m.id like '%'||#{menuId}||'%' and m.name like '%'||#{menuName}||'%' and r.id like '%'||#{roleId}||'%' and r.roleName like '%'||#{roleName}||'%' " +
            " and mr.status like '%'||#{status}||'%' ")
    int searchCount(@Param("menuId") String menuId, @Param("menuName") String menuName,@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("status") String status);

    @Select("select count(id) from menu_role where roleId = #{roleId} and menuId = #{menuId}")
    int insertCheck(@Param("roleId") long roleId,@Param("menuId") long menuId);

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
                DELETE_FROM("menu_role");
                WHERE("id in (" + sb.toString() + ")");
            }}.toString();
        }
    }
}
