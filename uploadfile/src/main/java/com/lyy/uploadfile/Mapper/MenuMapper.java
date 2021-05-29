package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {

    @Insert("insert into menu(id, name, url, parentId, seq, createDate, status) " +
            "values(#{id}, #{name}, #{url}, #{parentId}, #{seq}, #{createDate}, #{status})")
    int insert(Menu menu);

    @Delete("delete from menu where id = {id}")
    int delete(long id);

    @Update("update table set name = #{name}, parentId = #{parentId}, seq = #{seq}, createDate = #{createDate}, " +
            "status = #{status}, url = #{url} where id = #{id}")
    int update(Menu menu);

    @Select("select * from (select rownum as rn, m.* from menu m where rownum <= pageEnd) t where t.rn > pageStart")
    List<Menu> getAllbyPage(int pageStart, int pageEnd);

    @Select("select count(id) from menu")
    int countAllByPage();

    @Select("select * from menu where menuId = #{menuId}")
    Menu getOne(long menuId);

    @Select("select count(id) from menu where url = #{url}")
    int checkUrlDelipute(String url);
}
