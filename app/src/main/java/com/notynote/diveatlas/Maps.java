package com.notynote.diveatlas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class Maps extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    //Layout Variable
    private GoogleMap mMap;
    private MapView mapView;

//    LatLng Pak1 = new LatLng(12.0917,101.6822);
//    LatLng Hardeep = new LatLng(12.5425,100.9624);
    LatLng KohRang = new LatLng(11.8056, 102.3911);
//    LatLng HinLukBat = new LatLng(11.943944636888816, 102.27224349975586);
//    LatLng ChangWreck = new LatLng(11.90044339282193, 102.25919723510744);

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    Map mapMark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        //assign database
        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final String[] diveSitesName = {"Pak-1", "Hardeep Wreck", "Koh Rang", "Hin Luk Bat", "HTMS Chang Wreck"};

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(String dSName : diveSitesName){
                    mapMark = (Map)dataSnapshot.child("Divesites").child(dSName).getValue();

                    addMarker(mMap,((Double)mapMark.get("lat")),((Double)mapMark.get("lng")),dSName,"Click to see detail");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1 wreck").snippet("Type: Wreck\nDepth: 30 - 42 meters"));

//        Marker pak1 = mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1").snippet("Coor: 12.0917,101.6822"));
//        Marker hardeep = mMap.addMarker(new MarkerOptions().position(Hardeep).title("Hardeep Wreck").snippet("Coor: 12.5425,100.9624"));
//        Marker kohrang = mMap.addMarker(new MarkerOptions().position(KohRang).title("Koh Rang").snippet("Coor: 11.8056, 102.3911"));
//        Marker hinlukbat = mMap.addMarker(new MarkerOptions().position(HinLukBat).title("Hin Luk Bat").snippet("Coor: 11.9439, 102.2722"));
//        Marker changwreck = mMap.addMarker(new MarkerOptions().position(ChangWreck).title("HTMS Chang Wreck").snippet("Coor: 11.9004, 102.2592"));

        //show Info Windows by default
        //pak1.showInfoWindow();

        //set default camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(KohRang));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);

        //activate onInfoWindowClickListener
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), marker.getTitle() + " Info window clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), DivesiteActivity.class);
        intent.putExtra("diveSiteName", marker.getTitle());

        getContext().startActivity(intent);
    }

    private void addMarker(GoogleMap map, double lat, double lon,
                           String title, String snippet) {

        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(title)
                .snippet(snippet));
    }

}
