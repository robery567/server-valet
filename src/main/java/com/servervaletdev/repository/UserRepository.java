package com.servervaletdev.repository;

import com.servervaletdev.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {
    /**
     * Gets the user with username = {username}
     * @param username the username of the user to find
     * @return an User
     */
    User getIdByUsername(String username);

    /**
     * Gets the user servers by a given user Id
     * @param userId The user id
     */
    List<Map<String, Object>> getUserServersByUserId(String userId);

    /**
     * Persists the user and encodes its password
     * @param user the user to persist
     */
    void save(User user);
}
