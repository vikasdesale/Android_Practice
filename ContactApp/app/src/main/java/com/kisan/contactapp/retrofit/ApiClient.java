package com.kisan.contactapp.retrofit;

import com.kisan.contactapp.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 6/3/2017.
 */


public class ApiClient {

    public static final String BASE_URL = "https://eduapps.000webhostapp.com/";
    public static final String AUTH_TOKEN = BuildConfig.AUTH_TOKEN;
    public static final String APP_SID = BuildConfig.AUTH_SID;
    public static final String PHONE_FROM = "+17866863332";
    public static final String BASE_URL_SMS = "https://api.twilio.com/2010-04-01/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit getClient2() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientSms() {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SMS)
                    .build();
        }
        return retrofit2;
    }

}
