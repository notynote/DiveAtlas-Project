package com.notynote.diveatlas;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DiveSite extends Fragment {

    private RecyclerView recyclerView;
    private DiveSiteAdapter adapter;
    private ArrayList<DiveSiteInfo> diveSiteInfoArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dive_site, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diveSiteInfoArrayList = new ArrayList<>();

        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //recyclerView.addItemDecoration(new LineDividerItemDecoration(getActivity(), R.drawable.line_divider));

        adapter = new DiveSiteAdapter(getActivity(), diveSiteInfoArrayList);
        recyclerView.setAdapter(adapter);
        createListData();

        return view;
    }

    private void createListData() {
        DiveSiteInfo diveSiteAdd = new DiveSiteInfo("Pak-1","Wreck","30 to 42","12.0917,101.6822","The Koho Maru-5 tanker sank first Aug 25, 1996, 50 kilometers west of Koh Chang.");
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Hardeep Wreck","Wreck","25 to 30","12.5425,100.9624","The Hardeep (Suddhadib) Wreck is one of the most exciting wreck dives in Pattaya due to the vessels’ history and the duration it has spent on the seabed.");
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Koh Rang","Reef","12 to 15","11.8056, 102.3911","Yellowtail Barracudas, Stonefish, Murene, Catfish, Triggerfish, bannerfish , Angelfish and lots of more nice marine-life!!");
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("Hin Luk Bat","Reef","12 to 25","11.9439, 102.2722","The pinnacle is surrounded by huge underwater boulders which offer some great swim through.");
        diveSiteInfoArrayList.add(diveSiteAdd);
        diveSiteAdd = new DiveSiteInfo("HTMS Chang Wreck","Wreck","5 to 30","11.9004, 102.2592","On the 22nd of November 2012 the HTMS Chang has been sunk. The wreck quickly turned into a great artificial reef thriving with marine life, shells, coral, gorgonians... It is truly a spectacular wreck dive.");
        diveSiteInfoArrayList.add(diveSiteAdd);
    }



}
