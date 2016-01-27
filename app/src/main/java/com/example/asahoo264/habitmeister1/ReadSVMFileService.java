package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.app.ProgressDialog;
import android.view.ViewDebug;
import android.widget.Toast;

import com.example.asahoo264.habitmeister1.MainActivity;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipInputStream;
import java.lang.Math;

class ReadSVMFileService extends AsyncTask<String, String, Integer> {
    Activity main;
    String name;
    Integer result;


    public ReadSVMFileService(Activity main, String name) {
        this.main = main;
        this.name = name;
    }

    @Override
    protected Integer doInBackground(String... args) {

            File file1 = new File(name);

            try {
                FileInputStream fIn = new FileInputStream(file1);
                result = fIn.read();
                fIn.close();


            } catch (IOException e) {
                e.printStackTrace();

            }

    return result;

    }

    protected void onProgressUpdate(String... progress) {
        //called when the background task makes any progress
    }

    protected void onPreExecute() {
        //called before doInBackground() is started

    }

    //called after doInBackground() has finished
    protected void onPostExecute() {


        Toast.makeText(main, result.toString(), Toast.LENGTH_SHORT).show();

    }
}