package com.example.mobile_adproject.Profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobile_adproject.LoginAndRegisterFragment.SignUpFragment;

public class ProfileAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;//the number of the whole page
    public ProfileAdapter (FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DonateBookListFragment donateBookListFragment=new DonateBookListFragment();
                return donateBookListFragment;
            case 1:
                TransactionHistoryFragment transactionHistoryFragment=new TransactionHistoryFragment();
                return transactionHistoryFragment;
            case 2:
                ApprovedBookList approvedBookList=new ApprovedBookList();
                return approvedBookList;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
