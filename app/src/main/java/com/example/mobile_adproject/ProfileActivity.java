package com.example.mobile_adproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mobile_adproject.Profile.ProfileAdapter;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {

    ImageView home;
    ImageView message;
    ImageView donate;
    ImageView profile;

    TabLayout tabLayout;//provide the choice
    ViewPager viewPager;//change the page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager_profile);


/*// 创建一个新的 Tab，并设置自定义视图
        TabLayout.Tab tab1 = tabLayout.newTab().setCustomView(R.layout.tab_text_image_layout);
        TabLayout.Tab tab2 = tabLayout.newTab().setCustomView(R.layout.tab_text_image_layout);
        TabLayout.Tab tab3 = tabLayout.newTab().setCustomView(R.layout.tab_text_image_layout);

// 将 Tab 添加到 TabLayout 中
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);

// 设置自定义视图的内容，图标和文字
        ((ImageView) tab1.getCustomView().findViewById(R.id.tab_icon)).setImageResource(R.drawable.ic_baseline_library_books_24);
        ((TextView) tab1.getCustomView().findViewById(R.id.tab_text)).setText("Donate Book");

        ((ImageView) tab2.getCustomView().findViewById(R.id.tab_icon)).setImageResource(R.drawable.ic_baseline_history_24);
        ((TextView) tab2.getCustomView().findViewById(R.id.tab_text)).setText("History");

        ((ImageView) tab3.getCustomView().findViewById(R.id.tab_icon)).setImageResource(R.drawable.ic_baseline_check_box_24);
        ((TextView) tab3.getCustomView().findViewById(R.id.tab_text)).setText("Approved Book");*/

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_library_books_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_history_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_check_box_24));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ProfileAdapter adapter=new ProfileAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
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

        home=findViewById(R.id.home_action_bottom);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        message=findViewById(R.id.message_action_bottom);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });

        donate=findViewById(R.id.donate_action_bottom);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        profile=findViewById(R.id.profile_action_bottom);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}