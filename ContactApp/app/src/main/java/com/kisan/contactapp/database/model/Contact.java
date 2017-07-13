package com.kisan.contactapp.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.kisan.contactapp.util.ContactsUtil.getColumnLong;
import static com.kisan.contactapp.util.ContactsUtil.getColumnString;

public class Contact implements Parcelable {

    public final static Creator<Contact> CREATOR = new Creator<Contact>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Contact createFromParcel(Parcel in) {
            Contact instance = new Contact(null);
            instance.firstname = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastname = ((String) in.readValue((String.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Contact[] newArray(int size) {
            return (new Contact[size]);
        }

    };
    private final static long serialVersionUID = 9069196970472270690L;
    public final Long id;
    private String firstname;
    private String lastname;
    private String phone;

    public Contact(Cursor cursor) {
        this.id = getColumnLong(cursor, ColumnsContacts._ID);
        this.firstname = getColumnString(cursor, ColumnsContacts.FIRSTNAME);
        this.lastname = getColumnString(cursor, ColumnsContacts.LASTNAME);
        this.phone = getColumnString(cursor, ColumnsContacts.MOBILE_NO);

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstname);
        dest.writeValue(lastname);
        dest.writeValue(phone);
    }

    public int describeContents() {
        return 0;
    }

}
