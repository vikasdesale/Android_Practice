package com.kisan.contactapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.kisan.contactapp.database.ColumnsContacts;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.parcelable.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textPhone)
    TextView textPhone;
    @BindView(R.id.sendSms)
    Button sendSms;
    private ActionBar ab;
    private long id;
    private Cursor mCursor;

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
        Contact contact = new Contact(mCursor);
        textName.setText("Name: "+contact.getFirstname() + " " + contact.getLastname());

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


    @OnClick(R.id.sendSms)
    public void onViewClicked() {
    }
}
