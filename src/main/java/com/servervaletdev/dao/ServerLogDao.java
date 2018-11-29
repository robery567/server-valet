package com.servervaletdev.dao;

import java.util.List;

import com.servervaletdev.dao.prototype.ServerLogDao_Prototype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.servervaletdev.model.ServerLog;

@Repository
public class ServerLogDao implements ServerLogDao_Prototype {
    //private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addServerLog(ServerLog log) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(log);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServerLog> listServerLogs() {
        Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery("from server").list();
    }

    @Override
    public ServerLog getServerLogById(int id) {
        Session session = this.sessionFactory.getCurrentSession();

        return session.load(ServerLog.class, id);
    }

    @Override
    public void deleteLog(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        ServerLog server = session.load(ServerLog.class, id);

        if(null != server){
            session.delete(server);
        }
    }
}