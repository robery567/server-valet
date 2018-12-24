package com.servervaletdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping(path="/home")
public class Home {
    @GetMapping(path= "/index")
    public String index(){
        return "dashboard";
    }
}
