package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Configture.DTO.FileDTO;
import com.lyy.uploadfile.Entry.DownloadUF;
import com.lyy.uploadfile.Entry.FileUF;

import com.lyy.uploadfile.Mapper.DownloadMapper;
import com.lyy.uploadfile.Mapper.FileUFMapper;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.VO.FileListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    TablePrimaryKeyService tablePrimaryKeyService;

    FileUFMapper fileUFMapper;

    DownloadMapper downloadMapper;

    UserService userService;

    @Autowired
    public FileServiceImpl(TablePrimaryKeyService tablePrimaryKeyService,
                           FileUFMapper fileUFMapper,
                           DownloadMapper downloadMapper,
                           UserService userService) {
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.fileUFMapper = fileUFMapper;
        this.downloadMapper = downloadMapper;
        this.userService = userService;
    }

    @Override
    public Message create(FileUF file) {
        Long id = tablePrimaryKeyService.get(FileUF.class);
        file.setId(id);
        int insert = fileUFMapper.insert(file);
        return insert == 0 ? Message.success("上传文件登记成功") : Message.fail("上传文件登记失败");
    }

    @Override
    public Message<List> getAll(String account) {
        List<FileUF> byAccount = fileUFMapper.getByAccount(account);
        return Message.success("获取文件成功", byAccount);
    }

    @Override
    public Message getAllByPage(String account, int page, int limit) {

        List<FileUF> byAccountAndPage = fileUFMapper.getByAccountAndPage(account, (page - 1) * limit, page * limit);
        return Message.success("获取文件成功", byAccountAndPage);
    }

    @Override
    public int count(String account) {
        return fileUFMapper.count(account);
    }

    @Override
    public Message getOne(long id) {
        FileUF one = fileUFMapper.getOne(id);
        if (one == null) {
            return Message.fail("文件不存在");
        }
        return Message.success("文件获取成功", one);
    }

    @Override
    public Message updateDownloadCount(long id, int downloadCount) {
        int i = fileUFMapper.updateDownloadCount(id, downloadCount);
        return i == 0 ? Message.success("更新成功") : Message.fail("更新失败");
    }

    @Override
    public Message getDownloadList(String account) {
        List<FileDTO> all = downloadMapper.getAll(account);
        return Message.success("获取成功", all);
    }

    @Override
    public Message addDownloadFile(String account, long fileId) {
        Message message = userService.find(account);
        if (!message.isSuccess()) {
            return Message.fail("添加失败");
        }
        FileUF one = fileUFMapper.getOne(fileId);
        if (one == null) {
            return Message.fail("添加失败");
        }
        Long id = tablePrimaryKeyService.get(DownloadUF.class);
        DownloadUF download = new DownloadUF();
        download.setId(id);
        download.setAccount(account);
        download.setFileId(fileId);
        download.setDownloadDate(new Date());
        int insert = downloadMapper.insert(download);
        return insert == 0 ? Message.fail("添加下载记录失败") : Message.success("添加下载记录成功", download);
    }

    @Override
    public Message insertDownloadFile(DownloadUF downloadUF) {
        Long id = tablePrimaryKeyService.get(DownloadUF.class);
        downloadUF.setId(id);
        int insert = downloadMapper.insert(downloadUF);
        return insert == 0 ? Message.success("新增失败") : Message.fail("新增失败");
    }
}
