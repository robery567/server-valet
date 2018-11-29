package com.servervaletdev.service.prototype;

import com.servervaletdev.model.Server;

import java.util.List;

public interface ServerService_Prototype {
    void addServer(Server server);
    void updateServer(Server server);
    List<Server> listServers();
    Server getServerById(int id);
    void deleteServer(int id);
}
