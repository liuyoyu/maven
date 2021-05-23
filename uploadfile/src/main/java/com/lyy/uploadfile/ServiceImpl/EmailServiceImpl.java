package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Mapper.EmailStoreMapper;
import com.lyy.uploadfile.Service.EmailService;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
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
    public Message sendHTMLMail(String[] to, String from, String subject, String[] cc, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        log.info("\n开始发送邮件.......\n收件人：{}\n发件人：{}\n主题：{}\n抄送：{}\n内容：{}", to,
                from, subject, cc, content);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            assert from != null && to.length != 0;
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            if (cc != null && cc.length != 0)
                mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailSendException e) {
            String error = e.getMessage();
            log.error("邮件发送异常：{}", error);

            String exception = error.substring(0, error.indexOf(';'));
            exception = exception.substring(exception.lastIndexOf(':'));
            StringBuilder msg = new StringBuilder("邮件发送失败，报错信息如下：");
            msg.append(exception);
            return Message.fail(msg.toString());
        }
        return Message.success("邮件已发送至有效，请查看");
    }
}
