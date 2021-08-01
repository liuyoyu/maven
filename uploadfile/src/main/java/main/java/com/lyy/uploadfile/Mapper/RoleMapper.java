package main.java.com.lyy.uploadfile.Mapper;

import main.java.com.lyy.uploadfile.Entry.RoleUF;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    @Insert("insert into role_uf(id, roleName, createDate, status) values(#{id}, #{roleName}, #{createDate}, #{status})")
    int insert(RoleUF roleUF);

    @Update("update role_uf set status = #{status} where id = #{id}")
    int updateStatus(int status, long id);

    @Select("select * from (select rownum as rn, r.* from role_uf as r where rownum <= #{end}) t where t.rn > #{start}")
    List<RoleUF> getAll(@Param("start") int startPage, @Param("end") int endPage);

    @Delete("delete from role_uf where id = #{id}")
    int deleteOne(long id);

    @Select("select * from role_uf")
    List<RoleUF> getList();

    @Select("select * from role_uf where id = #{id}")
    RoleUF getOne(Long id);
}
