package main.java.com.lyy.uploadfile.Service;

import main.java.com.lyy.uploadfile.Utils.Message;

public interface EmailService {

    Message sendHTMLMail(String[] to, String from, String subject, String[] cc, String content);
}
