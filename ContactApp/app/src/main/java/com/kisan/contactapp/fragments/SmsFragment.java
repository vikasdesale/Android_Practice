package com.kisan.contactapp.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisan.contactapp.R;
import com.kisan.contactapp.SmsAdapter;


public class SmsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ContactsUtil mContactsUtil;
    SmsAdapter smsAdapter;
    public SmsFragment() {
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
        allNewsWindow();

        return v;
    }
    //Load All Movies from temporary database
    private void allNewsWindow() {
        try {
            Cursor cursor = mContactsUtil.allSmsCursor(getActivity());
            setUpAdapter(cursor);
        } catch (Exception e) {

        }

    }
    private void setUpAdapter(Cursor c) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        smsAdapter = new SmsAdapter(c);
        //mNewsLoader = mNewsLoader.newInstance(favflag, this, gridAdapter);
        //gridAdapter = new NewsRecyclerViewAdapter(getActivity(), c);
        //mNewsLoader.initLoader();
        if (mRecyclerView != null)
            mRecyclerView.setAdapter(smsAdapter);
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


}
