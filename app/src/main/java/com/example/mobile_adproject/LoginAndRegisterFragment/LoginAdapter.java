package com.example.mobile_adproject.LoginAndRegisterFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

// adapter use to change the page between login and register
public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;//the number of the whole page
    public LoginAdapter (FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginFragment loginFragment=new LoginFragment();
                return loginFragment;
            case 1:
                SignUpFragment signUpFragment=new SignUpFragment();
                return signUpFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
