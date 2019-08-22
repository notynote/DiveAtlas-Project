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

        LatLng Pak1 = new LatLng(12.0917,101.6822);
        //mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1 wreck").snippet("Type: Wreck\nDepth: 30 - 42 meters"));
        Marker pak1 = mMap.addMarker(new MarkerOptions().position(Pak1).title("Pak-1").snippet("Type: Wreck"));
        //pak1.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pak1));

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
