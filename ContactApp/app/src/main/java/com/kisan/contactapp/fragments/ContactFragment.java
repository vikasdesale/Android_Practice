package com.kisan.contactapp.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.kisan.contactapp.activities.ContactDetailsActivity;
import com.kisan.contactapp.adapter.ContactAdapter;
import com.kisan.contactapp.database.ContactsProvider;
import com.kisan.contactapp.database.model.Contact;
import com.kisan.contactapp.retrofit.RetrofitCall;
import com.kisan.contactapp.util.ContactsUtil;
import com.kisan.contactapp.util.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ContactFragment extends Fragment implements
        ContactAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CONTACT_LOADER_ID = 1;
    ContactsUtil mContactsUtil;
    @BindView(R.id.card_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recyclerview_contact_empty)
    TextView emptyView;
    Unbinder unbinder;
    private ContactAdapter contactAdapter;

    public ContactFragment() {
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

        mContactsUtil = new ContactsUtil();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        contactAdapter = new ContactAdapter(null);
        contactAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(contactAdapter);
        updateContact();
        return v;
    }

    private void updateContact() {
        if (!NetworkUtil.isNetworkConnected(getActivity())) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            if (getContext().getContentResolver().query(ContactsProvider.MyContacts.CONTENT_URI,
                    null, null, null, null).getCount() != 0) {

            } else {
                mContactsUtil.CacheDelete(getContext());
                RetrofitCall r = new RetrofitCall();
                r.fetchContacts(getContext());
                r.onRetrofit(new RetrofitCall.RetrofitCallback() {
                    @Override
                    public void onRetrofitCall(int article) {
                        if (article == 1) {
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setText(R.string.empty_contacts_list);
                            emptyView.setVisibility(View.VISIBLE);

                        }
                    }
                });

            }
        }
        getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
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
    public void onItemClick(View v, int position) {
        Contact task = contactAdapter.getItem(position);
        Intent intent = new Intent(getContext(), ContactDetailsActivity.class);
        intent.putExtra("id", task.id);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContactsProvider.MyContacts.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (contactAdapter != null) {
            contactAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        contactAdapter.swapCursor(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
