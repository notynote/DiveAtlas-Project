package com.notynote.diveatlas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiveSiteAdapter extends RecyclerView.Adapter<DiveSiteHolder> {

    private Context context;

    private ArrayList<DiveSiteInfo> diveSites;

    public DiveSiteAdapter(Context context, ArrayList<DiveSiteInfo> diveSites){

        this.context = context;
        this.diveSites = diveSites;
    }

    @Override
    public int getItemCount(){
        return diveSites.size();
    }

    @Override
    public DiveSiteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new DiveSiteHolder(view);
    }

    @Override
    public void onBindViewHolder(DiveSiteHolder holder, int position){
        DiveSiteInfo diveSite = diveSites.get(position);
        holder.setDetails(diveSite);
    }

}
