package com.ssf.day15lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

    @Controller
    @RequestMapping("/")
    public class IndexController {
        public String getIndex() {
            return "index";
        }
    }
