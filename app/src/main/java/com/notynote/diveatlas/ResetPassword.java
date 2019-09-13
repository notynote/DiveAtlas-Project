package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPassword extends Fragment {

    private TextInputLayout textEmail;
    private Button sendMail,back;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    LinearLayout mainLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        mainLayout = view.findViewById(R.id.resetPwdMainLayout);

        //hide keyboard when startup
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.resetPwdProgressBar);

        textEmail = view.findViewById(R.id.text_input_resetPwdEmail);
        sendMail = view.findViewById(R.id.resetPwdSend);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateEmail()){
                    return;
                }

                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

                //show progressbar
                progressBar.setVisibility(View.VISIBLE);

                String email = textEmail.getEditText().getText().toString();

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();

                                    //if success then redirect
                                    FragmentManager fm = getFragmentManager();
                                    fm.popBackStack();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        back = view.findViewById(R.id.resetPwdBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();

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

}
