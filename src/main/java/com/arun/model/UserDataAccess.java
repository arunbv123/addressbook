package com.arun.model;


import com.arun.hibernate.HibernateManager;

/**
 * Created by Arun on 2/28/2017.
 */
public class UserDataAccess  {
//    public Boolean saveUser(HbUsersEntity user) {
//
//        Boolean saveSuccessful = HibernateUtil.save(user);
//        return saveSuccessful;
//    }
//
//    public HbUsersEntity updateUser(HbUsersEntity u) {
//        HibernateUtil.update(u);
//        return u;
//    }
//
//    public void deleteUser(HbUsersEntity u) {
//        HibernateUtil.delete(u);
//    }

    public HbUsersEntity getUserByUsername(String username) {

        return (HbUsersEntity) HibernateManager.getUserByUsername(username);
    }



}
