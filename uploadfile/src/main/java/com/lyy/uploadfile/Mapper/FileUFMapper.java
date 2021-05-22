package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.FileUF;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUFMapper {

    @Select("select * from file_uf where uploadAccount = #{uploadAccount}")
    List<FileUF>  getByAccount(@Param("uploadAccount") String account);

    @Select("select * from (select rownum as rn, f.* from file_uf f where f.uploadAccount = #{account} and rownum <= #{endPage}) t where t.rn >#{startPage}")
    List<FileUF> getByAccountAndPage(@Param("account") String account, @Param("startPage") int start, @Param("endPage") int end);

    @Insert("insert into file_uf (id, fileName, fileType, storeName, fileSize, uploadName, uploadAccount, reviseName, reviseAccount, uploadDate, status, locatePath, downloadCount) " +
            "values(#{id}, #{fileName}, #{fileType}, #{storeName}, #{fileSize}, #{uploadName}, #{uploadAccount}, #{reviseName}, #{reviseAccount}, #{uploadDate}, #{status}, #{locatePath}, #{downloadCount})")
    int insert(FileUF fileUF);

    @Update("update file_uf set status = #{status} where id = #{id}")
    int changeStatus(int status, long id);

    @Select("select count(*) from file_uf where uploadAccount = #{account}")
    int count(@Param("account") String account);

    @Select("select * from file_uf where id = #{id}")
    FileUF getOne(long id);

    @Update("update file_uf set downloadCount = #{count} where id = #{id}")
    int updateDownloadCount(@Param("id") long id, @Param("count") int count);
}