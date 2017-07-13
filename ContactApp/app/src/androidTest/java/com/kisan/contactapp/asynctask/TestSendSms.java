package com.kisan.contactapp.asynctask;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Base64;

import com.kisan.contactapp.activities.MainActivity;
import com.kisan.contactapp.retrofit.ApiClient;
import com.kisan.contactapp.retrofit.ApiInterface;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.kisan.contactapp.retrofit.ApiClient.APP_SID;
import static com.kisan.contactapp.retrofit.ApiClient.AUTH_TOKEN;
import static com.kisan.contactapp.retrofit.ApiClient.PHONE_FROM;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Dell on 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestSendSms {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testAsnyc() throws IOException {


        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (APP_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From",PHONE_FROM);
        data.put("To", "+918600507926");
        data.put("Body","Test Sms");


        ApiInterface api = ApiClient.getClientSms().create(ApiInterface.class);

        Call<ResponseBody> call= api.sendMessage(APP_SID, base64EncodedCredentials, data);

        Response<ResponseBody> response= call.execute();
        ResponseBody authResponse = response.body();

        assertTrue(response.isSuccessful());

    }
}
