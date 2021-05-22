package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Mapper.EmailStoreMapper;
import com.lyy.uploadfile.Service.EmailService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    EmailStoreMapper emailStoreMapper;

    TablePrimaryKeyService tablePrimaryKeyService;

    JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(EmailStoreMapper emailStoreMapper, TablePrimaryKeyService tablePrimaryKeyService,
                            JavaMailSender javaMailSender) {
        this.emailStoreMapper = emailStoreMapper;
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Message sendHTMLMail(List<String> to, String from, String subject, List<String> cc, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            assert from != null && !to.isEmpty();
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to.toArray(new String[0]));
            if (cc != null && !cc.isEmpty())
                mimeMessageHelper.setCc(cc.toArray(new String[0]));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("验证码发送异常：{}", e.getMessage());
            return Message.fail("验证码发送失败");
        }
        return Message.success("验证码已发送至有效，请查看");
    }
}
