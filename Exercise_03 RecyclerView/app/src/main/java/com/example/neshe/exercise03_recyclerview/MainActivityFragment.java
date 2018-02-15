package com.example.neshe.exercise03_recyclerview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        /* initializing the two buttons */
        Button button_lannister = (Button) myView.findViewById(R.id.button_lannister);
        Button button_stark = (Button) myView.findViewById(R.id.button_stark);

        button_lannister.setOnClickListener(new View.OnClickListener() {
            //@Override 
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Lannister.class);
                startActivity(intent);
            }
        });

        button_stark.setOnClickListener(new View.OnClickListener() {
            //@Override 
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Stark.class);
                startActivity(intent);
            }
        });

        return myView;
    }
}
