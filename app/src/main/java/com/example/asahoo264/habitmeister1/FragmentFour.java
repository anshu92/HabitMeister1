package com.example.asahoo264.habitmeister1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import android.app.ActionBar.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;


public class FragmentFour extends Fragment {

    private ImageSwitcher sw;
    private final Random r = new Random();
    private Bitmap bitmap;
    public static int id = 4;
    private  int imageIds[]={R.drawable.a001,R.drawable.a001,R.drawable.n001,R.drawable.sp001,R.drawable.sn001,R.drawable.p001};
    private String[] emotions = {
            "PLEASURE",
            "AVERSION"
    };

    private String[] urls = {
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A002.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A004.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A005.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A007.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A019.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A029.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A032.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A048.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A049.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A050.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A051.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A052.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A053.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A054.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A055.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A056.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A057.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A058.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A059.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A060.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A063.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A067.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A068.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A069.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A070.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A071.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A072.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A073.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A074.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A075.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A084.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A092.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A103.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A110.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A111.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A112.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A113.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A114.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A115.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A116.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A117.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A118.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A119.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A120.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A121.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A122.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A123.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A124.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A125.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A127.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A128.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A129.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A130.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luaUo1UGRJSTlKWE0/A131.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H002.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H003.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H005.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H007.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H012.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H019.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H029.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H032.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H048.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H049.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H050.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H051.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H052.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H053.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H054.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H055.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H056.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H057.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H058.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H059.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H060.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H063.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H065.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H066.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H074.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H084.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H092.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H103.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H110.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H112.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H122.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H123.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H124.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H125.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luMGNFN3JNUDRXems/H126.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N002.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N003.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N004.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N019.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N032.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N046.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N065.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N066.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N067.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N068.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N069.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N070.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N071.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N072.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N073.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N075.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N092.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N096.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luZkc5dVZjaTBRMlE/N111.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P003.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P004.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P005.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P007.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P012.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P029.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P046.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P048.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P049.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P050.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P052.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P053.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P054.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P055.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P056.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P057.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P058.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P060.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P063.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P065.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P066.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P067.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P069.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P070.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P071.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P072.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P073.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P074.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P075.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P084.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P092.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P096.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P103.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P110.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P111.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P112.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P113.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P114.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P116.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P117.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P118.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P119.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P121.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P122.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P123.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P124.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P125.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P127.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P128.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P129.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luNVFXZnEzWnluQk0/P130.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn002.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn003.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn004.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn005.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn007.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn012.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn019.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn029.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn032.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn046.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn048.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn049.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn050.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn051.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn052.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn053.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn054.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn055.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn056.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn057.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn058.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn059.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn060.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn063.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn065.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn066.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn067.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn068.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn069.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn070.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn071.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn072.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn073.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn074.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn075.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn084.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn092.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn096.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn103.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn110.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn111.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn112.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn113.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn114.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn115.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn116.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn117.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn118.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn119.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn120.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn121.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn122.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn123.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn124.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn125.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn126.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn127.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn128.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn129.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn130.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn131.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn132.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luT3VBRW1SbUNRNUk/Sn133.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp001.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp002.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp003.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp004.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp005.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp006.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp007.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp008.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp009.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp010.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp011.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp012.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp013.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp014.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp015.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp016.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp017.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp018.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp019.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp020.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp021.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp022.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp023.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp024.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp025.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp026.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp027.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp028.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp029.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp030.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp031.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp032.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp033.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp034.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp035.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp036.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp037.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp038.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp039.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp040.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp041.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp042.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp043.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp044.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp045.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp046.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp047.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp048.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp049.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp050.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp051.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp052.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp053.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp054.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp055.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp056.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp057.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp058.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp060.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp061.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp062.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp063.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp064.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp065.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp066.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp067.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp068.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp069.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp070.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp071.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp072.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp073.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp074.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp075.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp076.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp077.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp078.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp079.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp080.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp081.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp082.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp083.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp084.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp085.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp086.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp087.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp088.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp089.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp090.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp091.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp093.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp094.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp095.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp096.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp097.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp098.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp099.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp100.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp101.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp102.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp103.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp104.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp105.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp106.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp107.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp108.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp109.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp110.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp111.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp112.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp113.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp114.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp115.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp116.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp117.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp118.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp119.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp120.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp121.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp122.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp123.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp124.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp125.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp126.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp127.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp128.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp129.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp130.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp131.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp132.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp133.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp134.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp135.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp136.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp137.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp138.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp139.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp140.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp141.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp142.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp143.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp144.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp145.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp146.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp147.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp148.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp149.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp150.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp151.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp152.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp153.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp154.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp155.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp156.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp157.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp158.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp159.bmp",
            "https://googledrive.com/host/0ByMFXhN-e7luRmVCOC00V2QxWUE/Sp160.bmp"

    };

    private int max_imageid = 730;
    private int min_imageid = 0;
    private int currentIndex=-1;
    private Switch seekBar;
    private TextView textView;
    private TextView timertext;
    private CalibCountDownTimer timer;
    private final long startTime = 10000;
    private final long interval = 1000;
    private long timeElapsed;
    private boolean timerHasStarted = false;
    private int counter=1;
    private int counter1=1;
        private boolean is_train = true;
        private int number_images = 10;
        private boolean switch_state = true;

    private int fix_cross = R.drawable.fixation;


    public static Fragment newInstance(Context context) {
    	FragmentFour f = new FragmentFour();
 
        return f;
    }

    @Override
    public void onStart() {

        super.onStart();
        sw = (ImageSwitcher) (getActivity()).findViewById(R.id.imageSwitcher);
        seekBar = (Switch) (getActivity()).findViewById(R.id.switchbar);
        textView = (TextView) (getActivity()).findViewById(R.id.progress);
        timertext = (TextView) (getActivity()).findViewById(R.id.timer);
        timer = new CalibCountDownTimer(startTime,interval);

            seekBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {

                            switch_state = isChecked;
                            if (isChecked)
                                    textView.setText(emotions[1]);
                            else
                                    textView.setText(emotions[0]);


                    }
            });
        sw.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getActivity().getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

            int index_state = 0;
        if(switch_state)
                index_state = 0;
         else
                index_state = 1;

        // Initialize the textview with '0'
        textView.setText(emotions[index_state]);
            timertext.setText(String.valueOf(startTime/1000)+" secs");




       // Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        //Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        // set the animation type to imageSwitcher
        //sw.setInAnimation(in);
        //sw.setOutAnimation(out);


            Drawable image  = new BitmapDrawable(getResources(),bitmap);
            new LoadImage().execute(urls[r.nextInt(urls.length - 1)]);
            sw.setImageResource(R.drawable.calibration_message);
            sw.setFitsSystemWindows(true);

            timer.start();
        sw.postDelayed(new Runnable() {
            int i = 0;


                public void run() {
                if(counter1++ > number_images - 1){
                    counter1 = 1;
                    ((MainActivity)getActivity()).start_recording = false;
                    return;
                }
                counter = 1;
                    if(counter1 > 0.5 * number_images ) {
                            is_train = false;
                    }
                    else {
                            is_train = true;
                    }
                        if(counter1 >  2) {
                                try {
                                        ((MainActivity) getActivity()).register_event(is_train, switch_state);
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }

                            ((MainActivity) getActivity()).start_of_event = false;

                    Drawable image  = new BitmapDrawable(getResources(), bitmap);
                    sw.setImageDrawable(image);
                    sw.setFitsSystemWindows(true);
                    ((MainActivity)getActivity()).start_of_event = true;

                    sw.postDelayed(this, 11000);
                timer.start();

            }


        }, 11000);

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
            if(counter++ == 6) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sw.setImageDrawable(null);
                        new LoadImage().execute(urls[r.nextInt(urls.length - 1)]);


                    }
                });

            }

            timertext.setText(millisUntilFinished / 1000 + " secs");
            timeElapsed = startTime - millisUntilFinished;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

        private class LoadImage extends AsyncTask<String, String, Bitmap> {
                @Override
                protected void onPreExecute() {
                        super.onPreExecute();

                }
                protected Bitmap doInBackground(String... args) {
                        try {
                                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
                               bitmap = getResizedBitmap(bitmap,sw.getHeight(),sw.getWidth());


                        } catch (Exception e) {
                                e.printStackTrace();
                        }


                        return bitmap;
                }

                protected void onPostExecute(Bitmap image) {

                        if(image != null){


                        }else{

                                Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

                        }
                }
        }


        public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

                int width = bm.getWidth();

                int height = bm.getHeight();

                float scaleWidth = ((float) newWidth) / width;

                float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

                Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

                matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

                Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
                return resizedBitmap;

        }

}
