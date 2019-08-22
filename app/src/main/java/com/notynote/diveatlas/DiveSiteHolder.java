package com.notynote.diveatlas;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


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
        imageView = itemView.findViewById(R.id.rImageVIew);

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
        Picasso.get().load(diveSiteInfo.getImage()).into(imageView);
    }

    public void setImage(String imgUrl){
        Picasso.get().load(imgUrl).into(imageView);
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public void setTxtType(TextView txtType) {
        this.txtType = txtType;
    }

    public void setTxtDepth(TextView txtDepth) {
        this.txtDepth = txtDepth;
    }

    public void setTxtCoor(TextView txtCoor) {
        this.txtCoor = txtCoor;
    }

    public void setTxtBio(TextView txtBio) {
        this.txtBio = txtBio;
    }
}
