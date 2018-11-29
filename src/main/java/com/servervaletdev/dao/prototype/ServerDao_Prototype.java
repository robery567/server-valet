package com.servervaletdev.dao.prototype;

import java.util.List;

import com.servervaletdev.model.Server;

public interface ServerDao_Prototype {
    void addServer(Server server);
    void updateServer(Server server);
    List<Server> listServers();
    Server getServerById(int id);
    void deleteServer(int id);
}

