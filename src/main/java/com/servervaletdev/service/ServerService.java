package com.servervaletdev.service;

import java.util.List;

import com.servervaletdev.service.prototype.ServerService_Prototype;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servervaletdev.dao.ServerDao;
import com.servervaletdev.model.Server;

@Service
public class ServerService implements ServerService_Prototype {

    private ServerDao ServerDao;

    public void setServerDao(ServerDao serverDao) {
        this.ServerDao = serverDao;
    }

    @Override
    @Transactional
    public void addServer(Server server) {
        this.ServerDao.addServer(server);
    }

    @Override
    @Transactional
    public void updateServer(Server server) {
        this.ServerDao.updateServer(server);
    }

    @Override
    @Transactional
    public List<Server> listServers() {
        return this.ServerDao.listServers();
    }

    @Override
    @Transactional
    public Server getServerById(int id) {
        return this.ServerDao.getServerById(id);
    }

    @Override
    @Transactional
    public void deleteServer(int id) {
        this.ServerDao.deleteServer(id);
    }
}