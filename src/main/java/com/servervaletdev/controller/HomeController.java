package com.servervaletdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.Map;

//@RestController
@Controller
public class HomeController {
    /**
     * Dashboard Action
     * @param model the data to send to the view
     * @return template name
     */
    @GetMapping(path= "/home/index")
    public String indexAction(Map<String, Object> model, Principal principal){
        String currentPrincipalName = principal.getName();

        model.put("moduleName", "dashboard");
        model.put("name", currentPrincipalName);
        return "admin";
    }
}
