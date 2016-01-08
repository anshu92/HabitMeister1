package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interaxon.libmuse.AnnotationData;
import com.interaxon.libmuse.Eeg;
import com.interaxon.libmuse.MessageType;
import com.interaxon.libmuse.MuseConfiguration;
import com.interaxon.libmuse.MuseDataPacket;
import com.interaxon.libmuse.MuseFileFactory;
import com.interaxon.libmuse.MuseFileReader;
import com.interaxon.libmuse.MuseVersion;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FragmentThree extends Fragment {

    public static int id = 3;
    public static String name = "new_muse_file.muse";
    public static ReadFileService ReadFile;

    public static Fragment newInstance(Context context) {
    	FragmentThree f = new FragmentThree();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_three, null);


        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String dir_str = dir.toString();

        String muse = dir_str + name;

        ReadFileService ReadFile = new ReadFileService(getActivity(),muse);

        ReadFile.execute("");
        return root;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        ReadFile.cancel(true);
    }

}