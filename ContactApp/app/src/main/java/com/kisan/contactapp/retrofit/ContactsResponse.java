
package com.kisan.contactapp.retrofit;



import com.google.gson.annotations.SerializedName;
import com.kisan.contactapp.parcelable.Contact;

import java.util.List;

public class ContactsResponse
{
    @SerializedName("contacts")
    private List<Contact> contacts = null;


    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


}
