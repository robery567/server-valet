package com.servervaletdev.dao;

import java.util.List;

import com.servervaletdev.dao.prototype.ServerDao_Prototype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.servervaletdev.model.Server;

@Repository
public class ServerDao implements ServerDao_Prototype {
    //private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addServer(Server server) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(server);
    }

    @Override
    public void updateServer(Server server) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(server);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Server> listServers() {
        Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery("from server").list();
    }

    @Override
    public Server getServerById(int id) {
        Session session = this.sessionFactory.getCurrentSession();

        return session.load(Server.class, id);
    }

    @Override
    public void deleteServer(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Server server = session.load(Server.class, id);

        if(null != server){
            session.delete(server);
        }
    }
}