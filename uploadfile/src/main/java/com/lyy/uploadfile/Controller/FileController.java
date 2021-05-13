package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Utils.PageData;
import com.lyy.uploadfile.Utils.Result;
import com.lyy.uploadfile.VO.FileListVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Value("${uploadfile.file.path}")
    private String filePath;

    @RequestMapping(value = "/all/{account}", method = RequestMethod.GET)
    public Result all(@PathVariable("account") String account,
                      @RequestParam("page") int page,
                      @RequestParam("limit") int limit){
        List<FileListVO> res = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            res.add(new FileListVO("刘永裕_" + ((page - 1) * limit + i), new Date(), "-", account));
        }
        return PageData.success(res, 100);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(@RequestParam("fileName") String fileName,
                         @RequestParam("page") int page,
                         @RequestParam("limit") int limit) {
        List<FileListVO> res = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            res.add(new FileListVO(((page - 1) * limit +i) + "_刘永裕", new Date(), "-", "-"));
        }
        return PageData.success(res, 50);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile[] files) {
        StringBuilder msg = new StringBuilder();
        for (int i=0; i<files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) {
                if (i != 0) msg.append("\n");
                msg.append("第"+i+"个文件上传失败");
                continue;
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf('.'));
            String newFileName = UUID.randomUUID() + suffixName;
            File t = new File(filePath + newFileName);
            if (t.getParentFile() != null && !t.getParentFile().exists()) {
                t.getParentFile().mkdir();
            }
            try {
                file.transferTo(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success("".equals(msg.toString()) ? "文件上传成功" : msg.toString());
    }

    @GetMapping("download/{fileName}")
    public Result download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        File file = new File(filePath + fileName);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(fileName,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            bis.close();
            fis.close();
            return null;
        }
        return Result.error("文件不存在");
    }
}
