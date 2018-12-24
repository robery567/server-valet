package com.servervaletdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path="/")
public class IndexController {
    @GetMapping(value = "/")
    public String index() {
        return "redirect:/home/index";
    }
}