package com.servervaletdev.dao;

import java.util.List;

import com.servervaletdev.dao.prototype.UserDao_Prototype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.servervaletdev.model.User;

@Repository
public class UserDao implements UserDao_Prototype {
    //private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
    }

    @Override
    public void updateUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(u);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery("from user").list();
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();

        return session.load(User.class, id);
    }

    @Override
    public void deleteUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User u = session.load(User.class, id);

        if(null != u){
            session.delete(u);
        }
    }
}