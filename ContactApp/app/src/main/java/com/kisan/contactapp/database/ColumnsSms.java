package com.kisan.contactapp.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dell on 7/12/2017.
 */

public interface ColumnsSms {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String FIRSTNAME = "firstName";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String LASTNAME = "lastName";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String MOBILE_NO = "mobileNo";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String DATE_SENT = "sent_date";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    String OTP_GENERATE = "otp";
}
