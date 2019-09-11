package com.notynote.diveatlas;

import android.os.Bundle;

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


public class Maps extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    //Layout Variable
    private GoogleMap mMap;
    private MapView mapView;

    LatLng Pak1 = new LatLng(12.0917,101.6822);
    LatLng Hardeep = new LatLng(12.5425,100.9624);
    LatLng KohRang = new LatLng(11.8056, 102.3911);
    LatLng HinLukBat = new LatLng(11.943944636888816, 102.27224349975586);
    LatLng ChangWreck = new LatLng(11.90044339282193, 102.25919723510744);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1 wreck").snippet("Type: Wreck\nDepth: 30 - 42 meters"));

        Marker pak1 = mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1").snippet("Coor: 12.0917,101.6822"));
        Marker hardeep = mMap.addMarker(new MarkerOptions().position(Hardeep).title("Hardeep Wreck").snippet("Coor: 12.5425,100.9624"));
        Marker kohrang = mMap.addMarker(new MarkerOptions().position(KohRang).title("Koh Rang").snippet("Coor: 11.8056, 102.3911"));
        Marker hinlukbat = mMap.addMarker(new MarkerOptions().position(HinLukBat).title("Hin Luk Bat").snippet("Coor: 11.9439, 102.2722"));
        Marker changwreck = mMap.addMarker(new MarkerOptions().position(ChangWreck).title("HTMS Chang Wreck").snippet("Coor: 11.9004, 102.2592"));

        //show Info Windows by default
        //pak1.showInfoWindow();

        //set default camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pak1));

        //activate onInfoWindowClickListener
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), "Info window clicked", Toast.LENGTH_SHORT).show();
    }

    private void addMarker(GoogleMap map, double lat, double lon,
                           int title, int snippet) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(getString(title))
                .snippet(getString(snippet)));
    }

}
