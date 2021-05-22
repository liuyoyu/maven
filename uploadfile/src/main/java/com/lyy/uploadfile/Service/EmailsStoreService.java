package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.EmailStore;
import com.lyy.uploadfile.Utils.Message;

public interface EmailsStoreService {

    Message insert(EmailStore emailStore);
}
