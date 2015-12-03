package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.interaxon.libmuse.Accelerometer;
import com.interaxon.libmuse.AnnotationData;
import com.interaxon.libmuse.ConnectionState;
import com.interaxon.libmuse.Eeg;
import com.interaxon.libmuse.LibMuseVersion;
import com.interaxon.libmuse.Muse;
import com.interaxon.libmuse.MuseArtifactPacket;
import com.interaxon.libmuse.MuseConfiguration;
import com.interaxon.libmuse.MuseConnectionListener;
import com.interaxon.libmuse.MuseConnectionPacket;
import com.interaxon.libmuse.MuseDataListener;
import com.interaxon.libmuse.MuseDataPacket;
import com.interaxon.libmuse.MuseDataPacketType;
import com.interaxon.libmuse.MuseFileWriter;
import com.interaxon.libmuse.MuseManager;
import com.interaxon.libmuse.MusePreset;
import com.interaxon.libmuse.MuseVersion;

import android.support.v7.widget.Toolbar;
 
public class FragmentOne extends Fragment{

        private boolean dataTransmission = true;
        private int clench_count = 0;
       // public static MainActivity.ConnectionListener connectionListener = null;
        //public static MainActivity.DataListener dataListener = null;
        public static int id = 1;

        public static update_conn_status mconnstatus = null;


        interface  update_conn_status{

            public void setonclickstuff(Button refreshButton, Button connectButton, Button disconnectButton);

    }



    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof update_conn_status)
        {
            mconnstatus = (update_conn_status)activity;
        }
        else
        {
            throw new ClassCastException();
        }

    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


    public static Fragment newInstance(Context context) {
    	FragmentOne f = new FragmentOne();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_one, null);

        Button refreshButton = (Button) root.findViewById(R.id.refresh);
        Button connectButton = (Button) root.findViewById(R.id.connect);
        Button disconnectButton = (Button) root.findViewById(R.id.disconnect);
        ((MainActivity)getActivity()).updating_conn = true;


        mconnstatus.setonclickstuff(refreshButton,connectButton,disconnectButton);

        // // Uncommet to test Muse File Reader
        //
        // // file can be big, read it in a separate thread
        // Thread thread = new Thread(new Runnable() {
        //     public void run() {
        //         playMuseFile("testfile.muse");
        //     }
        // });
        // thread.start();

//        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        fileWriter = MuseFileFactory.getMuseFileWriter(
//                new File(dir, "new_muse_file.muse"));
//        Log.i("Muse Headband", "libmuse version=" + LibMuseVersion.SDK_VERSION);
//        fileWriter.addAnnotationString(1, "MainActivity onCreate");
//        dataListener.setFileWriter(fileWriter);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).updating_conn = true;
        final String status = ((MainActivity)getActivity()).previous_connection_state +
                " -> " + ((MainActivity)getActivity()).connection_state;

        final Activity activity = ((MainActivity)getActivity());
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Spinner musesSpinner = (Spinner) activity.findViewById(R.id.muses_spinner);
                    musesSpinner.setAdapter(((MainActivity)getActivity()).adapterArray_status);

                    TextView statusText =
                            (TextView) activity.findViewById(R.id.con_status);

                    if(statusText != null)
                        statusText.setText(status);

                    TextView museVersionText =
                            (TextView) activity.findViewById(R.id.version);
                    if (((MainActivity)getActivity()).connection_state == ConnectionState.CONNECTED) {
                        MuseVersion museVersion = ((MainActivity)getActivity()).muse.getMuseVersion();
                        String version = museVersion.getFirmwareType() +
                                " - " + museVersion.getFirmwareVersion() +
                                " - " + Integer.toString(
                                museVersion.getProtocolVersion());
                        museVersionText.setText(version);
                    } else {
                        museVersionText.setText(R.string.undefined);
                    }
                }
            });
        }

    }

    @Override
    public void onStop(){
        super.onStop();
        ((MainActivity)getActivity()).updating_conn = false;


    }
}