package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.FileUF;
import com.lyy.uploadfile.Utils.Message;

public interface FileService {

    Message create(FileUF fileUF);

    Message getAll(String account);

    Message getAllByPage(String account, int page, int limie);

    int count(String account);

    Message getOne(long id);

    Message updateDownloadCount(long id, int downloadCount);
}
