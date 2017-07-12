package com.kisan.contactapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 6/3/2017.
 */


public class ApiClient {

    public static final String BASE_URL = "https://eduapps.000webhostapp.com/";
    private static Retrofit retrofit = null;
    public static final String AUTH_TOKEN="e49b756b87a2e1195e95996ba52e8157";
    public static final String APP_SID = "AC82b6cee7cad92e99eff7550a170321c3";
    public static final String PHONE_FROM= "+17866863332";

    /*public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
    public static Retrofit getClient2() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

}
