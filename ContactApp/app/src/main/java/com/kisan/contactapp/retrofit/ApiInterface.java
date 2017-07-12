package com.kisan.contactapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dell on 6/3/2017.
 */

public interface ApiInterface {

    //@GET("/contacts.json")
   // Observable<ContactsResponse> getContacts();
    @GET("/contacts.json")
    Call<ContactsResponse> getContacts();
}
