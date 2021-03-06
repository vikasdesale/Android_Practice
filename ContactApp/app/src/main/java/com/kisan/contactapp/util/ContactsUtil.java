package com.kisan.contactapp.util;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.database.model.ColumnsContacts;
import com.kisan.contactapp.database.model.Contact;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dell on 7/12/2017.
 */

public class ContactsUtil {

    private static final String TAG = "vikas";
    Cursor c;
    int count = 0;

    public static void CacheDelete(Context context) {

        try {

            context.getContentResolver().delete(ContactsProvider.MyContacts.CONTENT_URI,
                    null, null);
        } catch (Exception e) {
        }

    }

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }


    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }


    public static int insertSmsSent(Context context, ContentValues values) {
        if (context.getContentResolver().insert(
                ContactsProvider.MySmsSent.CONTENT_URI_SMS_SENT, values) != null) {
            Log.e(TAG, "Inserted new task");
        } else {
            Log.e(TAG, "Error inserting new task");
        }
        return 0;
    }

    // convert date to format as 5 hours ago
    public static String manipulateDateFormat(String post_date) {

        Date date = new Date(Long.parseLong(post_date));
        return DateFormat.getDateInstance().format(date);

    }

    public int insertData(Context context, ArrayList<Contact> contactArrayList) {
        c = null;
        int flag = 0;
        ContentProviderOperation.Builder builder = null;
        try {
            ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(contactArrayList.size());

            for (Contact contact : contactArrayList) {
                if (contact.getFirstname() != null && contact.getLastname() != null && contact.getPhone() != null) {
                    builder = ContentProviderOperation.newInsert(
                            ContactsProvider.MyContacts.CONTENT_URI);
                }
                if (builder != null) {
                    builder.withValue(ColumnsContacts.FIRSTNAME, contact.getFirstname());
                    builder.withValue(ColumnsContacts.LASTNAME, contact.getLastname());
                    builder.withValue(ColumnsContacts.MOBILE_NO, contact.getPhone());
                    batchOperations.add(builder.build());
                }

            }

            context.getContentResolver().applyBatch(ContactsProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {

        }
        try {

            if (c != null || !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
        }

        return 0;
    }
}
