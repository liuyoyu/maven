package com.lyy.uploadfile.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 解决获取不到favicon.ico文件问题
 *
 */
@Controller
public class FavcionICOController {

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {}
}
