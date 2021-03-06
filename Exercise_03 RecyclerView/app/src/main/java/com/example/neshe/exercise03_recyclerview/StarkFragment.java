package com.example.neshe.exercise03_recyclerview;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarkFragment extends Fragment {

    public StarkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stark, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.stark_list);
// Setup layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new myAdapter(DataFile.starkNames));

        return view;
    }
}
