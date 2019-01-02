package com.servervaletdev.controller;

import com.servervaletdev.model.Server;
import com.servervaletdev.repository.provider.UserDetailProvider;
import com.servervaletdev.repository.provider.UserRepositoryProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ServerController {
    private final UserRepositoryProvider User;

    public ServerController(UserRepositoryProvider User) {
        this.User = User;
    }


    /**
     * Shows the user server details
     * @return the template name
     */
    @GetMapping("/user/server_details")
    public Server userServerDetailsAction() {
        UserDetailProvider currentSession = (UserDetailProvider) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> userServers = this.User.getUserServersByUserId(currentSession.getUserId()).get(0);

        Server Server = this.User.getServerObjectFromMap(userServers);

        Server.setUserId(currentSession.getIntegerUserId());

        return Server;
    }

}
