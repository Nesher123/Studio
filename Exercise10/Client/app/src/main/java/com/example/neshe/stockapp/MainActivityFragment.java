package com.example.neshe.stockapp;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String serverUrl = "http://10.0.2.2:5000";
    // OR "https://frozen-atoll-20022.herokuapp.com/stock"
    private TextView openText;
    private TextView highText;
    private TextView lowText;
    private TextView closeText;
    private TextView volumeText;
    private Socket mSocket;
    {
        try
        {
            mSocket = IO.socket(serverUrl);
        }
        catch (URISyntaxException e) {}
    }


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mSocket.on("stockData", onReceivedAnswer);
        mSocket.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        openText = (TextView) view.findViewById(R.id.open_text);
        highText = (TextView) view.findViewById(R.id.high_text);
        lowText = (TextView) view.findViewById(R.id.low_text);
        closeText = (TextView) view.findViewById(R.id.close_text);
        volumeText = (TextView) view.findViewById(R.id.volume_text);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {
                final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                // progressDialog.setMessage("Fetching data from server...");
                // progressDialog.show();
                mSocket.emit("StockName", "MSFT");

            }
        });

        return view;
    }



    private Emitter.Listener onReceivedAnswer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = null;
                    String open, high, low, close, volume;
                    try {
                        data = new JSONObject(args[0].toString());
                    } catch (Exception e) {

                    }


                    try {
                        open = data.getString("open");
                        high = data.getString("high");
                        low = data.getString("low");
                        close = data.getString("close");
                        volume = data.getString("volume");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    displayData(open, high, low, close, volume);
                }
            });
        }
    };


    private void subscribeToStock () {
        mSocket.on("answer", onReceivedAnswer);
        mSocket.connect();
        
    }

    private void displayData (String... args) {
        openText.setText(args[0]);
        highText.setText(args[1]);
        lowText.setText(args[2]);
        closeText.setText(args[3]);
        volumeText.setText(args[4]);
    }
}
