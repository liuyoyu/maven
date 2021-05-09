package com.lyy.uploadfile.Controller;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {
    protected ModelAndView modelAndView;

    public BaseController() {
        this.modelAndView = new ModelAndView();
    }
}
