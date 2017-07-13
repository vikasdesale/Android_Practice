package com.kisan.contactapp.asynctask;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kisan.contactapp.activities.MainActivity;
import com.kisan.contactapp.retrofit.ApiClient;
import com.kisan.contactapp.retrofit.ApiInterface;
import com.kisan.contactapp.retrofit.ContactsResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Dell on 7/13/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TestAsync {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testAsnyc() throws IOException {
        ApiInterface apiService =
                ApiClient.getClient2().create(ApiInterface.class);


        Call<ContactsResponse> call = null;
        call = apiService.getContacts();
        Response<ContactsResponse> response= call.execute();
        ContactsResponse authResponse = response.body();

        assertTrue(response.isSuccessful()&&!authResponse.getContacts().isEmpty());

    }
}
