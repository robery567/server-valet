package com.servervaletdev.controller;

import com.servervaletdev.repository.UserRepository;
import com.servervaletdev.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AccountController {

    private UserRepository UserRepository;

    public AccountController(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    /**
     * Show the login form and some predefined messages if any
     * @param model data to send to the view
     * @param error true if there is an error
     * @param success true if account was created
     * @param logout true if logout successful
     * @return template name
     */
    @RequestMapping("/account/login")
    public String showLoginAction(
            Map<String, Object> model,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "success", required = false) String success,
            @RequestParam(name = "logout", required = false) String logout
    ) {
        if (error != null) {
            model.put("error", "Authentication failed");
        }

        if (success != null) {
            model.put("success", "Account created");
        }

        if (logout != null) {
            model.put("logout", "Logout successful");
        }

        model.put("content", "login");
        return "account";
    }

    /**
     * Shows the registration page
     * @param model the data to send to the view
     * @return the template name
     */
    @GetMapping("/account/register")
    public String showRegisterAction(Map<String, Object> model) {
        model.put("content", "register");
        return "account";
    }

    /**
     * Performs the registration and shows errors if any
     * @param model the data to send to the view
     * @param username the users nickname
     * @param password the users password
     * @param email the users email address
     * @return template name
     */
    @PostMapping("/account/register")
    public String doRegisterAction(
            Map<String, Object> model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
            ) {

        try {
            User newUser = new User(username, email, password);
            System.out.println("created object!");

            UserRepository.save(newUser);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            System.out.println("USERNAME: " + username + ", PASSWORD: " + password + ", EMAIL: " + email);
            model.put("content", "register");
            return "account";
        }

        return "redirect:/account/login?success";
    }
}