package com.kisan.contactapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisan.contactapp.ContactAdapter;
import com.kisan.contactapp.ContactDetailsActivity;
import com.kisan.contactapp.R;
import com.kisan.contactapp.parcelable.Contact;
import com.kisan.contactapp.retrofit.NetworkUtil;


public class ContactFragment extends Fragment implements
        ContactAdapter.OnItemClickListener{
    RecyclerView mRecyclerView;
    ContactsUtil mContactsUtil;
String tab;
    private ContactAdapter contactAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_contact, container, false);
         mRecyclerView = (RecyclerView)v.findViewById(R.id.card_recycler_view);
        mContactsUtil=new ContactsUtil();
        if (!NetworkUtil.isNetworkConnected(getActivity())) {
        } else {
            mContactsUtil.CacheDelete(getContext());
            RetrofitCall r = new RetrofitCall();
            r.fetchContacts(getContext());
            RetrofitCall.onRetrofit(new RetrofitCall.RetrofitCallback() {
                @Override
                public void onRetrofitCall(int article) {
                    // Send update broadcast to update the widget
                    //  getContext().sendBroadcast(new Intent(getString(R.string.widget_action)));
                    //progressBar.setVisibility(View.GONE);
                    //progressBar2.setVisibility(View.GONE);
                    allNewsWindow();
                    if (article == 1) {
                        //  progressBar.setVisibility(View.GONE);
                        //progressBar2.setVisibility(View.GONE);
//                        Snackbar snackbar = Snackbar
                        //                              .make(contLayout, R.string.network_data_not_found, Snackbar.LENGTH_LONG);

                        //                    snackbar.show();
                    }
                }
            });
        }
        return v;
    }
    //Load All Movies from temporary database
    private void allNewsWindow() {
        try {
           Cursor cursor = mContactsUtil.allContactCursor(getActivity());
            setUpAdapter(cursor);
        } catch (Exception e) {

        }

    }
    private void setUpAdapter(Cursor c) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        contactAdapter = new ContactAdapter(c);
        //mNewsLoader = mNewsLoader.newInstance(favflag, this, gridAdapter);
        //gridAdapter = new NewsRecyclerViewAdapter(getActivity(), c);
        //mNewsLoader.initLoader();
        contactAdapter.setOnItemClickListener(this);
        if (mRecyclerView != null)
            mRecyclerView.setAdapter(contactAdapter);
        // Send update broadcast to update the widget
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
        intent.putExtra("id",task.id);
        startActivity(intent);
    }
}
