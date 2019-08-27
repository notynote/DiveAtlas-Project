package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userDataLayout = (LinearLayout)view.findViewById(R.id.userDataLayout);
        userEmail = (TextView)view.findViewById(R.id.tvUserEmail);
        userName = (TextView)view.findViewById(R.id.tvUserName);
        userPhone = (TextView)view.findViewById(R.id.tvUserPhone);
        userCertLevel = (TextView)view.findViewById(R.id.tvUserDiverLevel);
        userCertNo = (TextView)view.findViewById(R.id.tvUserDiveNumber);
        userCertAgent = (TextView)view.findViewById(R.id.tvUserDiveAgent);

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

                userName.setText("Welcome " + firstname + " " + lastname);
                userCertLevel.setText(certlevel + " Diver");
                userCertNo.setText("Number: " + certnumber);
                userCertAgent.setText("Issue by: " + certagent);
                userPhone.setText("Phone: " + phone);

                userDataLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Infomation
        userEmail.setText(firebaseUser.getEmail());


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