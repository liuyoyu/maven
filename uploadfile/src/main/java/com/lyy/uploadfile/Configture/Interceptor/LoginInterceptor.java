package com.lyy.uploadfile.Configture.Interceptor;

import com.lyy.uploadfile.Configture.SystemParameters;
import com.lyy.uploadfile.Utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        //访问的cookie中没有带userInfo字段，则判断为无权限
        boolean hasCookie = false;
        for (Cookie cookie : cookies) {
            if (SystemParameters.COOKIE_USR_INFORMATION.equals(cookie.getName())) {
                hasCookie = true;
                String value = cookie.getValue();
                //验证有效
                if (JWTUtil.verify(value)) {
                    return true;
                }else{
                    //令cookie失效
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        // 跳转到登录界面
        if (hasCookie) {
            /*
            token失效或token校验出错，需要进行重新登录
             */
            response.sendRedirect("/uploadFile/loginPage?status=0");
        } else {
            /*
            第一次登录
             */
            response.sendRedirect("/uploadFile/loginPage?status=1");
        }
        return false;
    }
}
