package com.lyy.Util;

import com.lyy.Configer.GenerateCodeConfiger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件配置
 * author liuyongyu
 * date 2021/7/31
 */
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Map<String, PropCache> propCacheHashMap = new HashMap<>();

    private static final String fileName = GenerateCodeConfiger.FILENAME;

    public static String get(String key) {
        return get(fileName, key);
    }

    public static String get(String name, String key) {
        return get(name, key, null);
    }

    public static String get(String name, String key, String defaultValue) {
        if (!name.endsWith(".properties")) {
            logger.warn("传入文件不为properties格式");
            name += ".properties";
        }
        PropCache propCache = propCacheHashMap.get(name);
        if (propCache == null) {
            propCache = new PropCache(name);
        }
        String value = propCache.get(key, defaultValue);
        return value.trim();
    }


    private static class PropCache {
        private String fileName;

        private long lastModify;

        private Properties properties;

        PropCache(String fileName) {
            FileInputStream inputStream = null;
            try {
                File file = new File(fileName);
                inputStream = new FileInputStream(file);
                this.properties = new Properties();
                this.properties.load(inputStream);
                this.fileName = file.getAbsolutePath();
                this.lastModify = file.lastModified();
            } catch (IOException e) {
                logger.info("初始化配置文件失败", e);
            }finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String get(String key) {
            return get(key, null);
        }

        public String get(String key, String defaultVal) {
            return this.properties.getProperty(key, defaultVal);
        }
    }
}

