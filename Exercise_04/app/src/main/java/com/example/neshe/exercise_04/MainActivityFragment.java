package com.example.neshe.exercise_04;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    final int DEFAULT_NUMBER = 5; //default is 5 minutes.
    int number = DEFAULT_NUMBER;

    public MainActivityFragment() {
    }

    //@Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final EditText editText = (EditText)view.findViewById(R.id.numberOfMinutes);
        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                try {
                    number = Integer.parseInt(editText.getText().toString());
                } catch (Exception e) {
                    Toast toast = Toast.makeText(v.getContext(), "Please choose a valid number", Toast.LENGTH_SHORT);
                    toast.show();
                    number = DEFAULT_NUMBER;
                }

                NotificationIntentService.startActionNotification(getContext(), number);
            }
        });

        return view;
    }
}
