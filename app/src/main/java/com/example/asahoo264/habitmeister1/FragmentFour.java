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


public class FragmentFour extends Fragment {

    private ImageSwitcher sw;
    private  int imageIds[]={R.drawable.a001,R.drawable.a001,R.drawable.n001,R.drawable.sp001,R.drawable.sn001,R.drawable.p001};
    private String[] emotions = {
            "Happy",
            "Content",
            "Calm",
            "Tired",
            "Bored",
            "Sad",
            "Upset"
    };

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
        sw = (ImageSwitcher) ((MainActivity)getActivity()).findViewById(R.id.imageSwitcher);
        seekBar = (SeekBar) ((MainActivity)getActivity()).findViewById(R.id.seekBar);
        textView = (TextView) ((MainActivity)getActivity()).findViewById(R.id.progress);
        timertext = (TextView) ((MainActivity)getActivity()).findViewById(R.id.timer);
        timer = new CalibCountDownTimer(startTime,interval);

        sw.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getActivity().getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

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


        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        // set the animation type to imageSwitcher
        sw.setInAnimation(in);
        sw.setOutAnimation(out);
        final Random r = new Random();
        sw.setImageResource(imageIds[r.nextInt(5 - 0)]);
        timer.start();
        sw.postDelayed(new Runnable() {
            int i = 0;

            public void run() {
                sw.setImageResource(imageIds[r.nextInt(5 - 0)]);
                sw.postDelayed(this, 16000);
                timer.start();

            }
        }, 16000);

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
}
