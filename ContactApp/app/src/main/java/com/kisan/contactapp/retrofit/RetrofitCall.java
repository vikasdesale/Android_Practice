package com.kisan.contactapp.retrofit;

import android.content.Context;
import android.util.Log;

import com.kisan.contactapp.database.model.Contact;
import com.kisan.contactapp.util.ContactsUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 6/9/2017.
 */

public class RetrofitCall {
    static ArrayList<Contact> contactList;
    public RetrofitCallback callBack;

    public void onRetrofit(RetrofitCallback call) {

        callBack = call;
    }

    public void fetchContacts(final Context context) {
        final ContactsUtil mContactsUtil = new ContactsUtil();
        ApiInterface apiService =
                ApiClient.getClient2().create(ApiInterface.class);


        Call<ContactsResponse> call = null;
        call = apiService.getContacts();
        call.enqueue(new Callback<ContactsResponse>() {
            @Override
            public void onResponse(Call<ContactsResponse> call, Response<ContactsResponse> response) {
                if (response.isSuccessful()) {
                    contactList = (ArrayList<Contact>) response.body().getContacts();
                    mContactsUtil.insertData(context, contactList);
                    Log.i(TAG, "post submitted to API." + response.toString());
                    if (contactList == null) {
                        callBack.onRetrofitCall(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactsResponse> call, Throwable t) {
                Log.e(TAG, "Error" + t.toString());
                callBack.onRetrofitCall(1);
            }
        });
    }


    public interface RetrofitCallback {
        void onRetrofitCall(int articlesList);
    }

}
