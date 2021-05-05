package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.TablePrimaryKey;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TablePrimaryKeyMapper {

    @Select("select * from tablePrimaryKey where classReferenceName = #{classReferenceName}")
    TablePrimaryKey getOne(String classReferenceName);

    @Insert("insert into tablePrimaryKey (classReferenceName, currentTableID, length, strategy, offset, createDate) " +
            "values (#{classReferenceName}, #{currentTableID}, #{length}, #{strategy}, #{offset}, #{createDate})")
    int insert(TablePrimaryKey tablePrimaryKey);

    @Update("update tablePrimaryKey set currentTableID = #{currentTableID} where id = id")
    int update(String classReferenceName, String currTableID);
}
