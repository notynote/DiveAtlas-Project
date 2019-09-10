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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


public class SignUp extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=!])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$");

    //UI Variable
    //private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputFirstName;
    private TextInputLayout textInputLastName;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputDiverCertLevel;
    private TextInputLayout textInputDiverCertNumber;
    private TextInputLayout textInputDiverCertAgent;
    private Button signUpButton;
    private ProgressBar progressBar;

    //Drop down agent
    private Spinner agencySpinner;
    private String selectedAgency;
    private static final String[] agencies = {"BSAC", "CMAS", "IANTD", "NAUI", "PADI", "SDI", "SSI", "TDI", "Other"};

    //Firebase variable
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    //database variable
    FirebaseDatabase database;
    DatabaseReference dRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //Assign UI
        //textInputUsername = view.findViewById(R.id.text_input_regUser);
        textInputPassword = view.findViewById(R.id.text_input_regPassword);
        textInputEmail = view.findViewById(R.id.text_input_regEmail);
        textInputFirstName = view.findViewById(R.id.text_input_regFirstName);
        textInputLastName = view.findViewById(R.id.text_input_regLastName);
        textInputPhone = view.findViewById(R.id.text_input_regPhone);
        textInputDiverCertLevel = view.findViewById(R.id.text_input_regDiverCertLevel);
        textInputDiverCertNumber = view.findViewById(R.id.text_input_regDiverCertNo);
        //textInputDiverCertAgent = view.findViewById(R.id.text_input_regDiverCertAgent);
        progressBar = view.findViewById(R.id.signup_loading);

        //drop down agency
        agencySpinner = view.findViewById(R.id.agencyDropDown);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,agencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agencySpinner.setAdapter(adapter);
        agencySpinner.setOnItemSelectedListener(this);


        //Assign Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Assign Database
        database = FirebaseDatabase.getInstance();
        dRef = database.getReference("Userdata");

        //Assign Button
        signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use one verticle bar to check all
                //if (!validateEmail() | !validateUsername() | !validatePassword()){
                //        return;
                //}

                if (!validateEmail() | !validatePassword()){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //AddUserData();

                CreateNewAccount();
            }

        });

        return view;
    }

    private void CreateNewAccount() {

        String email = textInputEmail.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),"Account Created Successfully",Toast.LENGTH_SHORT).show();

                    //get UserID to implement as the id on the database aswell
                    firebaseUser = firebaseAuth.getCurrentUser();
                    String userID = firebaseUser.getUid();

                    //add information into database
                    String email = textInputEmail.getEditText().getText().toString();
                    String firstname = textInputFirstName.getEditText().getText().toString();
                    String lastname = textInputLastName.getEditText().getText().toString();
                    String phone = textInputPhone.getEditText().getText().toString();
                    String certlevel = textInputDiverCertLevel.getEditText().getText().toString();
                    String certnumber = textInputDiverCertNumber.getEditText().getText().toString();
                    //String certagent = textInputDiverCertAgent.getEditText().getText().toString();
                    String certagent = selectedAgency;
                    String image = "https://ui-avatars.com/api/?size=300&rounded=true&name=" + firstname + "+" + lastname;

                    Userdata userdata = new Userdata(email,firstname,lastname,phone,certlevel,certnumber,certagent, image);

                    //add information with child same as userID
                    dRef.child(userID).setValue(userdata);

                    //if success then redirect
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    userProfile frag = new userProfile();
                    ft.replace(R.id.fragmentView, frag);
                    ft.commit();

                } else {
                    String errorMessage = task.getException().toString();
                    Toast.makeText(getActivity(),"Error: " + errorMessage,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //check input
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("Please enter valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

//    private boolean validateUsername(){
//        String usernameInput = textInputUsername.getEditText().getText().toString().trim();
//
//        if (usernameInput.isEmpty()){
//            textInputUsername.setError("Username can't be empty");
//            return false;
//        } else if (usernameInput.length() > 20){
//            textInputUsername.setError("Username too long");
//            return false;
//        } else {
//            textInputUsername.setError(null);
//            return true;
//        }
//    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Password can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            textInputPassword.setError("Password too weak");
            return false;
        } else if (passwordInput.length() > 20){
            textInputPassword.setError("Password too long");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                this.selectedAgency = "BSAC";
                break;
            case 1:
                this.selectedAgency = "CMAS";
                break;
            case 2:
                this.selectedAgency = "IANTD";
                break;
            case 3:
                this.selectedAgency = "NAUI";
                break;
            case 4:
                this.selectedAgency = "PADI";
                break;
            case 5:
                this.selectedAgency = "SDI";
                break;
            case 6:
                this.selectedAgency = "SSI";
                break;
            case 7:
                this.selectedAgency = "TDI";
                break;
            case 8:
                this.selectedAgency = "Other";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    public void AddUserData(){
//
//        String email = textInputEmail.getEditText().getText().toString();
//        String firstname = textInputFirstName.getEditText().getText().toString();
//        String lastname = textInputLastName.getEditText().getText().toString();
//        String phone = textInputPhone.getEditText().getText().toString();
//        String certlevel = textInputDiverCertLevel.getEditText().getText().toString();
//        String certnumber = textInputDiverCertNumber.getEditText().getText().toString();
//        String certagent = textInputDiverCertAgent.getEditText().getText().toString();
//
//        String id = dRef.push().getKey();
//
//        Userdata userdata = new Userdata(email,firstname,lastname,phone,certlevel,certnumber,certagent);
//
//        dRef.child(id).setValue(userdata);
//
//    }

}

