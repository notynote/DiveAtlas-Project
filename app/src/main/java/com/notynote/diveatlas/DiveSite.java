package com.notynote.diveatlas;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class DiveSite extends Fragment {

    private RecyclerView recyclerView;
    private DiveSiteAdapter adapter;
    private ArrayList<DiveSiteInfo> diveSiteInfoArrayList;
    //FirebaseDatabase mFirebaseDatabase;
    //DatabaseReference mRef;
    //private FirebaseRecyclerAdapter<DiveSiteInfo, DiveSiteHolder> mFirebaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dive_site, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //Filter box
        EditText editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diveSiteInfoArrayList = new ArrayList<>();

        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //recyclerView.addItemDecoration(new LineDividerItemDecoration(getActivity(), R.drawable.line_divider));

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mRef = mFirebaseDatabase.getReference("Data");
//        Query diveSiteQuery = mRef.orderByKey();
//
//        FirebaseRecyclerOptions mFireOption = new FirebaseRecyclerOptions.Builder<DiveSiteInfo>().setQuery(diveSiteQuery, DiveSiteInfo.class).build();

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<DiveSiteInfo, DiveSiteHolder>(mFireOption) {
//            @Override
//            protected void onBindViewHolder(@NonNull DiveSiteHolder diveSiteHolder, int i, @NonNull DiveSiteInfo diveSiteInfo) {
//                diveSiteHolder.setDetails(diveSiteInfo);
//            }
//
//            @NonNull
//            @Override
//            public DiveSiteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                return null;
//            }
//        };

        //recyclerView.setAdapter(mFirebaseAdapter);


        adapter = new DiveSiteAdapter(getActivity(), diveSiteInfoArrayList);
        recyclerView.setAdapter(adapter);
        createListData();

        return view;
    }

    private void filter(String text){
        ArrayList<DiveSiteInfo> filteredDiveSite = new ArrayList<>();

        for (DiveSiteInfo sites : diveSiteInfoArrayList){
            if (sites.getDiveSiteName().toLowerCase().contains(text.toLowerCase())){
                filteredDiveSite.add(sites);
            }
        }

        adapter.filterList(filteredDiveSite);
    }

    private void createListData() {
        String defaultImage = "https://firebasestorage.googleapis.com/v0/b/diveatlas-project.appspot.com/o/dive%20atlas%20dive%20site%20default.png?alt=media&token=d73e414a-5c4b-414b-993e-440512255899";

        DiveSiteInfo diveSiteAdd = new DiveSiteInfo("Pak-1","Wreck","30 to 42","12.0917,101.6822","The Koho Maru-5 tanker sank first Aug 25, 1996, 50 kilometers west of Koh Chang.", defaultImage);
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Hardeep Wreck","Wreck","25 to 30","12.5425,100.9624","The Hardeep (Suddhadib) Wreck is one of the most exciting wreck dives in Pattaya due to the vessels’ history and the duration it has spent on the seabed.", defaultImage);
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Koh Rang","Reef","12 to 15","11.8056, 102.3911","Yellowtail Barracudas, Stonefish, Murene, Catfish, Triggerfish, bannerfish , Angelfish and lots of more nice marine-life!!", "https://firebasestorage.googleapis.com/v0/b/diveatlas-project.appspot.com/o/KohRang_.jpg?alt=media&token=581dcb41-da0e-46dc-a0e7-312a8cb61842");
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Hin Luk Bat","Reef","12 to 25","11.9439, 102.2722","The pinnacle is surrounded by huge underwater boulders which offer some great swim through.", defaultImage);
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("HTMS Chang Wreck","Wreck","5 to 30","11.9004, 102.2592","On the 22nd of November 2012 the HTMS Chang has been sunk. The wreck quickly turned into a great artificial reef thriving with marine life, shells, coral, gorgonians... It is truly a spectacular wreck dive.", "https://firebasestorage.googleapis.com/v0/b/diveatlas-project.appspot.com/o/htmsChangWreck.jpg?alt=media&token=452d91e1-04fc-454f-abbe-a36dedac5082");
        diveSiteInfoArrayList.add(diveSiteAdd);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mFirebaseAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mFirebaseAdapter.stopListening();
//    }



}
