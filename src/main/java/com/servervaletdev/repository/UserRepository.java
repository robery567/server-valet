package com.servervaletdev.repository;

import com.servervaletdev.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    /**
     * Gets the user with email = {email}
     * @param email the email of the user to find
     * @return an User
     */
    User getByEmail(String email);

    /**
     * Persists the user and encodes its password
     * @param user the user to persist
     */
    void save(User user);
}
