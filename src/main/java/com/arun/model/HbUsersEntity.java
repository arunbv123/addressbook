package com.arun.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * Hibernate Entity for users table in database. This has the information regarding the users for addressbook in the database
 * Created by Arun on 2/28/2017.
 */

@javax.persistence.Entity
public class HbUsersEntity {
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int iduser;

    private String username;
    private String userpass;
    private String useremail;

    @javax.persistence.Id
    @javax.persistence.Column(name = "iduser", nullable = false)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "username", nullable = false, length = -1, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "userpass", nullable = false, length = -1)
    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "useremail", nullable = true, length = -1)
    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HbUsersEntity that = (HbUsersEntity) o;

        if (iduser != that.iduser) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (userpass != null ? !userpass.equals(that.userpass) : that.userpass != null) return false;
        if (useremail != null ? !useremail.equals(that.useremail) : that.useremail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iduser;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userpass != null ? userpass.hashCode() : 0);
        result = 31 * result + (useremail != null ? useremail.hashCode() : 0);
        return result;
    }
}

