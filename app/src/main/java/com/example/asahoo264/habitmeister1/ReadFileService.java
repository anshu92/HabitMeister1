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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipInputStream;
import java.lang.Math;

class ReadFileService extends AsyncTask<String, String, Boolean> {
    Activity  main;
    String name;
    ProgressDialog prgDialog = null;
    List<Double> tp9 = new ArrayList<Double>();
    List<Double> fp1 = new ArrayList<Double>();
    List<Double> fp2 = new ArrayList<Double>();
    List<Double> tp10 = new ArrayList<Double>();
    private int counter =  1;


    public ReadFileService(Activity main, String name){
            this.main = main;
            this.name = name;
    }

    @Override
    protected Boolean doInBackground(String... args) {
        File dir = main.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, name);
        final String tag = "Muse File Reader";
        if (!file.exists()) {
            Log.w(tag, "file doesn't exist");
            return false;
        }
        MuseFileReader fileReader = MuseFileFactory.getMuseFileReader(file);

        while (fileReader.gotoNextMessage()) {
            MessageType type = fileReader.getMessageType();
            int id = fileReader.getMessageId();
            long timestamp = fileReader.getMessageTimestamp();
            Log.i(tag, "type: " + type.toString() +
                    " id: " + Integer.toString(id) +
                    " timestamp: " + String.valueOf(timestamp));
            switch(type) {
                case EEG: case BATTERY: case ACCELEROMETER: case QUANTIZATION:
                    MuseDataPacket packet = fileReader.getDataPacket();
                    tp9.add(packet.getValues().get(Eeg.TP9.ordinal()));
                    fp1.add(packet.getValues().get(Eeg.FP1.ordinal()));
                    fp2.add(packet.getValues().get(Eeg.FP2.ordinal()));
                    tp10.add(packet.getValues().get(Eeg.TP10.ordinal()));
                    break;
                case VERSION:
                    MuseVersion version = fileReader.getVersion();
                    Log.i(tag, "version" + version.getFirmwareType());
                    break;
                case CONFIGURATION:
                    MuseConfiguration config = fileReader.getConfiguration();
                    Log.i(tag, "config" + config.getBluetoothMac());
                    break;
                case ANNOTATION:
                    AnnotationData annotation = fileReader.getAnnotation();
                    Log.i(tag, "annotation" + annotation.getData());
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    protected void onProgressUpdate(String...progress) {
        //called when the background task makes any progress
    }

    protected void onPreExecute() {
        //called before doInBackground() is started
        super.onPreExecute();
        // Show Progress Bar Dialog before calling doInBackground method
        prgDialog = new ProgressDialog(main);
        prgDialog.setTitle("Opening File");
        prgDialog.setMessage("Opening " + name + "\nPlease wait...");
        prgDialog.show();
    }

    //called after doInBackground() has finished
    protected void onPostExecute(Boolean readFileSuccess) {
        // If unable to read from file, print error and generate a random set of sample data
        if(!readFileSuccess) {
            Random randomGenerator = new Random();
            System.out.println("@IOERROR: Unable to read from file. Creating random dataset");
            for(int i=0; i<128; i++)
            {
                tp9.add(randomGenerator.nextDouble());
                tp10.add(randomGenerator.nextDouble());
                fp1.add(randomGenerator.nextDouble());
                fp2.add(randomGenerator.nextDouble());


            }
        }

        final GraphView graph1 = (GraphView) main.findViewById(R.id.graph1);
        final GraphView graph2 = (GraphView) main.findViewById(R.id.graph2);
        final GraphView graph3 = (GraphView) main.findViewById(R.id.graph3);
        final GraphView graph4 = (GraphView) main.findViewById(R.id.graph4);

        graph1.setTitle("Tp9");
        graph2.setTitle("Fp1");
        graph3.setTitle("Fp2");
        graph4.setTitle("Tp10");
        // Plot the data points
        DataPoint[] datapoint_tp9 = new DataPoint[tp9.size()];
        DataPoint[] datapoint_fp1 = new DataPoint[fp1.size()];
        DataPoint[] datapoint_fp2 = new DataPoint[fp2.size()];
        DataPoint[] datapoint_tp10 = new DataPoint[tp10.size()];

        if((tp9.size() + fp1.size())-(tp10.size()+ fp2.size()) == 0) {
            for (int i = 0; i < tp9.size(); i++) {
                datapoint_tp9[i] = new DataPoint(i, tp9.get(i));
                datapoint_fp1[i] = new DataPoint(i, fp1.get(i));
                datapoint_fp2[i] = new DataPoint(i, fp2.get(i));
                datapoint_tp10[i] = new DataPoint(i, tp10.get(i));
            }
        }

        final DataPoint[] temp_tp9 = datapoint_tp9;
        final DataPoint[] temp_fp1 = datapoint_fp1;
        final DataPoint[] temp_fp2 = datapoint_fp2;
        final  DataPoint[] temp_tp10 = datapoint_tp10;
        final int max_counter = (int)Math.floor(tp9.size() / 128);
        prgDialog.dismiss();
        prgDialog = null;

        Timer t= new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                main.runOnUiThread(new Runnable() {
                    public void run() {
                if(counter >= max_counter){
                        graphData(Arrays.copyOfRange(temp_tp9, (counter - 1) * 128, temp_tp9.length), graph1);
                        graphData(Arrays.copyOfRange(temp_fp1, (counter - 1) * 128, temp_fp1.length), graph2);
                        graphData(Arrays.copyOfRange(temp_fp2, (counter - 1) * 128, temp_fp2.length), graph3);
                        graphData(Arrays.copyOfRange(temp_tp10, (counter - 1) * 128, temp_tp10.length), graph4);

                    return;
                }
                else{
                    graphData(Arrays.copyOfRange(temp_tp9, (counter-1)*128, counter*128), graph1);
                    graphData(Arrays.copyOfRange(temp_fp1,(counter-1)*128, counter*128), graph2);
                    graphData(Arrays.copyOfRange(temp_fp2, (counter-1)*128, counter*128), graph3);
                    graphData(Arrays.copyOfRange(temp_tp10,(counter-1)*128, counter*128), graph4);
                    counter++;
                }
                        graph1.getViewport().setMinX((counter - 2) * 128);
                        graph1.getViewport().setMaxX((counter - 1) * 128);
                        graph2.getViewport().setMinX((counter - 2) * 128);
                        graph2.getViewport().setMaxX((counter - 1) * 128);
                        graph3.getViewport().setMinX((counter - 2) * 128);
                        graph3.getViewport().setMaxX((counter - 1) * 128);
                        graph4.getViewport().setMinX((counter - 2) * 128);
                        graph4.getViewport().setMaxX((counter - 1) * 128);
                        String  text = "x-axis -> "+ Integer.toString((counter-2)) + " to " + Integer.toString((counter-1)) + " seconds";
                        Toast.makeText(main.getApplicationContext(),text,Toast.LENGTH_SHORT).show();


                }
                });
            }

        }, 0, 2000);


    }

    public void graphData(DataPoint[] finalData, GraphView graph){

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(finalData);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

    }


}