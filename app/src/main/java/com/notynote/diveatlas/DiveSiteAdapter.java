package com.notynote.diveatlas;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DiveSiteAdapter extends RecyclerView.Adapter<DiveSiteAdapter.DiveSiteHolder> implements View.OnClickListener {

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

    public void filterList(ArrayList<DiveSiteInfo> filteredDiveSite){
        diveSites = filteredDiveSite;
        notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {

        TextView txtName = view.findViewById(R.id.txtName);

        String diveSiteName = txtName.getText().toString();

        Toast.makeText(view.getContext(), diveSiteName + " clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, DivesiteActivity.class);
        intent.putExtra("diveSiteName", diveSiteName);

        context.startActivity(intent);
    }

    public class DiveSiteHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtType, txtDepth;
        private Context context;
        protected ImageView imageView;

        public DiveSiteHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtType = itemView.findViewById(R.id.txtType);
            txtDepth = itemView.findViewById(R.id.txtDepth);
            imageView = itemView.findViewById(R.id.rImageVIew);

            itemView.setOnClickListener(DiveSiteAdapter.this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), txtName.getText() + " clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        public void setDetails(DiveSiteInfo diveSiteInfo) {
            txtName.setText(diveSiteInfo.getDiveSiteName());
            txtType.setText("Type : " + diveSiteInfo.getDiveSiteType());
            txtDepth.setText("Depth : " + diveSiteInfo.getDiveSiteDepth() + " meters");
            Picasso.get().load(diveSiteInfo.getImage()).into(imageView);

            Log.e(TAG, diveSiteInfo.diveSiteName);
        }

    }
}
