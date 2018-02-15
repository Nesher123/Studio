package com.example.neshe.stockapp;

import android.app.ProgressDialog;
import android.content.SyncStatusObserver;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText stockName;
    private TextView stockData;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        stockName = (EditText) view.findViewById(R.id.StockName);
        stockData = (TextView) view.findViewById(R.id.text);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                if (stockName.getText().length() == 0) {
                    Toast.makeText(view.getContext(), "You did not enter a valid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    mSocket.emit("StockName", stockName.getText());
                }
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
                    try {
                        stockData.setText(args[0].toString());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };


    private void subscribeToStock () {
        mSocket.on("answer", onReceivedAnswer);
        mSocket.connect();
    }
}
