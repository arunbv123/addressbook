package com.arun.hibernate;


import com.arun.model.HbContactsEntity;
import com.arun.model.HbUsersEntity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.List;

/**
 * Created by Arun on 2/28/2017.
 */
public class HibernateManager {

    private static final Configuration configuration = new Configuration();
    private static final SessionFactory sessionFactory;


    static {
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static HbUsersEntity getUserByUsername(String username) {
        final Session session = HibernateManager.getSessionFactory().openSession();
        Transaction transaction = null;
        HbUsersEntity user = new HbUsersEntity();
        List<HbUsersEntity> list = null;
        try {
            transaction = session.beginTransaction();
           // list = session.createQuery("from HbUsersEntity where username = '" + username + "' ").list();
            Query query = session.createQuery("from HbUsersEntity where username= :username" );
            query.setParameter("username",username);
            list = query.list();
            if (list.isEmpty()) {
                user = null;
            } else {
                user = list.get(0);
            }
            transaction.commit();
        }catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace(); // todo: log to file instead
        } finally {
            session.close();
        }




        return user;
    }


    public static List<HbContactsEntity> getContactList(int userId) {

        final Session session = HibernateManager.getSessionFactory().openSession();
        Transaction transaction = null;
        List<HbContactsEntity> list = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from HbContactsEntity where iduser= :user" );
            query.setParameter("user",userId);
            list = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace(); // todo: log to file instead
        } finally {
            session.close();
        }
        return list;
    }

    public static boolean update(Object object) {

        final Session session = HibernateManager.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.saveOrUpdate(object);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace(); // todo: log to file instead
            return false;
        } finally {
            session.close();
        }
    }

    public static boolean deleteContact(int id)
    {

        final Session session = HibernateManager.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            HbContactsEntity myObject;
            myObject = session.load(HbContactsEntity.class,id);
            session.delete(myObject);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace(); // todo: log to file instead
            return false;
        } finally {
            session.close();
        }




    }


}
