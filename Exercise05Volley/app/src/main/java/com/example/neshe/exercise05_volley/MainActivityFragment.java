package com.example.neshe.exercise05_volley;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url = "http://www.google.com/search?q=";

        Button button = (Button)view.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String textToSearch = ((EditText)getView().findViewById(R.id.searchText)).getText().toString();
                final TextView result = (TextView)getView().findViewById(R.id.resultText);

                final StringRequest req = new StringRequest(Request.Method.GET, url + textToSearch, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!textToSearch.equals("")) {
                            result.setText(getTopResultTitle(response));
                        }
                        else {
                            result.setText("");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivityFragment", "Encountered error - " + error);
                    }
                });

                queue.add(req);
            }
        });

        return view;
    }

    private String getTopResultTitle(String textToSearch) {
        //String url = "http://www.google.com/webhp?#q=" + textToSearch + "&btnI=I";
        int index;

        index = textToSearch.indexOf("<body class");
        textToSearch = textToSearch.substring(index);

        index = textToSearch.indexOf("class=\"srg");
        textToSearch = textToSearch.substring(index);

        index = textToSearch.indexOf("<span dir=");
        textToSearch = textToSearch.substring(index);

        index = textToSearch.indexOf("</span>");
        textToSearch = textToSearch.substring(16, index);

        return textToSearch;
    }
}
