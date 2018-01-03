package com.example.neshe.timeclient;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView time_slot = (TextView) view.findViewById(R.id.textView);
        Button timeButton = (Button) view.findViewById(R.id.showTime_button);
        timeButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {
                final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Fetching time from server...");
                progressDialog.show();
                final RequestQueue _queue = Volley.newRequestQueue(getContext());
                String url = "http://10.0.2.2:5000/getTime";
                // OR https://thawing-caverns-53468.herokuapp.com/getTime";

                StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        time_slot.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Error while fetching time from server", Toast.LENGTH_LONG);
                        return;
                    }
                });

                _queue.add(req);
            }
        });

        return view;
    }
}