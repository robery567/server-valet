package com.servervaletdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
@Controller
@RequestMapping(path="/home")
public class HomeController {
    /**
     * Dashboard Action
     * @param model the data to send to the view
     * @return template name
     */
    @GetMapping(path= "/index")
    public String index(Map<String, Object> model){
        model.put("moduleName", "dashboard");
        return "account";
    }
}
