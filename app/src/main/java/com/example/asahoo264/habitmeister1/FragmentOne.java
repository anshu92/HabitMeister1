package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 
public class FragmentOne extends Fragment implements View.OnClickListener{

        private Muse tempmuse = null;
        private boolean dataTransmission = true;
        private int clench_count = 0;
        public static MainActivity.ConnectionListener connectionListener = null;
        public static MainActivity.DataListener dataListener = null;
        private static ArrayAdapter<String> adapterArray = null;
        update_conn_status mconnstatus = null;


        interface  update_conn_status{

            public void update_status(MuseConnectionPacket p);

            public void configureLibrary();

            public Muse getmuse();

            public void setmuse(Muse tempmuse);

    }


        @Override
        public void onClick(View v) {
            Spinner musesSpinner = (Spinner) getActivity().findViewById(R.id.muses_spinner);
            if (v.getId() == R.id.refresh) {
                MuseManager.refreshPairedMuses();
                List<Muse> pairedMuses = MuseManager.getPairedMuses();
                List<String> spinnerItems = new ArrayList<String>();
                for (Muse m: pairedMuses) {
                    String dev_id = m.getName() + "-" + m.getMacAddress();
                    Log.i("Muse Headband", dev_id);
                    spinnerItems.add(dev_id);
                }
                adapterArray = new ArrayAdapter<String> (
                        getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
                musesSpinner.setAdapter(adapterArray);
            }
            else if (v.getId() == R.id.connect) {
                List<Muse> pairedMuses = MuseManager.getPairedMuses();
                if (pairedMuses.size() < 1 ||
                        musesSpinner.getAdapter().getCount() < 1) {
                    Log.w("Muse Headband", "There is nothing to connect to");
                }
                else {

                    tempmuse = pairedMuses.get(musesSpinner.getSelectedItemPosition());
                    mconnstatus.setmuse(tempmuse);
                    ConnectionState state = tempmuse.getConnectionState();
                    if (state == ConnectionState.CONNECTED ||
                            state == ConnectionState.CONNECTING) {
                        Log.w("Muse Headband",
                                "doesn't make sense to connect second time to the same muse");
                        return;
                    }
                    mconnstatus.configureLibrary();

                    /**
                     * In most cases libmuse native library takes care about
                     * exceptions and recovery mechanism, but native code still
                     * may throw in some unexpected situations (like bad bluetooth
                     * connection). Print all exceptions here.
                     */
                    try {
                        tempmuse.runAsynchronously();
                    } catch (Exception e) {
                        Log.e("Muse Headband", e.toString());
                    }
                }
            }
            else if (v.getId() == R.id.disconnect) {
                if (tempmuse != null) {
                    /**
                     * true flag will force libmuse to unregister all listeners,
                     * BUT AFTER disconnecting and sending disconnection event.
                     * If you don't want to receive disconnection event (for ex.
                     * you call disconnect when application is closed), then
                     * unregister listeners first and then call disconnect:
                     * muse.unregisterAllListeners();
                     * muse.disconnect(false);
                     */
                    tempmuse.disconnect(true);

                }
            }

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
        refreshButton.setOnClickListener(this);
        Button connectButton = (Button) root.findViewById(R.id.connect);
        connectButton.setOnClickListener(this);
        Button disconnectButton = (Button) root.findViewById(R.id.disconnect);
        disconnectButton.setOnClickListener(this);

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

    public void configureLibrary() {





    }





}