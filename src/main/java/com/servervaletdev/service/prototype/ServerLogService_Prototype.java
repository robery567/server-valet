package com.servervaletdev.service.prototype;

import com.servervaletdev.model.ServerLog;

import java.util.List;

public interface ServerLogService_Prototype {
    void addServerLog(ServerLog log);
    List<ServerLog> listServerLogs();
    ServerLog getServerLogById(int id);
    void deleteLog(int id);
}
