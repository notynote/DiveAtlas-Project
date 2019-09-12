package com.notynote.diveatlas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private TextView mTextMessage;
    FragmentManager fm = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFrag(findViewById(R.id.navigation_home));
                    return true;
                case R.id.navigation_maps:
                    changeFrag(findViewById(R.id.navigation_maps));
                    return true;
                case R.id.navigation_divesite:
                    changeFrag(findViewById(R.id.navigation_divesite));
                    return true;
                case R.id.navigation_profile:
                    changeFrag(findViewById(R.id.navigation_profile));
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.isAnyPermissionPermanentlyDenied()){
                            showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();

                    }
                })
                .check();
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
                //update current user
                firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    frag = new userProfile();
                } else {
                    frag = new ProfilePage();
                }
                break;
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentView, frag);

        //backstack check with back button on device
        ft.addToBackStack("com.notynote.week6class.oneFragment");

        //commit the change
        ft.commit();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}
