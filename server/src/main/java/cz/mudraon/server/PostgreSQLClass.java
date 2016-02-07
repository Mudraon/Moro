/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mudraon.server;

import cz.mudraon.shared.Message;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Mutagen
 */
public class PostgreSQLClass {

    private final Logger logger = Logger.getLogger(PostgreSQLClass.class);
    private SessionFactory sessionFactory = null;

    public PostgreSQLClass() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            logger.error("Can't initialize session", e);
        }
    }
    
    private Session getSession() throws HibernateException {
        if (sessionFactory == null) {
            return null;
        }
        return sessionFactory.openSession();
    }

    public boolean saveHibernate(Message entity) {
        boolean result = false;
        Transaction tx = null;
        Session session = getSession();
        if (session == null) {
            return false;
        }
        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            result = true;
        } catch (RuntimeException e) {
            tx.rollback();
            result = false;
            logger.error("Can't save to DB", e);
        }
        return result;
    }
}
