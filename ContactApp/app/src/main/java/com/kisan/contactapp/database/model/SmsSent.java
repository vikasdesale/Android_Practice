package com.kisan.contactapp.database.model;

import android.database.Cursor;

import static com.kisan.contactapp.util.ContactsUtil.getColumnLong;
import static com.kisan.contactapp.util.ContactsUtil.getColumnString;

/**
 * Created by Dell on 7/12/2017.
 */

public class SmsSent {
    public final Long id;
    private String firstname;
    private String lastname;
    private String phone;
    private String smsContent;
    private String date_time;
    private String otp;

    public SmsSent(Cursor cursor) {
        this.id = getColumnLong(cursor, ColumnsSms._ID);
        this.firstname = getColumnString(cursor, ColumnsSms.FIRSTNAME);
        this.lastname = getColumnString(cursor, ColumnsSms.LASTNAME);
        this.phone = getColumnString(cursor, ColumnsSms.MOBILE_NO);
        this.smsContent = getColumnString(cursor, ColumnsSms.MSG_CONTENT);
        this.date_time = getColumnString(cursor, ColumnsSms.DATE_SENT);
        this.otp = getColumnString(cursor, ColumnsSms.OTP_GENERATE);


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

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
