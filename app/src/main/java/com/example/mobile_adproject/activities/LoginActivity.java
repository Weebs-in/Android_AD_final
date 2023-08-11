package com.example.mobile_adproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mobile_adproject.LoginAndRegisterFragment.LoginAdapter;
import com.example.mobile_adproject.LoginAndRegisterFragment.SignUpFragment;
import com.example.mobile_adproject.R;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity implements SignUpFragment.OnNavigateToLoginListener {

    TabLayout tabLayout;//provide the choice (login or register)
    ViewPager viewPager;//change the page between login or register
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager_login);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter=new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        //take care of the page change
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void onNavigateToLogin() {
        viewPager.setCurrentItem(0); // Switch to the Login tab (position 0)
    }
}