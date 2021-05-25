package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.Menu;
import com.lyy.uploadfile.Entry.MenuRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRoleMapper {

    @Insert("insert into menu_role (Id, roleId, menuId, status, createDate) " +
            "values (#{id}, #{roleId}, #{menuId}, #{status}, #{createDate})")
    int insert(MenuRole menuRole);

    @Delete("delete from menu_role where id = #{id}")
    int delete(long id);

    @Update("update menu_role set roleId = #{roleId}, menuId = #{menuId} where id = #{id}")
    int update(MenuRole menuRole);

    @Select("select t.*, r.id as roleId, r.name as roleName, m.id as menuId, m.name as menuName " +
            "from (select rownum as rn, mr.* from menu_role mr where rownnum <= #{end}) t " +
            "left join role r on r.id = t.roleId " +
            "left join menu m on m.id = t.menuId " +
            "where t.rn > #{start}")
    @ResultType(MenuRole.class)
    List<MenuRole> getByPage(int start, int end);

    @Select("select count(id) from menu_role")
    int countByPage();
}
