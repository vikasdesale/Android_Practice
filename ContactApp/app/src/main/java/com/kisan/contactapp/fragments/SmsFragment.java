package com.kisan.contactapp.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisan.contactapp.R;
import com.kisan.contactapp.adapter.SmsAdapter;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.database.model.ColumnsSms;
import com.kisan.contactapp.util.ContactsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SmsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SMS_LOADER_ID = 1;
    ContactsUtil mContactsUtil;
    SmsAdapter smsAdapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recyclerview_contact_empty)
    TextView emptyView;
    Unbinder unbinder;

    public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (getContext().getContentResolver().query(ContactsProvider.MySmsSent.CONTENT_URI_SMS_SENT,
                null, null, null, null).getCount()==0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setText(R.string.sms_sent);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
            mContactsUtil = new ContactsUtil();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            smsAdapter = new SmsAdapter(null);
            mRecyclerView.setAdapter(smsAdapter);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SMS_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Sort order:  Ascending, by date.
        String sortOrder = ColumnsSms.DATE_SENT + " DESC";
        return new CursorLoader(getActivity(), ContactsProvider.MySmsSent.CONTENT_URI_SMS_SENT,
                null,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (smsAdapter != null) {
            smsAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        smsAdapter.swapCursor(null);
    }
}
