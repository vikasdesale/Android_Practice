package com.kisan.contactapp.fragments;

import android.content.Context;
import android.util.Log;

import com.kisan.contactapp.parcelable.Contact;
import com.kisan.contactapp.retrofit.ApiClient;
import com.kisan.contactapp.retrofit.ApiInterface;
import com.kisan.contactapp.retrofit.ContactsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 6/9/2017.
 */

public class RetrofitCall {
    public static RetrofitCallback callBack;
    static ArrayList<Contact> contactList;

    public static void onRetrofit(RetrofitCallback call) {

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
                }

                callBack.onRetrofitCall(0);

            }

            @Override
            public void onFailure(Call<ContactsResponse> call, Throwable t) {
                Log.e(TAG, "Error" + t.toString());
                int i = 1;
                callBack.onRetrofitCall(i);
            }
        });
    }

  /*  public void fetchContacts(final Context context) {
        final ContactsUtil mContactsUtil = new ContactsUtil();
        ApiInterface apiService =
                ApiClient.getClient2().create(ApiInterface.class);

       Observable<ContactsResponse> news= apiService.getContacts();

        news.subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Observer<ContactsResponse>(){

            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(ContactsResponse contactResponse) {
                contactList = (ArrayList<Contact>) contactResponse.getContacts();
                mContactsUtil.insertData(context, contactList);
                Log.i(TAG, "post submitted to API." + contactResponse.toString());
                callBack.onRetrofitCall(0);


            }

            @Override
            public void onError(Throwable throwable) {
                int i = 1;
                callBack.onRetrofitCall(i);
            }

            @Override
            public void onComplete() {

            }
        });


    }
*/
    public interface RetrofitCallback {
        public void onRetrofitCall(int articlesList);
    }

}
