package com.kisan.contactapp.fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.kisan.contactapp.R;
import com.kisan.contactapp.database.model.ColumnsSms;
import com.kisan.contactapp.retrofit.ApiClient;
import com.kisan.contactapp.retrofit.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kisan.contactapp.retrofit.ApiClient.APP_SID;
import static com.kisan.contactapp.retrofit.ApiClient.AUTH_TOKEN;
import static com.kisan.contactapp.retrofit.ApiClient.PHONE_FROM;
import static com.kisan.contactapp.util.ContactsUtil.insertSmsSent;

/**
 * Created by Dell on 7/13/2017.
 */

public class ContactDialogFragment extends DialogFragment {


    @BindView(R.id.phone_no)
    EditText phoneNo;
    @BindView(R.id.msg)
    EditText msg;
    @BindView(R.id.otp)
    EditText otp;
    Unbinder unbinder;
    int otp_number;
    String firstName;
    String lastName;
    String phone;
    Bundle mArgs;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View mView = layoutInflaterAndroid.inflate(R.layout.sms_dialog_box, null);
        unbinder = ButterKnife.bind(this, mView);

        mArgs = getArguments();
        phone = mArgs.getString("phone");
        firstName = mArgs.getString("firstName");
        lastName = mArgs.getString("lastName");

        //  msg = mView.findViewById(R.id.msg);
        //otp = mView.findViewById(R.id.otp);
        // phoneNo = mView.findViewById(R.id.phone_no);
        phoneNo.setEnabled(false);
        otp.setEnabled(false);
        AlertDialog.Builder alertDialogBuilderUserInput;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        }

        alertDialogBuilderUserInput.setView(mView);
        phoneNo.setText("" + phone);
        otp_number = getRandom();
        otp.setText(getString(R.string.your_otp_is) + otp_number);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(R.string.send_my_sms, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sendMessage(getActivity(), msg.getText() + " " + otp.getText().toString(), phoneNo.getText().toString(),
                                PHONE_FROM, otp_number, firstName, lastName);

                    }
                })

                .setNegativeButton(R.string.cancel_sms,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        return alertDialogBuilderUserInput.create();

    }




    public int getRandom() {
        return 100000 + (int) (Math.random() * ((999999 - 100000) + 999999));
    }

    private void sendMessage(final Context context, final String body, final String to, String from, final int otp_number, final String firstName, final String lastName) {


        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (APP_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", to);
        data.put("Body", body);


        ApiInterface api = ApiClient.getClientSms().create(ApiInterface.class);

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


}
