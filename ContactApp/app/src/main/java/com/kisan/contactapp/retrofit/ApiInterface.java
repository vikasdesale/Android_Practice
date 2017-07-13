package com.kisan.contactapp.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Dell on 6/3/2017.
 */

public interface ApiInterface {


    @GET("/contacts.json")
    Call<ContactsResponse> getContacts();

    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages.json")
    Call<ResponseBody> sendMessage(
            @Path("ACCOUNT_SID") String accountSId,
            @Header("Authorization") String signature,
            @FieldMap Map<String, String> metadata
    );
}
