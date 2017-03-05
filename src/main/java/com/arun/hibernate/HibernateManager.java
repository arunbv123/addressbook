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
 *
 * This class manages the data flow between the java classes and Hibernate entity
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

    /**
     * This function is used to get the user data from the sql database.
     *
     * @param username - username of the user
     * @return - returns the Hibernate entity for user that has data from the sql database
     */
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

    /**
     * This function gets the list of contacts associated with the user
     * @param userId - user id of the user
     * @return - returns list of contacts associated with the user.
     */


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

    /**
     * This function updates the hibernate enity in the database
     * @param object - The Hibernate entity to be updated in database
     * @return - True if the update was successful False if unsuccessful
     */

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

    /**
     * This function deletes the contact from database
     * @param id - id of the contact that needs to be deleted
     * @return - True if the deletion was successful False if unsuccessful
     */

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

    /**
     * This function creates the user in database
     * @param object - Hibernate entity for users with the data to create a new user
     * @return - True if the deletion was successful False if unsuccessful
     */

    public static boolean createUser (Object object) {

        final Session session = HibernateManager.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(object);
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
