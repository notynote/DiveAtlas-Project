package com.notynote.diveatlas;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    FragmentManager fm = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFrag(findViewById(R.id.navigation_home));
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_maps:
                    changeFrag(findViewById(R.id.navigation_maps));
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_divesite:
                    changeFrag(findViewById(R.id.navigation_divesite));
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_profile:
                    changeFrag(findViewById(R.id.navigation_profile));
                    //mTextMessage.setText("Profile");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void changeFrag(View v){
        Fragment frag = null;
        switch (v.getId()) {
            case R.id.navigation_home:
                frag = new Home();
                break;
            case R.id.navigation_maps:
                frag = new Maps();
                break;
            case R.id.navigation_divesite:
                frag = new DiveSite();
                break;
            case R.id.navigation_profile:
                frag = new ProfilePage();
                break;
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentView, frag);

        //backstack check with back button on device
        ft.addToBackStack("com.notynote.week6class.oneFragment");

        //commit the change
        ft.commit();

    }

}
