package com.notynote.diveatlas;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewEndlessLoop {
    private ArrayList<String> arr_all, arr_show;
    private int first_row, last_row, total_row = 20;
    private Context mContext;
    private ListView listView;

    public ListViewEndlessLoop (Context context, ListView lv, ArrayList<String> array, int viewRow) {
        mContext = context;
        listView = lv;
        total_row = viewRow;
        arr_all = new ArrayList<>();
        arr_show = new ArrayList<>();
        arr_all = array;
        first_row = arr_all.size() -1;

        while(first_row < 0){
            first_row += arr_all.size();
        }

        for(int i = 0 ; i < total_row ; i++){
            arr_show.add(arr_all.get(i));
            first_row++;
            if(first_row >= arr_all.size()){
                first_row = 0;
            }
        }

        first_row -= total_row;

        while (first_row < 0) {
            first_row += arr_all.size();
        }

        last_row = first_row + total_row -1;

        while (last_row >= arr_all.size()){
            last_row -= arr_all.size();
        }

        initListView();
    }

    public ListViewEndlessLoop (Context context, ListView lv, String[] array, int viewRow) {
        mContext = context;
        listView = lv;
        total_row = viewRow;
        arr_all = new ArrayList<>();
        arr_show= new ArrayList<>();

        for(int i = 0 ; i < array.length ; i++){
            arr_all.add(array[i]);
        }

        first_row = array.length -1;

        while (first_row < 0){
            first_row += array.length;
        }

        for(int i = 0 ; i < total_row ; i++){
            arr_show.add(array[first_row]);
            first_row++;
            if(first_row >= array.length){
                first_row = 0;
            }
        }

        first_row -= total_row;

        while (first_row < 0){
            first_row += array.length;
        }

        last_row = first_row + total_row -1;

        while (last_row >= array.length){
            last_row -= array.length;
        }

        initListView();
    }

    public void initListView() {

        listView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,arr_show));

        listView.setSelection((total_row/2));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    arr_show = new ArrayList<>();
                    last_row -= total_row / 2;

                    while(last_row < 0){
                        last_row += arr_all.size();
                    }

                    for(int i = 0 ; i < total_row ; i++){
                        arr_show.add(arr_all.get(last_row));
                        last_row++;
                        if(last_row >= arr_all.size()) {
                            last_row = 0;
                        }
                    }

                    first_row = last_row - total_row;

                    while(first_row < 0){
                        first_row += arr_all.size();
                    }

                    last_row--;
                    while (last_row < 0){
                        last_row += arr_all.size();
                    }

                    listView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, arr_show));

                    if(total_row % 2 == 0){
                        listView.setSelection((firstVisibleItem + 2) - (total_row / 2));
                    } else {
                        listView.setSelection((firstVisibleItem + 1) - (total_row / 2));
                    }
                }

                if(firstVisibleItem == 0){
                    arr_show = new ArrayList<>();
                    first_row -= total_row / 2;

                    while (first_row < 0){
                        first_row += arr_all.size();
                    }

                    for(int i = 0 ; i < total_row ; i++) {
                        arr_show.add(arr_all.get(first_row));
                        first_row++;
                        if(first_row >= arr_all.size())
                            first_row = 0;
                    }

                    first_row -= total_row;

                    while(first_row < 0) {
                        first_row += arr_all.size();
                    }

                    last_row = first_row + total_row - 1;

                    while(last_row >= arr_all.size()) {
                        last_row -= arr_all.size();
                    }

                    listView.setAdapter(new ArrayAdapter<String>
                            (mContext,
                                    android.R.layout.simple_list_item_1
                                    , arr_show));

                    listView.setSelection((total_row / 2) + 1);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

    public int getSelectedRow(int position) {
        if(first_row + position >= arr_all.size())
            return (first_row + position) - arr_all.size();
        else {
            return first_row + position;
        }
    }

    public void setSelection(int position) {
        arr_show = new ArrayList<String>();
        last_row = position - (total_row / 2);

        while(last_row < 0) {
            last_row += arr_all.size();
        }

        for(int i = 0 ; i < total_row ; i++) {
            arr_show.add(arr_all.get(last_row));
            last_row++;
            if(last_row >= arr_all.size())
                last_row = 0;
        }

        first_row = last_row - total_row;

        while(first_row < 0) {
            first_row += arr_all.size();
        }

        last_row--;
        while(last_row < 0) {
            last_row += arr_all.size();
        }

        listView.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, arr_show));

        if(total_row % 2 == 0) {
            listView.setSelection(total_row / 2);
        } else {
            listView.setSelection(total_row / 2);
        }
    }
}
