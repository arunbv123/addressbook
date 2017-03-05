package com.arun.model;

import com.arun.hibernate.HibernateManager;

import java.util.List;

/**
 * /Class that acts buffer between hibernate manager and java class that needs the data
 * Created by Arun on 3/2/2017.
 */
public class ContactDataAccess {


    public List<HbContactsEntity> getAllContacts(int userid) {

        return HibernateManager.getContactList(userid);

    }

    public boolean update(HbContactsEntity contact) {
        return HibernateManager.update(contact);
    }
}
