package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Entry.EmailStore;
import main.java.com.lyy.uploadfile.Utils.Message;

public interface EmailsStoreService {

    Message insert(EmailStore emailStore);
}
