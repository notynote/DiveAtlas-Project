package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Map;

public class userProfile extends Fragment {

    //UI variable

    LinearLayout userDataLayout;
    TextView userEmail;
    TextView userName;
    TextView userPhone;
    TextView userCertLevel;
    TextView userCertNo;
    TextView userCertAgent;
    Button userLogOut;
    Button editProfile;
    ProgressBar loadingBar;
    TextView userGreeting;
    RelativeLayout userDetailLayout;
    ImageButton profileImage;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    String userID;

    Calendar cal;
    int timeOfDay;
    String greet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        loadingBar = (ProgressBar) view.findViewById(R.id.LoadingBar);

        loadingBar.setVisibility(View.VISIBLE);

        userDataLayout = (LinearLayout)view.findViewById(R.id.userDataLayout);
//        userEmail = (TextView)view.findViewById(R.id.tvUserEmail);
        userName = (TextView)view.findViewById(R.id.tvUserName);
//        userPhone = (TextView)view.findViewById(R.id.tvUserPhone);
        userCertLevel = (TextView)view.findViewById(R.id.tvUserDiverLevel);
        userCertNo = (TextView)view.findViewById(R.id.tvUserDiveNumber);
        userCertAgent = (TextView)view.findViewById(R.id.tvUserDiveAgent);
        userGreeting = view.findViewById(R.id.userGreeting);
        userDetailLayout = view.findViewById(R.id.userDetailLayout);

        //image profile
        profileImage = view.findViewById(R.id.ProfileImage);

        //greeting by time
        cal = Calendar.getInstance();
        timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greet = "Good Morning";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greet = "Good Afternoon";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            greet = "Good Evening";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            greet = "Good Night";
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Userdata");

        userID = firebaseUser.getUid();
        DatabaseReference uidRef = dRef.child("Userdata").child(userID);

        //get information from database
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map)dataSnapshot.child(userID).getValue();
                String email = String.valueOf(map.get("email"));
                String firstname = String.valueOf(map.get("firstName"));
                String lastname = String.valueOf(map.get("lastName"));
                String phone = String.valueOf(map.get("phone"));
                String certlevel = String.valueOf(map.get("certLevel"));
                String certnumber = String.valueOf(map.get("certNo"));
                String certagent = String.valueOf(map.get("certAgent"));
                String profileImg = String.valueOf(map.get("imageUrl"));

                userGreeting.setText(greet);
                userName.setText(firstname + " " + lastname);
                if (certlevel.equals("")){
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(userCertLevel,TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    userCertLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    userCertLevel.setText("N/A");
                } else {
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(userCertLevel,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    userCertLevel.setText(certlevel);
                }
                if (certnumber.equals("")){
                    userCertNo.setText("N/A");
                } else {
                    userCertNo.setText(certnumber);
                }
                userCertAgent.setText(certagent);
//                userPhone.setText("Phone: " + phone);
                if (profileImg.equals("")){
                    profileImg = "https://ui-avatars.com/api/?size=300&rounded=true&name=" + firstname + "+" + lastname;
                    Picasso.get().load(profileImg).into(profileImage);
                } else {
                    Picasso.get().load(profileImg).into(profileImage);
                }

                loadingBar.setVisibility(View.GONE);
                userDetailLayout.setVisibility(View.VISIBLE);
                userGreeting.setVisibility(View.VISIBLE);
                userName.setVisibility(View.VISIBLE);
//                userEmail.setVisibility(View.VISIBLE);
                userCertAgent.setVisibility(View.VISIBLE);
                userCertLevel.setVisibility(View.VISIBLE);
                userCertNo.setVisibility(View.VISIBLE);
//                userPhone.setVisibility(View.VISIBLE);
                userLogOut.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Infomation
//        userEmail.setText(firebaseUser.getEmail());

        editProfile = view.findViewById(R.id.btnEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect back to log in page
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                editProfile frag = new editProfile();
                ft.replace(R.id.fragmentView, frag);
                ft.commit();
            }
        });


        userLogOut = (Button)view.findViewById(R.id.btnLogOut);
        userLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign out
                FirebaseAuth.getInstance().signOut();

                //redirect back to log in page
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProfilePage frag = new ProfilePage();
                ft.replace(R.id.fragmentView, frag);
                ft.commit();
            }
        });

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Userdata uData = new Userdata();
            uData.setEmail(ds.child(userID).getValue(Userdata.class).getEmail());
            uData.setFirstName(ds.child(userID).getValue(Userdata.class).getFirstName());
            uData.setLastName(ds.child(userID).getValue(Userdata.class).getLastName());
            uData.setPhone(ds.child(userID).getValue(Userdata.class).getPhone());
            uData.setCertLevel(ds.child(userID).getValue(Userdata.class).getCertLevel());
            uData.setCertNo(ds.child(userID).getValue(Userdata.class).getCertNo());
            uData.setCertAgent(ds.child(userID).getValue(Userdata.class).getCertAgent());
        }
    }
}