package com.servervaletdev.dao.prototype;

import java.util.List;

import com.servervaletdev.model.ServerLog;

public interface ServerLogDao_Prototype {
    void addServerLog(ServerLog log);
    List<ServerLog> listServerLogs();
    ServerLog getServerLogById(int id);
    void deleteLog(int id);
}