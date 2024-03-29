package main.java.com.lyy.uploadfile.Configture;

import main.java.com.lyy.uploadfile.Utils.Result;
import main.java.com.lyy.uploadfile.Configture.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("thymeleafViewResolver")
    ThymeleafViewResolver thymeleafViewResolver;

    @Value("http://${server.address}:${server.port}")
    String basePath;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            Map<String, Object> map = new HashMap<>();
//            map.put("basePath", basePath);
            map.put("respSuccess", String.valueOf(Result.Type.SUCCESS.value()));
            map.put("userId", SystemBaseRoles.USER);
            map.put("adminId", SystemBaseRoles.ADMIN);
            thymeleafViewResolver.setStaticVariables(map);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/signUp", "/uploadFile/loginPage", "/login", "/error","/favicon.ico", "/login/**", "/img/**", "/layui/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/signUp").setViewName("register");
    }
}
