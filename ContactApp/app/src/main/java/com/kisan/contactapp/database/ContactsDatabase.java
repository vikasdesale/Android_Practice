package com.kisan.contactapp.database;

import com.kisan.contactapp.database.model.ColumnsContacts;
import com.kisan.contactapp.database.model.ColumnsSms;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Dell on 6/4/2017.
 */

@Database(version = ContactsDatabase.VERSION)
public class ContactsDatabase {
    public static final int VERSION = 1;

    //temporary table
    @Table(ColumnsContacts.class)
    public static final String MY_CONTACTS = "myContacts";

    //permanent table
    @Table(ColumnsSms.class)
    public static final String MY_SMS_SENT = "mySmsSent";


    private ContactsDatabase() {
    }
}