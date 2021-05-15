package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.FileUF;

import com.lyy.uploadfile.Mapper.FileUFMapper;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    TablePrimaryKeyService tablePrimaryKeyService;

    FileUFMapper fileUFMapper;

    @Autowired
    public FileServiceImpl(TablePrimaryKeyService tablePrimaryKeyService,
                           FileUFMapper fileUFMapper) {
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.fileUFMapper = fileUFMapper;
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
}
