package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface EmailService {

    Message sendHTMLMail(String[] to, String from, String subject, String[] cc, String content);
}
