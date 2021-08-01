package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.DownloadUF;
import main.java.com.lyy.uploadfile.Entry.FileUF;
import main.java.com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface FileService {

    Message create(FileUF fileUF);

    Message getAll(String account);

    Message getAllByPage(String account, int page, int limie);

    int count(String account);

    Message getOne(long id);

    Message updateDownloadCount(long id, int downloadCount);

    Message getDownloadList(String account);

    Message addDownloadFile(String account, long fileId);

    Message insertDownloadFile(DownloadUF downloadUF);

    List<FileUF> searchByFileName(String fileName, int page, int limit);

    int countByFileName(String fileName);
}
