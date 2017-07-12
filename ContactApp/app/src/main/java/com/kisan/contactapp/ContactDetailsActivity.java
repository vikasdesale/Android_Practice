package com.kisan.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kisan.contactapp.database.ColumnsContacts;
import com.kisan.contactapp.database.ColumnsSms;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.parcelable.Contact;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.kisan.contactapp.fragments.ContactsUtil.insertSmsSent;
import static com.kisan.contactapp.retrofit.ApiClient.APP_SID;
import static com.kisan.contactapp.retrofit.ApiClient.AUTH_TOKEN;
import static com.kisan.contactapp.retrofit.ApiClient.PHONE_FROM;


public class ContactDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textPhone)
    TextView textPhone;
    @BindView(R.id.sendSms)
    Button sendSms;
    EditText phoneNo;
    EditText msg;
    EditText otp;
    @BindView(R.id.progressbar)
    CircularProgressBar progressbar;
    private ActionBar ab;
    private long id;
    private Cursor mCursor;
    Contact contact;
    int otp_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setupFind();
//Task must be passed to this activity as a valid provider Uri
        id = getIntent().getLongExtra("id", 0);
        mCursor = getData(id);
        mCursor.moveToFirst();
        contact = new Contact(mCursor);
        textName.setText("Name: " + contact.getFirstname() + " " + contact.getLastname());
        textPhone.setText("Contact No: " + contact.getPhone());

    }

    private Cursor getData(long id) {
        String selection = String.format("%s = ?", ColumnsContacts._ID);
        String selectionArgs[] = new String[]{String.valueOf(id)};
        return getContentResolver().query(ContactsProvider.MyContacts.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
    }

    private void setupFind() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Optional
    @OnClick(R.id.sendSms)
    public void onViewClicked() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.sms_dialog_box, null);
        msg=mView.findViewById(R.id.msg);
        otp=mView.findViewById(R.id.otp);
        phoneNo=mView.findViewById(R.id.phone_no);
        phoneNo.setEnabled(false);
        otp.setEnabled(false);
        AlertDialog.Builder alertDialogBuilderUserInput;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilderUserInput = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        }

        alertDialogBuilderUserInput.setView(mView);
        phoneNo.setText("" + contact.getPhone());
        otp_number = getRandom();
        otp.setText("Hi your otp is: " + otp_number);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        progressbar.setVisibility(View.VISIBLE);
                        sendMessage(getApplicationContext(), msg.getText() + " " + otp.getText().toString(), phoneNo.getText().toString(),
                                PHONE_FROM, otp_number, contact.getFirstname(), contact.getLastname());

                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    public int getRandom() {
        return 10000 + (int) (Math.random() * ((99999 - 10000) + 99999));
    }

    private void sendMessage(final Context context, final String body, final String to, String from, final int otp_number, final String firstName, final String lastName) {


        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (APP_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", to);
        data.put("Body", body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build();
        TwilioApi api = retrofit.create(TwilioApi.class);

        api.sendMessage(APP_SID, base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse->success");
                    ContentValues values = new ContentValues(6);
                    values.put(ColumnsSms.FIRSTNAME, firstName);
                    values.put(ColumnsSms.LASTNAME, lastName);
                    values.put(ColumnsSms.MOBILE_NO, to);
                    values.put(ColumnsSms.MSG_CONTENT, body);
                    values.put(ColumnsSms.DATE_SENT, System.currentTimeMillis());
                    values.put(ColumnsSms.OTP_GENERATE, otp_number);

                    insertSmsSent(context, values);
                    progressbar.setVisibility(View.GONE);

                } else {
                    Log.d("TAG", "onResponse->failure");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure");
            }
        });
    }

    interface TwilioApi {
        @FormUrlEncoded
        @POST("Accounts/{ACCOUNT_SID}/SMS/Messages.json")
        Call<ResponseBody> sendMessage(
                @Path("ACCOUNT_SID") String accountSId,
                @Header("Authorization") String signature,
                @FieldMap Map<String, String> metadata
        );
    }
}
