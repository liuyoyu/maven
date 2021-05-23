package com.lyy.uploadfile;

import com.lyy.uploadfile.Utils.LocalCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UploadfileApplicationTests {

    @Test
    void contextLoads() {
        try {
            LocalCache.set("test", "捧沙卡拉卡", 3000);
            System.out.println(LocalCache.get("test"));
            Thread.currentThread().sleep(3000);
            System.out.println("再次取出："+LocalCache.get("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
