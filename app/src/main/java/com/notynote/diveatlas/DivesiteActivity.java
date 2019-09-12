package com.notynote.diveatlas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DivesiteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Context context;

    TextView nameText, typeText, depthText, coorText, descText;
    Button backBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userUID;

    String diveSiteNameKeep;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    Map map, mapMark;

    private GoogleMap mMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divesite);

        Bundle bundle = getIntent().getExtras();
        String diveSiteName = bundle.getString("diveSiteName");

        Toast.makeText(this, "Got " + diveSiteName, Toast.LENGTH_SHORT).show();

        nameText = findViewById(R.id.diveSitePageTxtName);
        typeText = findViewById(R.id.diveSitePageTxtType);
        depthText = findViewById(R.id.diveSitePageTxtDepth);
        coorText = findViewById(R.id.diveSitePageTxtCoor);
        descText = findViewById(R.id.diveSitePageTxtDesc);

        //Map
        mapView = findViewById(R.id.diveSiteMap);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();;
        mapView.getMapAsync(this);

//        //get user
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        userUID = firebaseUser.getUid();
        diveSiteNameKeep = diveSiteName;

        //assign database
        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference();

        //database reading
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                map = (Map)dataSnapshot.child("Divesites").child(diveSiteNameKeep).getValue();

                String diveSiteType = String.valueOf(map.get("diveSiteType"));
                String diveSiteCoor = String.valueOf(map.get("diveSiteCoor"));
                String diveSiteBio = String.valueOf(map.get("diveSiteBio"));
                String diveSiteDepth = String.valueOf(map.get("diveSiteDepth"));

                nameText.setText(diveSiteNameKeep);
                typeText.setText("Type: " + diveSiteType);
                depthText.setText("Depth: " + diveSiteDepth + " meter");
                coorText.setText("Location: " + diveSiteCoor);
                descText.setText(diveSiteBio);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void BackToHome(View view) {

        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mapMark = (Map)dataSnapshot.child("Divesites").child(diveSiteNameKeep).getValue();
                String diveSiteCoor = String.valueOf(map.get("diveSiteCoor"));
                Double lat = ((Double)map.get("lat"));
                Double lng = ((Double)map.get("lng"));

                LatLng diveSitePosition = new LatLng(lat,lng);

                Marker diveSiteMarker = mMap.addMarker(new MarkerOptions().position(diveSitePosition).title(diveSiteNameKeep).snippet("Coor: " + diveSiteCoor));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(diveSitePosition));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
