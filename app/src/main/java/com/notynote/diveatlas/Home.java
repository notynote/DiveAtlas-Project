package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


public class Home extends Fragment {

    TextView greeting;
    Calendar cal;
    int timeOfDay;
    String greet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        greeting = view.findViewById(R.id.homeGreeting);

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

        greeting.setText(greet);

        return view;
    }

}
