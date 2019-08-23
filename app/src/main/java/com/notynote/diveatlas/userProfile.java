package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userProfile extends Fragment {

    TextView userEmail;
    Button userLogOut;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userEmail = (TextView)view.findViewById(R.id.tvUserEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

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
}