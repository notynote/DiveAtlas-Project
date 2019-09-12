package com.notynote.diveatlas;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class editProfile extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputPhone;
    private TextInputLayout textInputDiverCertLevel;
    private TextInputLayout textInputDiverCertNumber;
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

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Assign UI
        textInputPhone = view.findViewById(R.id.text_input_editPhone);
        textInputDiverCertLevel = view.findViewById(R.id.text_input_editDiverCertLevel);
        textInputDiverCertNumber = view.findViewById(R.id.text_input_editDiverCertNo);
        progressBar = view.findViewById(R.id.editprofile_loading);

        //drop down agency
        agencySpinner = view.findViewById(R.id.editAgencyDropDown);

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
        Button editButton = view.findViewById(R.id.editProfileButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                //get UserID to implement as the id on the database aswell
                firebaseUser = firebaseAuth.getCurrentUser();
                String userID = firebaseUser.getUid();

                String phone = textInputPhone.getEditText().getText().toString();
                String certlevel = textInputDiverCertLevel.getEditText().getText().toString();
                String certnumber = textInputDiverCertNumber.getEditText().getText().toString();
                String certagent = selectedAgency;

                DatabaseReference uRef = FirebaseDatabase.getInstance().getReference().child("Userdata").child(userID);

                Map<String, Object> updates = new HashMap<String, Object>();

                if (!phone.isEmpty()){
                    updates.put("phone", phone);
                }

                if (!certlevel.isEmpty()){
                    updates.put("certLevel", certlevel);
                }

                if (!certnumber.isEmpty()){
                    updates.put("certNo", certnumber);
                }

                updates.put("certAgent", certagent);

                uRef.updateChildren(updates);


                Toast.makeText(getActivity(),"Profile Update Successfully",Toast.LENGTH_SHORT).show();

                //if success then redirect
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                userProfile frag = new userProfile();
                ft.replace(R.id.fragmentView, frag);
                ft.commit();

            }
        });



        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //change text color
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

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
}