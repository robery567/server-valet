package com.servervaletdev.controller;

import com.servervaletdev.model.Server;
import com.servervaletdev.repository.ServerRepository;
import com.servervaletdev.repository.UserRepository;
import com.servervaletdev.model.User;
import com.servervaletdev.repository.provider.UserDetailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AccountController {

    private UserRepository UserRepository;

    private ServerRepository ServerRepository;

    public AccountController(UserRepository userRepository, ServerRepository serverRepository) {
        this.UserRepository = userRepository;
        this.ServerRepository = serverRepository;
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

            UserRepository.save(newUser);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("content", "register");
            return "account";
        }

        return "redirect:/account/login?success";
    }

    /**
     * Shows the Set-up page
     * @param model the data to send to the view
     * @return the template name
     */
    @GetMapping("/account/setup")
    public String setUpAction(Map<String, Object> model) {
        model.put("content", "setup");
        return "account";
    }


    /**
     * Performs the setup and shows errors if any
     * @param model the data to send to the view
     * @param hostname the server hostname
     * @param username the server username
     * @param password the server password
     * @param port the server port
     * @return template name
     */
    @PostMapping("/account/setup")
    public String setupAction(
            Map<String, Object> model,
            @RequestParam("hostname") String hostname,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("port") Integer port
    ) {

        try {
            UserDetailProvider currentSession = (UserDetailProvider) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userId = currentSession.getIntegerUserId();

            Server newServer = new Server(hostname, username, password, port, userId);
            System.out.println("created object");
            this.ServerRepository.save(newServer);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("content", "setup");
            System.out.println("esec");
            return "account";
        }

        return "redirect:/account/setup/success";
    }
}