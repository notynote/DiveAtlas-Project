package com.notynote.diveatlas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNewsHolder> {

    private Context context;
    private ArrayList<HomeNewsInfo> homeNews;

    public  HomeNewsAdapter(Context context, ArrayList<HomeNewsInfo> homeNews){

        this.context = context;
        this.homeNews = homeNews;

    }

    @NonNull
    @Override
    public HomeNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_item_row, parent, false);

        return new HomeNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewsHolder holder, int position) {
        HomeNewsInfo homeNewsItem = homeNews.get(position);
        holder.setDetails(homeNewsItem);

    }

    @Override
    public int getItemCount() {
        return homeNews.size();
    }

    public class HomeNewsHolder extends RecyclerView.ViewHolder {

        private TextView txtHeading, txtDate, txtDesc;
        private Context context;
        protected ImageView imageView;

        public HomeNewsHolder(View itemView) {
            super(itemView);

            txtHeading = itemView.findViewById(R.id.homeNewsHeading);
            txtDate = itemView.findViewById(R.id.homeNewsDate);
            txtDesc = itemView.findViewById(R.id.homeNewsDescription);
            imageView = itemView.findViewById(R.id.homeNewsImages);

        }

        public void setDetails(HomeNewsInfo homeNewsInfo){

            txtHeading.setText(homeNewsInfo.getHomeNewsHeading());
            txtDate.setText(homeNewsInfo.getHomeNewsDate());
            txtDesc.setText(homeNewsInfo.getHomeNewsDesc());
            Picasso.get().load(homeNewsInfo.getHomeNewsImage()).into(imageView);

        }

    }
}
