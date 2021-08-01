package main.java.com.lyy.uploadfile.Mapper;

import main.java.com.lyy.uploadfile.Configture.DTO.FileDTO;
import main.java.com.lyy.uploadfile.Entry.DownloadUF;
import main.java.com.lyy.uploadfile.VO.FileListVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadMapper {

    @Select("select f.id as fileId, f.fileName, f.fileSize, f.fileType, f.uploadName, f.reviseName, f.uploadDate as createDate, d.downloadDate " +
            "from download d left join file_uf f on d.fileId = f.id where d.account = #{account}")
    @ResultType(FileListVO.class)
    List<FileDTO> getAll(@Param("account") String account);

    @Insert("insert into download(id, fileId, account, downloadDate) " +
            "values(#{id}, #{fileId}, #{account}, #{downloadDate})")
    int insert(DownloadUF dl);
}
