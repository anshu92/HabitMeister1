package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interaxon.libmuse.ConnectionState;
import com.interaxon.libmuse.MuseConnectionPacket;


public class FragmentTwo extends Fragment {



    public static Fragment newInstance(Context context) {
    	FragmentTwo f = new FragmentTwo();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_two, null);
        return root;
    }

    public void update_status(MuseConnectionPacket p){

        final ConnectionState current = p.getCurrentConnectionState();
        final String status = p.getPreviousConnectionState().toString() +
                " -> " + current;
        final String full = "Muse " + p.getSource().getMacAddress() +
                " " + status;
        Log.i("Muse Headband", full);
        Activity activity = getActivity();
        // UI thread is used here only because we need to update
        // TextView values. You don't have to use another thread, unless
        // you want to run disconnect() or connect() from connection packet
        // handler. In this case creating another thread is required.
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView statusText =
                            (TextView) ((MainActivity)getActivity()).findViewById(R.id.con_status_two);
                    statusText.setText(status);
//                       TextView museVersionText =
//                               (TextView) findViewById(R.id.version);
//                        if (current == ConnectionState.CONNECTED) {
//                            MuseVersion museVersion = muse.getMuseVersion();
//                            String version = museVersion.getFirmwareType() +
//                                 " - " + museVersion.getFirmwareVersion() +
//                                 " - " + Integer.toString(
//                                    museVersion.getProtocolVersion());
//                            museVersionText.setText(version);
//                        } else {
//                            museVersionText.setText(R.string.undefined);
//                        }
                }
            });
        }
    }
}