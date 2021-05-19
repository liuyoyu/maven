package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.FileUF;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import com.lyy.uploadfile.Utils.Result;
import com.lyy.uploadfile.VO.FileListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/download")
public class DownloadListController {

    FileService fileService;

    LoginService loginService;

    @Autowired
    public DownloadListController(FileService fileService, LoginService loginService) {
        this.fileService = fileService;
        this.loginService = loginService;
    }

    @RequestMapping("/list")
    public Result getDownloadList(){
        UserUF loginInfo = loginService.getLoginInfo();
        Message downloadList = fileService.getDownloadList(loginInfo.getAccount());
        List<FileListVO> res = (List<FileListVO>) downloadList.res();
        return PageData.success(res, res.size());
    }
}
