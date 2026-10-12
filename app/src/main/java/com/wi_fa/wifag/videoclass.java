package com.wi_fa.wifag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by ahmedalalimi on 14/11/2017.
 */

public class videoclass extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    int totalSize,downloadedSize;
    TextView counter;
    ImageView close,imptyv;
    Button visturl;
    ProgressDialog mProgressDialog;
    public static int ready = 0;
    public static int timeads = 0;
    public static int adspostion = 0;
    private int ss = 0;
    public static String urlvideo = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        videoView = (VideoView)findViewById(R.id.videoView);
        close = (ImageView) findViewById(R.id.close_btn);
        close.setVisibility(View.GONE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imptyv = (ImageView) findViewById(R.id.viewimpty);
        counter = (TextView) findViewById(R.id.counter_txt);
        counter.setText(String.valueOf(timeads));
        visturl = (Button) findViewById(R.id.ads_click_pg);
        timeads = Func.v09ArrayList.get(adspostion).getTimeallds();
        visturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Func.v09ArrayList.get(adspostion).getUrlclick().contains(Str.attr.c022)){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Str.attr.c021 + Func.v09ArrayList.get(adspostion).getUrlclick()));
                    startActivity(browserIntent);
                }
            }
        });
        if(timeads == 0){
            finish();
        }
        //String SDCardRoot = Environment.getExternalStorageDirectory() + File.separator + getPackageName() + File.separator + Str.attr.c016;
        //File SDCardRoot = Environment.getExternalStorageDirectory();
        //File file = new File(SDCardRoot,Func.v09ArrayList.get(adspostion).getNamev());
        //File file = new File(getApplicationContext().getCacheDir(), Func.v09ArrayList.get(adspostion).getNamev());
        urlvideo = Str.attr.c017 + Str.attr.c020 + Func.v09ArrayList.get(adspostion).getNamev();
        File file = new File(getApplicationContext().getCacheDir().toString());
        File list[] = file.listFiles();
        String lll = "";
        for( int i=0; i< list.length; i++)
        {
            if(list[i].getName().contains(Func.v09ArrayList.get(adspostion).getNamev())){
                File files = new File(getApplicationContext().getCacheDir(), list[i].getName());
                lll = files.toString();
            }
        }
        if(lll != ""){
            try{
                videoView.setVideoPath(lll);
                videoView.requestFocus();
                videoView.start();
                strt_timr();
                Func.v09ArrayList.get(adspostion).setWatchedds(1);
                Geturldata.urlw = "http://www."+ Geturldata.wifilink +"/androidpross/watchvideo.php";
                Geturldata.data = "none";
                Geturldata.withpost = 3;
                Geturldata.post = new String[]{"wificode","mycode", "vname"};
                Geturldata.postdata = new String[]{MainActivity.wifiCode,Func.Getval("code",MainActivity.myinfodata),Func.v09ArrayList.get(adspostion).getNamev()};
                try {
                    new Geturldata.SendRequest().execute();
                }
                catch(Exception e){}
            } catch (Exception e) {}

        }
        else{
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        if(timeads == 0){
            this.finish();
        }
    }
    int t = 0;
    public void strt_timr(){
        t = timeads;
        try {
            CountDownTimer waitTimer;
            waitTimer = new CountDownTimer(timeads*1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    t = t - 1;
                    counter.setText(String.valueOf(t));
                    if(t <= Func.v09ArrayList.get(adspostion).getTimewatchds()){
                        close.setVisibility(View.VISIBLE);
                    }
                }
                public void onFinish() {
                    //ads_wifa.this.finish();
                    counter.setText("0");
                    close.setVisibility(View.VISIBLE);
                    if(Func.v09ArrayList.get(adspostion).getUrlclick().contains(Str.attr.c022)){
                        visturl.setVisibility(View.VISIBLE);
                    }

                }
            }.start();


        }
        catch (Exception e){}
    }
    private void playvideo() {
        //mProgressDialog = new ProgressDialog(this);
        //mProgressDialog.setMessage("Downloading");
        //mProgressDialog.setCancelable(true);
        //mProgressDialog.show();

        //MediaController mediaController= new MediaController(this);
        //mediaController.setAnchorView(videoView);
        Uri uri=Uri.parse(urlvideo);
        //videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        //videoView.start();

        //videoView.setMediaController(mediaController);

        try{
            //videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer arg0) {
                //mProgressDialog.dismiss();
                videoView.start();
            }

        });

    }



}
