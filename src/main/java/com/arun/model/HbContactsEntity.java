package com.arun.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * Created by Arun on 2/27/2017.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "contacts", schema = "AddressBook", catalog = "")
public class HbContactsEntity {
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int idcontact;
    private int iduser;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;

    @javax.persistence.Id
    @javax.persistence.Column(name = "idcontact", nullable = false)
    public int getIdcontact() {
        return idcontact;
    }

    public void setIdcontact(int idcontact) {
        this.idcontact = idcontact;
    }

    @javax.persistence.Id
    @javax.persistence.Column(name = "iduser", nullable = false)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "contact_name", nullable = true, length = -1)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "contact_email", nullable = true, length = -1)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "contact_phone", nullable = true, length = 20)
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "contact_address", nullable = true, length = -1)
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HbContactsEntity that = (HbContactsEntity) o;

        if (idcontact != that.idcontact) return false;
        if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null) return false;
        if (contactEmail != null ? !contactEmail.equals(that.contactEmail) : that.contactEmail != null) return false;
        if (contactPhone != null ? !contactPhone.equals(that.contactPhone) : that.contactPhone != null) return false;
        if (contactAddress != null ? !contactAddress.equals(that.contactAddress) : that.contactAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idcontact;
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
        result = 31 * result + (contactAddress != null ? contactAddress.hashCode() : 0);
        return result;
    }
}
