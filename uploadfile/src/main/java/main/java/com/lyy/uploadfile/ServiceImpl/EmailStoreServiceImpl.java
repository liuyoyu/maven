package main.java.com.lyy.uploadfile.ServiceImpl;

import main.java.com.lyy.uploadfile.Service.EmailsStoreService;
import main.java.com.lyy.uploadfile.Entry.EmailStore;
import main.java.com.lyy.uploadfile.Mapper.EmailStoreMapper;
import main.java.com.lyy.uploadfile.Service.EmailService;
import main.java.com.lyy.uploadfile.Service.TablePrimaryKeyService;
import main.java.com.lyy.uploadfile.Utils.Message;
import org.springframework.stereotype.Service;

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
