package com.ssf.day15lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssf.day15lecture.model.Item;
    @Controller
    @RequestMapping("/")
    public class IndexController {
        public String getIndex(Model model) {
            model.addAttribute("item", new Item());
            return "index";
        }
    }
