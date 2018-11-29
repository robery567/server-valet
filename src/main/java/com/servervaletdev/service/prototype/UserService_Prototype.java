package com.servervaletdev.service.prototype;

import java.util.List;

import com.servervaletdev.model.User;

public interface UserService_Prototype {
    void addUser(User u);
    void updateUser(User u);
    List<User> listUsers();
    User getUserById(int id);
    void deleteUser(int id);
}

