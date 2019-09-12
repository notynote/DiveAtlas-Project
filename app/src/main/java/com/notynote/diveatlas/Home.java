package com.notynote.diveatlas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class Home extends Fragment {

    TextView greeting;
    Calendar cal;
    int timeOfDay;
    String greet;

    private RecyclerView recyclerView;
    private HomeNewsAdapter adapter;
    private ArrayList<HomeNewsInfo> homeNewsInfoArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecyclerView);

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeNewsInfoArrayList = new ArrayList<>();

        adapter = new HomeNewsAdapter(getActivity(), homeNewsInfoArrayList);
        recyclerView.setAdapter(adapter);
        createListData();

        return view;
    }

    private void createListData() {
        String defaultImage = "https://firebasestorage.googleapis.com/v0/b/diveatlas-project.appspot.com/o/noImgiveSite.png?alt=media&token=cafa3b41-fc9c-4a6c-99b3-ec62bc826f57";

        HomeNewsInfo homeNewsAdd = new HomeNewsInfo("Welcome to Dive Atlas"
                , "13 September 2019"
                , "We would like to Thank You for installing Dive Atlas. You can check the news about Scuba Diving in this area. Meanwhile, you can locate dive sites on Map and Dive Sites page. Lastly, the profile page allows you to create an account which will help you display your scuba diving profile."
                , defaultImage);
        homeNewsInfoArrayList.add(homeNewsAdd);

        homeNewsAdd = new HomeNewsInfo("Thailand Open Season for Scuba Diving"
                , "13 September 2019"
                , "It's time to have some fun again! Thailand is opening its scuba diving season from October 2019 to April 2020. It is highly suggest that you do not miss a first dive of the season because the chance to see rare sea creature is higher than ever. Enjoy you trip!!"
                , "https://firebasestorage.googleapis.com/v0/b/diveatlas-project.appspot.com/o/newsJanPrincess.jpg?alt=media&token=a4614d5e-53fe-4c39-a094-3db9abf68fbd");
        homeNewsInfoArrayList.add(homeNewsAdd);

    }

}
