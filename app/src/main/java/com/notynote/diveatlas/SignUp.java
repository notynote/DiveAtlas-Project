package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class SignUp extends Fragment {

    //UI Variable
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputEmail;
    private Button signUpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        textInputUsername = view.findViewById(R.id.text_input_regUser);
        textInputPassword = view.findViewById(R.id.text_input_regPassword);
        textInputEmail = view.findViewById(R.id.text_input_regEmail);

        signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use one verticle bar to check all
                if (!validateEmail() | !validateUsername() | !validatePassword()){
                        return;
                }

                String input = "Username: " + textInputUsername.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + textInputPassword.getEditText().getText().toString();
                input += "\n";
                input += "Email: " + textInputEmail.getEditText().getText().toString();

                Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();

            }

        });

        return view;
    }

    //check input
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Email can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername(){
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()){
            textInputUsername.setError("Username can't be empty");
            return false;
        } else if (usernameInput.length() > 20){
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Password can't be empty");
            return false;
        } else if (passwordInput.length() > 20){
            textInputPassword.setError("Password too long");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

}
