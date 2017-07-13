package com.kisan.contactapp.dbtest;

import android.content.ContentValues;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kisan.contactapp.activities.MainActivity;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.database.model.ColumnsSms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Dell on 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DbInsertSms {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDb() throws IOException {
        ContentValues values = new ContentValues(6);
        values.put(ColumnsSms.FIRSTNAME, "test");
        values.put(ColumnsSms.LASTNAME, "test");
        values.put(ColumnsSms.MOBILE_NO, "4545");
        values.put(ColumnsSms.MSG_CONTENT, "566767");
        values.put(ColumnsSms.DATE_SENT, System.currentTimeMillis());
        values.put(ColumnsSms.OTP_GENERATE, "612345");

        assertTrue(getContext().getContentResolver().insert(
                ContactsProvider.MySmsSent.CONTENT_URI_SMS_SENT, values)!=null);
    }
}
