package com.servervaletdev.service;

import java.util.List;

import com.servervaletdev.model.ServerLog;
import com.servervaletdev.service.prototype.ServerLogService_Prototype;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servervaletdev.dao.ServerLogDao;

@Service
public class ServerLogService implements ServerLogService_Prototype {

    private ServerLogDao ServerLogDao;

    public void setServerLogDao(ServerLogDao serverLogDao) {
        this.ServerLogDao = serverLogDao;
    }

    @Override
    @Transactional
    public void addServerLog(ServerLog server) {
        this.ServerLogDao.addServerLog(server);
    }


    @Override
    @Transactional
    public List<ServerLog> listServerLogs() {
        return this.ServerLogDao.listServerLogs();
    }

    @Override
    @Transactional
    public ServerLog getServerLogById(int id) {
        return this.ServerLogDao.getServerLogById(id);
    }

    @Override
    @Transactional
    public void deleteLog(int id) {
        this.ServerLogDao.deleteLog(id);
    }
}