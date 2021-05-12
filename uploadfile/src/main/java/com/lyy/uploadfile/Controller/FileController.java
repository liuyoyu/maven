package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Utils.Result;
import com.lyy.uploadfile.VO.FileListVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @RequestMapping(value = "/all/{account}", method = RequestMethod.GET)
    public Result all(@PathVariable("account") String account){
        List<FileListVO> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            res.add(new FileListVO("刘永裕_" + i, new Date(), "-", account));
        }
        return Result.success("成功获取用户文件", res);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(@RequestParam("fileName") String fileName,
                         @RequestParam("page") int page,
                         @RequestParam("limit") int limit) {
        List<FileListVO> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            res.add(new FileListVO(i + "_刘永裕", new Date(), "-", "-"));
        }
        return Result.success("查询成功", res);
    }

}
