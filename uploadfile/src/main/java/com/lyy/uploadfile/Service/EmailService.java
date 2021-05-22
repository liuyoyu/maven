package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface EmailService {

    Message sendHTMLMail(List<String> to, String from, String subject, List<String> cc, String content);
}
