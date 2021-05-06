package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.TablePrimaryKey;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TablePrimaryKeyMapper {

    @Select("select * from table_primary_key where classReferenceName = #{classReferenceName}")
    TablePrimaryKey getOne(String classReferenceName);

    @Insert("insert into table_primary_key (classReferenceName, currentTableID, strategy, createDate) " +
            "values (#{classReferenceName}, #{currentTableID}, #{strategy}, #{createDate})")
    int insert(TablePrimaryKey tablePrimaryKey);

    @Update("update table_primary_key set currentTableID = #{currentTableID} where classReferenceName = #{classReferenceName}")
    int update(@Param("currentTableID") String classReferenceName, @Param("classReferenceName") String currentTableID);
}
