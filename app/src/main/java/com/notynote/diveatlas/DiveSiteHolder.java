package com.notynote.diveatlas;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public class DiveSiteHolder extends RecyclerView.ViewHolder {

    private TextView txtName, txtType, txtDepth, txtCoor, txtBio;
    private Context context;
    protected ImageView imageView;

    public DiveSiteHolder(View itemView){
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtType = itemView.findViewById(R.id.txtType);
        txtDepth = itemView.findViewById(R.id.txtDepth);
        txtCoor = itemView.findViewById(R.id.txtCoor);
        txtBio = itemView.findViewById(R.id.txtBio);
        imageView = itemView.findViewById(R.id.thumbnail);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click Event
            }
        });
    }

    public void setDetails(DiveSiteInfo diveSiteInfo) {
        txtName.setText(diveSiteInfo.getDiveSiteName());
        txtType.setText("Type : " + diveSiteInfo.getDiveSiteType());
        txtDepth.setText("Depth : " + diveSiteInfo.getDiveSiteDepth() + " meters");
        txtCoor.setText("Coordinate : " + diveSiteInfo.getDiveSiteCoor());
        txtBio.setText(diveSiteInfo.getDiveSiteBio());
    }
}
