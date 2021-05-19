package com.lyy.uploadfile.Controller;

import com.lyy.uploadfile.Entry.DownloadUF;
import com.lyy.uploadfile.Entry.FileUF;
import com.lyy.uploadfile.Entry.UserUF;
import com.lyy.uploadfile.Service.FileService;
import com.lyy.uploadfile.Service.LoginService;
import com.lyy.uploadfile.Utils.FileUtil;
import com.lyy.uploadfile.Utils.Message;
import com.lyy.uploadfile.Utils.PageData;
import com.lyy.uploadfile.Utils.Result;
import com.lyy.uploadfile.VO.FileListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${uploadfile.file.path}")
    private String filePath;        //文件存放路径

    FileService fileService;

    LoginService loginService;

    private final ReentrantLock lock;

    @Autowired
    public FileController(FileService fileService,
                          LoginService loginService) {
        this.fileService = fileService;
        this.loginService = loginService;
        lock = new ReentrantLock();
    }

    @RequestMapping(value = "/all/{account}", method = RequestMethod.GET)
    public Result all(@PathVariable("account") String account,
                      @RequestParam("page") int page,
                      @RequestParam("limit") int limit){
        Message all = fileService.getAllByPage(account, page, limit);
        List<FileUF> files = (ArrayList)all.res();
        int count = fileService.count(account);
        return PageData.success(files, count);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(@RequestParam("fileName") String fileName,
                         @RequestParam("page") int page,
                         @RequestParam("limit") int limit) {
        List<FileListVO> res = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            //res.add(new FileListVO(((page - 1) * limit +i) + "_刘永裕", new Date(), "-", "-"));
        }
        return PageData.success(res, 50);
    }

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("不能上传空文件");
        }
        UserUF loginInfo = loginService.getLoginInfo();
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0, fileName.lastIndexOf('.'));
        String suffixName = fileName.substring(fileName.lastIndexOf('.') + 1);
        String newFileName = UUID.randomUUID() + "";
        File t = new File(filePath + newFileName + "." + suffixName);
        if (t.getParentFile() != null && !t.getParentFile().exists()) {
            t.getParentFile().mkdir();
        }
        try {
            file.transferTo(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUF fileUF = new FileUF();
        fileUF.setFileName(name);
        fileUF.setFileType(suffixName);
        fileUF.setFileSize(FileUtil.fileSize2String(file.getSize()));
        fileUF.setStoreName(newFileName);
        fileUF.setReviseAccount("");//todo 审核人
        fileUF.setUploadName(loginInfo.getName());
        fileUF.setUploadAccount(loginInfo.getAccount());
        fileUF.setStatus(1);//todo 审核
        fileUF.setUploadDate(new Date());
        fileUF.setLocatePath(filePath);
        fileService.create(fileUF);
        return Result.success(name + " 文件上传成功", fileUF);
    }

    /**
     * 多文件上传（预留）
     * @param files
     * @return
     */
    @PostMapping("/upload/multi")
    public Result upload(@RequestParam("files") MultipartFile[] files) {
        StringBuilder msg = new StringBuilder();
        UserUF loginInfo = loginService.getLoginInfo();
        for (int i=0; i<files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) {
                if (i != 0) msg.append("\n");
                msg.append("第"+i+"个文件上传失败");
                continue;
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf('.') + 1);
            String newFileName = UUID.randomUUID() + suffixName;
            File t = new File(filePath + newFileName);
            if (t.getParentFile() != null && !t.getParentFile().exists()) {
                t.getParentFile().mkdir();
            }
            boolean fail = false;
            try {
                file.transferTo(t);
            } catch (IOException e) {
                e.printStackTrace();
                fail = true;
            }
            if (!fail) {
                FileUF fileUF = new FileUF();
                fileUF.setFileName(fileName);
                fileUF.setFileType(suffixName);
                fileUF.setFileSize(FileUtil.fileSize2String(file.getSize()));
                fileUF.setStoreName(newFileName);
                fileUF.setReviseAccount("");
                fileUF.setUploadName(loginInfo.getName());
                fileUF.setUploadAccount(loginInfo.getAccount());
                fileUF.setStatus(1);
                fileUF.setUploadDate(new Date());
                fileUF.setLocatePath(filePath);
                fileService.create(fileUF);
            }
        }
        return Result.success("".equals(msg.toString()) ? "文件上传成功" : msg.toString());
    }

    @GetMapping("download/{fileId}")
    public Result download(@PathVariable("fileId") long id, HttpServletResponse response) throws IOException {
        Message one = fileService.getOne(id);
        if (!one.isSuccess()) {
            return Result.error(one.msg());
        }
        UserUF loginInfo = loginService.getLoginInfo();
        lock.lock();
        try{
            FileUF fileuf = (FileUF)one.res();
            fileuf.setDownloadCount(fileuf.getDownloadCount() + 1);
            File file = new File(fileuf.getLocatePath() + "/" + fileuf.getStoreName() + "." + fileuf.getFileType());
            if(file.exists()){ //判断文件父目录是否存在
                fileService.updateDownloadCount(fileuf.getId(), fileuf.getDownloadCount()); //更新下载次数
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                // response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" +
                        java.net.URLEncoder.encode(fileuf.getStoreName() + "." + fileuf.getFileType(),"UTF-8"));
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

                DownloadUF duf = new DownloadUF();
                duf.setFileId(id);
                duf.setAccount(loginInfo.getAccount());
                duf.setDownloadDate(new Date());
                fileService.insertDownloadFile(duf);

                return null;
            }
        }finally {
            lock.unlock();
        }

        return Result.error("文件不存在");
    }

    /**
     * 视频播放
     * @param id
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/video")
    public Result videoView(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response) {
        Message one = fileService.getOne(id);
        if (!one.isSuccess()) {
            return Result.error(one.msg());
        }

        FileUF fileuf = (FileUF)one.res();
        File f = new File(fileuf.getLocatePath() + "/" + fileuf.getStoreName() + "." + fileuf.getFileType());

        String fileName = f.getName();

        String agent = request.getHeader("User-Agent").toUpperCase();
        InputStream fis = null;
        OutputStream os = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(f.getPath()));
            byte[] buffer;
            buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();
            //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
            if(agent.indexOf("FIREFOX") != -1){//火狐浏览器
                response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
            }else{//其他浏览器
                response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "UTF-8"));
            }
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Length", "" + f.length());
            response.setContentType("video/mpeg4;video/ts");
            os = response.getOutputStream();
            os.write(buffer);
        }catch(Exception e){
            logger.error("视频播放出现异常：{}", e.getMessage());
        } finally{
            //关闭流
            try {
                if(fis != null){
                    fis.close();
                }
                if(os != null){
                    os.flush();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                logger.error("视频播放发送失败：{}", e.getMessage());
            }
        }
        return null;
    }

    /**
     *  pdf预览
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/pdf/{id}")
    public Result pdfView(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        Message one = fileService.getOne(id);
        if (!one.isSuccess()) {
            return Result.error("文件不存在");
        }
        FileUF res = (FileUF)one.res();
        File file = new File(res.getLocatePath() + "/" + res.getStoreName() + "." + res.getFileType());
        try {
            response.setContentType("application/pdf");
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            while ((in.read(b))!=-1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            logger.error("PDF预览出现问题：{}", e.getMessage());
        }
        return null;
    }
}
