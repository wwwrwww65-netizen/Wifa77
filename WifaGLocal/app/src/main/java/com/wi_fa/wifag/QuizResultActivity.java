package com.wi_fa.wifag;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by ahmedalalimi on 14/10/2017.
 */

public class QuizResultActivity extends AppCompatActivity {
    public static String result1;
    public static int result2, level , station;
    private TextView rsltstatus1, rsltstatus2, rsltcard, rsltcard2, rsltlevel, carduptime, carddownload, cardval, rsltpass,rsltpass2,cardqouta, ads1, ads2;
    private LinearLayout LinearLayloading, cardinfolayer, laycarduptime, laycarddownload, laycardval,laycardpass;
    private GifTextView loadigIcon = null;
    private Button tryagain, cancelbtn;
    private InterstitialAd mInterstitialAd;
    private MediaPlayer mpwiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Str.attr.lang > 1){
            setContentView(R.layout.activity_quiz_result_en);
        }
        else{
            setContentView(R.layout.activity_quiz_result);
        }
        setTitle(Str.attr.getstr(41));
        MyService.dayli = 5000;
        definedviewslayout();
        loadingicon(0);
        getresultdatafromweb();
        try{
            MobileAds.initialize(this, "ca-app-pub-5996028277858503~9197612641");
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-5996028277858503/7439711791");
        } catch (Exception e){}
        openads();
    }
    private void openads(){
        try {
            CountDownTimer waitTimer2;
            waitTimer2 = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    if(Func.wifidatainofupdate != Str.attr.c000){
                        if(Func.Getval(Str.attr.c001,Func.wifidatainofupdate) != Str.attr.c000 &&
                                Func.Getintval(Str.attr.c004,Func.wifidatainofupdate) > 0 &&
                                Func.Getval(Str.attr.c001,Func.wifidatainofupdate).contains(Str.attr.c002)){
                            int pos = -1;
                            for (int ids=0; ids<Str.attr.C0002 ; ids++){
                                int done = Func.v09ArrayList.get(ids).getWatchedds();
                                if(done == 0){
                                    pos = ids;
                                }
                            }
                            if(pos >= 0){
                                if(Func.v09ArrayList.get(pos).getContestds() == 0){
                                    //File SDCardRoot = Environment.getExternalStorageDirectory();
                                    //File file = new File(SDCardRoot,Func.v09ArrayList.get(pos).getNamev());
                                    //File file = new File(getApplicationContext().getCacheDir(), Func.v09ArrayList.get(pos).getNamev());
                                    File file = new File(getApplicationContext().getCacheDir().toString());
                                    File list[] = file.listFiles();
                                    String lll = "";
                                    for( int i=0; i< list.length; i++)
                                    {
                                        if(list[i].getName().contains(Func.v09ArrayList.get(pos).getNamev())){
                                            File files = new File(getApplicationContext().getCacheDir(), list[i].getName());
                                            lll = files.toString();
                                        }
                                    }
                                    if(lll != "" && Geturldata.videodon == "done"){
                                        Random rn = new Random();
                                        int rands = Func.v09ArrayList.get(pos).getRandomds();
                                        int rand = rn.nextInt(rands - 1 + 1) + 1;
                                        if(rand == 1 && Func.v09ArrayList.get(pos).getWatchedds() == 0){
                                            videoclass.adspostion = pos;
                                            startActivity(new Intent(QuizResultActivity.this, videoclass.class));
                                        }
                                    }
                                    else{
                                        try{
                                            if(Geturldata.filenm == ""){
                                                Geturldata.filenm = "586";
                                                Geturldata.videodon = "none";
                                                new Geturldata.DownloadTask(getApplicationContext()).execute(Func.v09ArrayList.get(pos).getNamev());
                                            }
                                        } catch (Exception e){  }
                                    }

                                }
                            }
                        }
                        else if(Func.Getval(Str.attr.c001,Func.wifidatainofupdate).contains(Str.attr.c003)){
                            if(Func.Getintval(Str.attr.c010,Func.wifidatainofupdate) == 0){
                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                mInterstitialAd.setAdListener(new AdListener() {
                                    public void onAdLoaded() {
                                        // Call displayInterstitial() function
                                        try{
                                            Random rn = new Random();
                                            int rands = Func.Getintval(Str.attr.c009,Func.wifidatainofupdate);
                                            int rand = rn.nextInt(rands - 1 + 1) + 1;
                                            if(rand == 1){
                                                displayInterstitial();
                                            }
                                        } catch (Exception e){}

                                    }
                                });
                            }
                        }
                    }
                }
            }.start();
        }
        catch (Exception e){}
    }
    public void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private void definedviewslayout() {
        cardinfolayer = (LinearLayout) findViewById(R.id.cardinfo_layer);
        laycarduptime = (LinearLayout) findViewById(R.id.lay_card_uptime);
        laycarddownload = (LinearLayout) findViewById(R.id.lay_card_download);
        laycardval = (LinearLayout) findViewById(R.id.lay_card_val);
        laycardpass = (LinearLayout) findViewById(R.id.lay_card_pass);
        LinearLayloading = (LinearLayout) findViewById(R.id.Linearlay_loading);
        LinearLayloading.setVisibility(View.GONE);
        loadigIcon = (GifTextView) findViewById(R.id.image_loading);
        loadigIcon.setVisibility(View.GONE);
        rsltstatus1 = (TextView) findViewById(R.id.rslt_status1);
        rsltstatus2 = (TextView) findViewById(R.id.rslt_status2);
        rsltcard = (TextView) findViewById(R.id.rslt_card);
        rsltcard2 = (TextView) findViewById(R.id.rslt_card2);
        rsltlevel = (TextView) findViewById(R.id.rslt_level);
        carduptime = (TextView) findViewById(R.id.card_uptime);
        carddownload = (TextView) findViewById(R.id.card_download);
        cardval = (TextView) findViewById(R.id.card_val);
        rsltpass = (TextView) findViewById(R.id.rslt_pass);
        rsltpass2 = (TextView) findViewById(R.id.rslt_pass2);
        cardqouta = (TextView) findViewById(R.id.card_qouta);
        ads1 = (TextView) findViewById(R.id.ads1);
        ads2 = (TextView) findViewById(R.id.ads2);
        tryagain = (Button) findViewById(R.id.try_again);
        cancelbtn = (Button) findViewById(R.id.cancel);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startButtonIntent = new Intent(QuizResultActivity.this, Quiz_explane.class);
                startActivity(startButtonIntent);
                QuizResultActivity.this.finish();

            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizResultActivity.this.finish();

            }
        });
        mpwiner = MediaPlayer.create(getBaseContext(), (R.raw.winersound));


    }
    private void loadingicon(int status){
        if(status == 0){
            LinearLayloading.setVisibility(View.VISIBLE);
            loadigIcon.setVisibility(View.VISIBLE);
        }
        else if(status == 1){
            LinearLayloading.setVisibility(View.GONE);
            loadigIcon.setVisibility(View.GONE);
        }
    }
    int ii = 0;
    int cardsfound = 0;
    private void getresultdatafromweb(){
        Geturldata.urlw = "http://"+ MainActivity.mainlink +"/finshcontest.php?lang=" + Str.attr.lang;
        Geturldata.data = "none";
        Geturldata.withpost = 4;
        Geturldata.post = new String[]{"wificode","mycode", "station","result"};
        Geturldata.postdata = new String[]{MainActivity.wifiCode,Func.Getval("code",MainActivity.myinfodata),String.valueOf(station),String.valueOf(result2)};
        try {
            new Geturldata.SendRequest().execute();
        }
        catch(Exception e){}
        rsltstatus1.setText(result1);
        if(result2 == 1){
            rsltstatus2.setText(Str.attr.getstr(39));
            rsltcard.setText(Str.attr.getstr(42));
            rsltlevel.setText(Str.attr.getstr(43) + " : " + Str.attr.getstr(44));
            cardinfolayer.setVisibility(View.GONE);
            rsltcard2.setVisibility(View.GONE);
            loadingicon(1);
        }
        else if(result2 == 0){
            if(Func.Getintval("music",MainActivity.myinfodata) == 0 || Func.Getintval("music",MainActivity.myinfodata) == 2){
                try {
                    mpwiner = MediaPlayer.create(getBaseContext(), (R.raw.winersound));
                    mpwiner.start();
                } catch (Exception e) {}
            }
            if(station == 1){
                rsltstatus2.setText(Str.attr.getstr(49));
            }
            else if(station == 2){
                rsltstatus2.setText(Str.attr.getstr(50));
            }
            else if(station == 3){
                rsltstatus2.setText(Str.attr.getstr(51));
            }
            else if(station == 4){
                rsltstatus2.setText(Str.attr.getstr(52));
            }
            if(level == 1){ rsltlevel.setText(Str.attr.getstr(43) + " : " + Str.attr.getstr(45)); }
            else if(level == 2){ rsltlevel.setText(Str.attr.getstr(43) + " : " + Str.attr.getstr(46)); }
            else if(level == 3){ rsltlevel.setText(Str.attr.getstr(43) + " : " + Str.attr.getstr(47)); }
            else if(level == 4){ rsltlevel.setText(Str.attr.getstr(43) + " : " + Str.attr.getstr(48)); }
            ii = 0;
            try {
                CountDownTimer waitTimer;
                waitTimer = new CountDownTimer(25000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(ii == 0) {
                            if (Geturldata.data != "none") {
                                ii = 1;
                                if(Geturldata.data.contains("=re=re")){
                                    cardsfound = 1;
                                    if(Geturldata.data.contains("=win=")){
                                        String data = Geturldata.data;
                                        String usrnam = Func.Getval("username",data);
                                        String pass = Func.Getval("password",data);
                                        String uptime = Func.Getval("uptime",data);
                                        String download = Func.Getval("download",data);
                                        String val = Func.Getval("val",data);
                                        String aads1 = Func.Getval("ads1",data);
                                        String aads2 = Func.Getval("ads2",data);
                                        if(!aads1.contains("none")){ ads1.setText(aads1); }
                                        if(!aads2.contains("none")){ ads2.setText(aads2); }
                                        rsltcard.setText(usrnam);
                                        rsltpass.setText(pass);
                                        String userpass = usrnam;
                                        if(!pass.contains("none")){ laycardpass.setVisibility(View.VISIBLE); userpass = usrnam + " " + Str.attr.getstr(53) + " : " + pass;}
                                        carduptime.setText(uptime);
                                        carddownload.setText(download);
                                        cardval.setText(val);
                                        loadingicon(1);
                                        try{
                                            myDbAdapter helperdb = new myDbAdapter(QuizResultActivity.this);
                                            String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                            long idd = helperdb.insertmsgdata(MainActivity.wifiCode,   Str.attr.getstr(54) + userpass, 0, datenow, 1, 0, 1);
                                        }catch (Exception e){}
                                    }
                                }
                                else{
                                    int do1 = MainActivity.helperdb.updateoffer(MainActivity.wifiCode,station);
                                    if(do1 <= 0){
                                        rsltcard.setText(Str.attr.getstr(55));
                                    }
                                    else{
                                        rsltcard.setText(Str.attr.getstr(55));
                                    }

                                    rsltlevel.setText(Str.attr.getstr(56));
                                    cardinfolayer.setVisibility(View.GONE);
                                    rsltcard2.setVisibility(View.GONE);
                                    loadingicon(1);
                                    cardsfound = 1;
                                }
                            }
                            else{
                                //loadingicon(1);
                                //rsltstatus1.setText(Geturldata.data);
                            }
                        }
                    }
                    public void onFinish() {
                        if(cardsfound == 0){
                            int do1 = MainActivity.helperdb.updateoffer(MainActivity.wifiCode,station);
                            if(do1 <= 0){
                                rsltcard.setText(Str.attr.getstr(55));
                            }
                            else{
                                rsltcard.setText(Str.attr.getstr(55));
                            }

                            rsltlevel.setText(Str.attr.getstr(56));
                            cardinfolayer.setVisibility(View.GONE);
                            rsltcard2.setVisibility(View.GONE);
                            loadingicon(1);
                        }
                        //After 60000 milliseconds (60 sec) finish current
                        //if you would like to execute something when time finishes
                    }
                }.start();
            }
            catch (Exception e){}
        }


    }
}
