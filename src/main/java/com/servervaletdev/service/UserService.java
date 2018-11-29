package com.servervaletdev.service;


import java.util.List;

import com.servervaletdev.service.prototype.UserService_Prototype;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servervaletdev.dao.UserDao;
import com.servervaletdev.model.User;

@Service
public class UserService implements UserService_Prototype {

    private UserDao UserDao;

    public void setUserDao(UserDao userDao) {
        this.UserDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User u) {
        this.UserDao.addUser(u);
    }

    @Override
    @Transactional
    public void updateUser(User u) {
        this.UserDao.updateUser(u);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.UserDao.listUsers();
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return this.UserDao.getUserById(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        this.UserDao.deleteUser(id);
    }
}