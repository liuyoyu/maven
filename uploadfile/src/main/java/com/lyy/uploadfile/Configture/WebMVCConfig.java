package com.lyy.uploadfile.Configture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("thymeleafViewResolver")
    ThymeleafViewResolver thymeleafViewResolver;

    @Value("http://${server.address}:${server.port}")
    String basePath;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            Map<String, String> map = new HashMap<>();
            map.put("basePath", basePath);
            thymeleafViewResolver.setStaticVariables(map);
        }
    }
}
