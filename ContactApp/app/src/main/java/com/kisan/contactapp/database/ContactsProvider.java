package com.kisan.contactapp.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Dell on 6/4/2017.
 */

@ContentProvider(authority = ContactsProvider.AUTHORITY, database = ContactsDatabase.class)
public class ContactsProvider {

    public static final String AUTHORITY = "com.kisan.contactapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String MY_CONTACTS = "myContact";
        String MY_SMS_SENT = "mySmsSent";
    }

    @TableEndpoint(table = ContactsDatabase.MY_CONTACTS)
    public static class MyContacts {
        @ContentUri(
                path = Path.MY_CONTACTS,
                type = "vnd.android.cursor.dir/myContacts",
                defaultSort = ColumnsContacts._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.MY_CONTACTS);
        @InexactContentUri(
                path = Path.MY_CONTACTS + "/#",
                name = "_ID",
                type = "vnd.android.cursor.dir/myContacts",
                whereColumn = ColumnsContacts._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/myContacts/" + id);
        }

    }

    @TableEndpoint(table = ContactsDatabase.MY_SMS_SENT)
    public static class MySmsSent {
        @ContentUri(
                path = Path.MY_SMS_SENT,
                type = "vnd.android.cursor.dir/mySmsSent",
                defaultSort = ColumnsSms.DATE_SENT + " ASC")
        public static final Uri CONTENT_URI_SMS_SENT = buildUri(Path.MY_SMS_SENT);

    }
}
