package com.lyy.uploadfile.Utils;

import org.springframework.http.HttpCookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpUtil {

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    public static void setCookie(String key, String value, int second){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(second);
        String contextPath = getRequest().getContextPath();
        if ("".equals(contextPath)) {
            contextPath = "/";
        }
        cookie.setPath(contextPath);
        getResponse().addCookie(cookie);
    }

    public static Cookie getCookieByName(String name){
        Cookie[] cookies = getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}
