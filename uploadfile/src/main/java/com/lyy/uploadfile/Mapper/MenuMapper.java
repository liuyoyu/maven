package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MenuMapper {

    @Insert("insert into menu(id, name, url, parentId, seq, createDate, status) " +
            "values(#{id}, #{name}, #{url}, #{parentId}, #{seq}, #{createDate}, #{status})")
    int insert(Menu menu);

    @Delete("delete from menu where id = {id}")
    int delete(@Param("id")long id);

    @Update("update table set name = #{name}, parentId = #{parentId}, seq = #{seq}, createDate = #{createDate}, " +
            "status = #{status}, url = #{url} where id = #{id}")
    int update(Menu menu);

    @Select("select * from (select rownum as rn, m.* from menu m where rownum <= #{pageEnd}) t where t.rn > #{pageStart}")
    List<Menu> getAllbyPage(@Param("pageStart")int pageStart, @Param("pageEnd") int pageEnd);

    @Select("select count(id) from menu")
    int countAllByPage();

    @Select("select * from menu where menuId = #{menuId}")
    Menu getOne(@Param("menuId") long menuId);

    @Select("select count(id) from menu where url = #{url}")
    int checkUrlDelipute(@Param("url") String url);

    @Select("select t.id, t.name, t.parentId, t.status, t.createDate, t.url, t.seq from ( " +
            "select rownum as rn, m.* from menu m " +
            "where id like '%'||#{id}||'%' and name like '%'||#{name}||'%' and parentId like '%'||#{parentId}||'%' " +
            "and status like '%'||#{status}||'%' and createDate like '%'||#{createDate}||'%' and url like '%'||#{url}||'%' " +
            "and rownum <= #{end} " +
            ") t where t.rn > #{start}")
    List<Menu> search(@Param("id") String id, @Param("parentId") String parentId, @Param("name") String name,
                      @Param("status") String status, @Param("createDate") String date, @Param("url") String url,
                      @Param("start") int start, @Param("end") int end);

    @Select("select count(id) from menu where id like '%'||#{id}||'%' and name like '%'||#{name}||'%' and parentId like '%'||#{parentId}||'%' " +
            "and status like '%'||#{status}||'%' and createDate like '%'||#{createDate}||'%' and url like '%'||#{url}||'%'")
    int searchCount(@Param("id") String id, @Param("parentId") String parentId, @Param("name") String name,
                    @Param("status") String status, @Param("createDate") String date, @Param("url") String url);

    @Select("select * from menu")
    List<Menu> getAll();

    @Select("select id, name from menu where parentId = #{parentId}")
    List<Menu> getParentMenu(@Param("parentId") long parentId);

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
                DELETE_FROM("menu");
                WHERE("id in (" + sb.toString() + ")");
            }}.toString();
        }

    }
}
