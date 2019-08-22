package com.notynote.diveatlas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class DiveSite extends Fragment {
    ListView lv;
    ListViewEndlessLoop lvel;

    String[] lv_arr = { "Row 0", "Row 1", "Row 2", "Row 3", "Row 4"
            , "Row 5", "Row 6", "Row 7", "Row 8", "Row 9", "Row 10"
            , "Row 11", "Row 12", "Row 13", "Row 14", "Row 15", "Row 16"
            , "Row 17", "Row 18", "Row 19" , "Row 20", "Row 21", "Row 22"
            , "Row 23", "Row 24", "Row 25", "Row 26", "Row 27", "Row 28"
            , "Row 29", "Row 30", "Row 31", "Row 32", "Row 33", "Row 34"
            , "Row 35", "Row 36", "Row 37", "Row 38", "Row 39", "Row 40"
            , "Row 41", "Row 42", "Row 43", "Row 44", "Row 45", "Row 46"
            , "Row 47", "Row 48", "Row 49", "Row 50", "Row 51", "Row 52"
            , "Row 53", "Row 54", "Row 55", "Row 56", "Row 57", "Row 58"
            , "Row 59", "Row 60", "Row 61", "Row 62", "Row 63", "Row 64"
            , "Row 65", "Row 66", "Row 67", "Row 68", "Row 69", "Row 70"
            , "Row 71", "Row 72", "Row 73", "Row 74", "Row 75", "Row 76"
            , "Row 77", "Row 78", "Row 79", "Row 80", "Row 81", "Row 82"
            , "Row 83", "Row 84", "Row 85", "Row 86", "Row 87", "Row 88"
            , "Row 89", "Row 90", "Row 91", "Row 92", "Row 93", "Row 94"
            , "Row 95", "Row 96", "Row 97", "Row 98", "Row 99", "Row 100"
            , "Row 101", "Row 102", "Row 103", "Row 104", "Row 105"
            , "Row 106", "Row 107", "Row 108", "Row 109", "Row 110"
            , "Row 111", "Row 112", "Row 113", "Row 114", "Row 115"
            , "Row 116", "Row 117", "Row 118", "Row 119", "Row 120"
            , "Row 121", "Row 122", "Row 123", "Row 124", "Row 125"
            , "Row 126", "Row 127", "Row 128", "Row 129", "Row 130"
            , "Row 131" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dive_site, container, false);

        lv = (ListView)view.findViewById(R.id.listView1);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getContext(), "Select row "
                                + String.valueOf(lvel.getSelectedRow(arg2))
                        , Toast.LENGTH_SHORT).show();
            }
        });

        lvel = new ListViewEndlessLoop(getContext(), lv, lv_arr, 20);
        lvel.setSelection(121);

        // Inflate the layout for this fragment
        return view;
    }

}
