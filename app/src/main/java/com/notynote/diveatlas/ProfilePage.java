package com.notynote.diveatlas;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;


public class ProfilePage extends Fragment {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=!])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$");

    ProgressBar progressBar;

    private TextInputLayout textEmail;
    private TextInputLayout textPassword;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        progressBar = view.findViewById(R.id.profilepage_loading);

        textEmail = view.findViewById(R.id.text_input_logEmail);
        textPassword = view.findViewById(R.id.text_input_logPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        Button signUp = (Button)view.findViewById(R.id.signUp);
        //change frag on click
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SignUp frag = new SignUp();
                ft.replace(R.id.fragmentView, frag);
                //backstack check with back button on device
                ft.addToBackStack("com.notynote.week6class.oneFragment");
                ft.commit();
            }
        });

        Button logIn = (Button)view.findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //check for input
                //use one verticle bar to check all
                if (!validateEmail() | !validatePassword()){
                    return;
                }

                //show progressbar
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(textEmail.getEditText().getText().toString(), textPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //disable progress bar
                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            //if success then redirect
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            userProfile frag = new userProfile();
                            ft.replace(R.id.fragmentView, frag);
                            //backstack check with back button on device
                            ft.addToBackStack("com.notynote.week6class.oneFragment");
                            ft.commit();
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    //check input
    private boolean validateEmail() {
        String emailInput = textEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textEmail.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textEmail.setError("Please enter valid email address");
            return false;
        } else {
            textEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textPassword.setError("Password can't be empty");
            return false;
        //} else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
        //    textPassword.setError("Password too weak");
        //    return false;
        } else if (passwordInput.length() > 20){
            textPassword.setError("Password too long");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }

}
