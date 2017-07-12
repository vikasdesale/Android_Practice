package com.kisan.contactapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kisan.contactapp.R;
import com.kisan.contactapp.fragments.ContactFragment;
import com.kisan.contactapp.fragments.SmsFragment;

/**
 * Created by Dell on 6/2/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int TAB_COUNT=2;
    final static int CONTACTS=0;
    final static int SMS_SENT=1;
    Context mContext;
    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
          Fragment fragment=null;
        switch (position) {
            case CONTACTS:
                fragment= new ContactFragment();
            break;
            case SMS_SENT:
                fragment= new SmsFragment();
                break;
        }
        return  fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return  mContext.getResources().getStringArray(R.array.tabs)[position];
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}