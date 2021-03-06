package com.servervaletdev.controller;

import com.servervaletdev.model.Server;
import com.servervaletdev.repository.provider.UserDetailProvider;
import com.servervaletdev.repository.provider.UserRepositoryProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final UserRepositoryProvider User;

    public HomeController(UserRepositoryProvider User) {
        this.User = User;
    }

    /**
     * Dashboard Action
     * @param model the data to send to the view
     * @return template name
     */
    @GetMapping(path= "/home/index")
    public String indexAction(Map<String, Object> model){
        UserDetailProvider currentSession = (UserDetailProvider) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentPrincipalName = currentSession.getUsername();
        List<Map<String, Object>> userServers = this.User.getUserServersByUserId(currentSession.getUserId());

        if (!userServers.isEmpty()) {
            String distributionName = currentSession.getServer().getDistributionName();
            Server serverDetails = currentSession.getServer();

            model.put("distributionName", distributionName.toLowerCase());
            model.put("serverDetails", serverDetails);
            model.put("usedStoragePercentage", serverDetails.getUsedStorageMemory()/serverDetails.getTotalStorageMemory()*100);
        }

        model.put("userServers", userServers);
        model.put("moduleName", "dashboard");
        model.put("name", currentPrincipalName);
        return "admin";
    }
}
