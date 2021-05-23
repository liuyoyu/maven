package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.EmailStore;
import com.lyy.uploadfile.Mapper.EmailStoreMapper;
import com.lyy.uploadfile.Service.EmailService;
import com.lyy.uploadfile.Service.EmailsStoreService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailStoreServiceImpl implements EmailsStoreService {

    EmailStoreMapper emailStoreMapper;

    EmailService emailService;

    TablePrimaryKeyService tablePrimaryKeyService;

    public EmailStoreServiceImpl(EmailStoreMapper emailStoreMapper, EmailService emailService,
                                 TablePrimaryKeyService tablePrimaryKeyService) {
        this.emailStoreMapper = emailStoreMapper;
        this.emailService = emailService;
        this.tablePrimaryKeyService = tablePrimaryKeyService;
    }

    @Override
    public Message insert(EmailStore emailStore) {
        String to = emailStore.getToEmailAddr();
        Message res = emailService.sendHTMLMail(new String[]{to}, emailStore.getFroEmailAddr(), "UF注册邮件", null, emailStore.getContextHTML());
        if (res.isSuccess()) {
            Long id = tablePrimaryKeyService.get(EmailStore.class);
            emailStore.setId(id);
            emailStoreMapper.insert(emailStore);
            return Message.success("邮件发送成功");
        } else {
            return res;
        }
    }
}
