package com.servervaletdev.repository;

import com.servervaletdev.model.Server;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository {
    /**
     * Gets the server details by a given user id
     * @param serverId the owner user id of the server
     * @return an User
     */
    Server getServerByUserId(Integer serverId);

    /**
     * Persists the server
     * @param server the server to persist
     */
    void save(Server server);
}

