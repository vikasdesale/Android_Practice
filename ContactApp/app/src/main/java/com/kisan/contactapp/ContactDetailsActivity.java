package com.kisan.contactapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kisan.contactapp.database.ColumnsContacts;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.parcelable.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class ContactDetailsActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.textName)
    TextView textName;
    @Nullable
    @BindView(R.id.textPhone)
    TextView textPhone;
    @Nullable
    @BindView(R.id.sendSms)
    Button sendSms;
    @Nullable
    @BindView(R.id.phone_no)
    EditText phoneNo;
    @Nullable
    @BindView(R.id.msg)
    EditText msg;
    @Nullable
    @BindView(R.id.otp)
    EditText otp;
    private ActionBar ab;
    private long id;
    private Cursor mCursor;
    Contact contact;
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
        ButterKnife.bind(this, mView);

        phoneNo.setEnabled(false);
        otp.setEnabled(false);
        AlertDialog.Builder alertDialogBuilderUserInput;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilderUserInput = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        }

        alertDialogBuilderUserInput.setView(mView);
        phoneNo.setText(""+contact.getPhone());
        otp.setText("Hi your otp is: "+getRandom());
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        
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
}
