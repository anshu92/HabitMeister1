package com.example.asahoo264.habitmeister1;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import android.app.ActionBar.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.Random;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.os.AsyncTask;
import android.os.Handler;

public class FragmentFour extends Fragment {

    private ImageSwitcher sw;
    //private  int imageIds[]={R.drawable.a001,R.drawable.a001,R.drawable.n001,R.drawable.sp001,R.drawable.sn001,R.drawable.p001};
    private String[] emotions = {
            "Happy",
            "Content",
            "Calm",
            "Tired",
            "Bored",
            "Sad",
            "Upset"
    };

    private String[] urls = {
            "http://www.gordonrigg.com/the-hub/wp-content/uploads/2015/06/little_cute_cat_1920x1080.jpg",
            "http://images.forbes.com/media/lists/companies/google_416x416.jpg",
            "http://www.gordonrigg.com/the-hub/wp-content/uploads/2015/06/little_cute_cat_1920x1080.jpg"

    };

    private int max_imageid = 2;
    private int min_imageid = 0;
    private int currentIndex=-1;
    private SeekBar seekBar;
    private TextView textView;
    private TextView timertext;
    private CalibCountDownTimer timer;
    private final long startTime = 15000;
    private final long interval = 1000;
    private long timeElapsed;
    private boolean timerHasStarted = false;

    public static Fragment newInstance(Context context) {
    	FragmentFour f = new FragmentFour();
 
        return f;
    }

    @Override
    public void onStart() {

        super.onStart();
        //sw = (ImageSwitcher) ((MainActivity)getActivity()).findViewById(R.id.imageSwitcher);
        seekBar = (SeekBar) ((MainActivity)getActivity()).findViewById(R.id.seekBar);
        textView = (TextView) ((MainActivity)getActivity()).findViewById(R.id.progress);
        timertext = (TextView) ((MainActivity)getActivity()).findViewById(R.id.timer);
        timer = new CalibCountDownTimer(startTime,interval);
        final Random r = new Random();
        final ImageView im1 = (ImageView) ((MainActivity)getActivity()).findViewById(R.id.imageView3);
        new DownloadImageTask(im1).execute(urls[r.nextInt(max_imageid - min_imageid) + 1]);
//        new DownloadImageTask(im1).execute("https://drive.google.com/file/d/0ByMFXhN-e7luelcxVF9PUU9FdVU/view");

/*
                sw.setFactory(new ViewFactory() {
                    @Override
                    public View makeView() {
                        ImageView myView = new ImageView(getActivity().getApplicationContext());
                        myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        return myView;
                    }

        });
*/
        // Initialize the textview with '0'
        textView.setText("Your Choice: " + emotions[seekBar.getProgress()]);
        timertext.setText("Time: " + String.valueOf(startTime/1000)+" secs");


        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                // Toast.makeText(getActivity().getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getActivity().getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Your Choice: " + emotions[seekBar.getProgress()]);
                //Toast.makeText(getActivity().getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

/*
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        // set the animation type to imageSwitcher
        sw.setInAnimation(in);
        sw.setOutAnimation(out);
        final Random r = new Random();
    //    sw.setImageResource(imageIds[r.nextInt(max_imageid - min_imageid)]);
        sw.setImageResource(im1.getId());
        timer.start();
        sw.postDelayed(new Runnable() {
            int i = 0;

            public void run() {
             //   sw.setImageResource(imageIds[r.nextInt(max_imageid - min_imageid)]);
                sw.setImageResource(im1.getId());
                sw.postDelayed(this, 16000);
                timer.start();

            }
        }, 16000);
*/
        timer.start();
        Handler timerHandler = new Handler();
        timerHandler.postDelayed(new Runnable() {
            ImageView im1 = (ImageView) ((MainActivity)getActivity()).findViewById(R.id.imageView3);

            @Override
            public void run() {
                //   sw.setImageResource(imageIds[r.nextInt(max_imageid - min_imageid)]);
                new DownloadImageTask(im1).execute(urls[r.nextInt(max_imageid - min_imageid) + 1]);
                im1.postDelayed(this, 16000);
                timer.start();

            }
            public Runnable init(ImageView im1) {
                this.im1 = im1;
                return(this);
            }
        }.init(im1), 16000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_four, null);

        return root;
    }

    public class CalibCountDownTimer extends CountDownTimer
    {

        public CalibCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            timertext.setText("Time's up!");
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            timertext.setText("Time: " + millisUntilFinished/1000 + " secs");
            timeElapsed = startTime - millisUntilFinished;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }


/*    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        showProgressDialog();
    }*/

        protected void onPostExecute(Bitmap result) {
            //pDlg.dismiss();
            bmImage.setImageBitmap(result);
        }}
}
