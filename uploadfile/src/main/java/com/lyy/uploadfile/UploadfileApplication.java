package com.lyy.uploadfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyy.uploadfile.Mapper")
public class UploadfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadfileApplication.class, args);
    }

}
