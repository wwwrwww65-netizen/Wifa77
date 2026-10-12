package com.wi_fa.wifag;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.wi_fa.wifag.maxupmenu.maxupActivities;
import com.wi_fa.wifag.maxupmenu.maxuphome;
import com.wi_fa.wifag.maxupmenu.maxupmsg;
import com.wi_fa.wifag.maxupmenu.maxupsettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifTextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int APP_PERMISSION_REQUEST = 102;
    public static boolean appStatus = false;
    private ImageView loadads;
    Toolbar toolbar;
    TextView title;
    public static String[] wifidata = null;
    public static String[] android_info = null;
    public static String wifiCode,myinfodata,android_id;
    public static String mainlink = "ahmedalimi735135459";
    public static myDbAdapter helperdb;
    public static DbAccessp helperap;
    private Menu menu;
    private InterstitialAd mInterstitialAd;
    MenuItem mm;
    public static int loginstatus = 0;
    public static int menusel = 0;
    public static int secrtnum = 0;
    private Fragment selectedFragment = null;
    CountDownTimer waitTimer;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                selectedFragment = new maxuphome();
                setcc024e(0);
            } else if (itemId == R.id.question) {
                selectedFragment = new maxupActivities();
                setcc024e(1);
                menusel = 0;
            } else if (itemId == R.id.messages) {
                selectedFragment = new maxupmsg();
                setcc024e(2);
                menusel = 0;
            } else if (itemId == R.id.settings) {
                selectedFragment = new maxupsettings();
                setcc024e(3);
                menusel = 0;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlink = Str.attr.c027 + Str.attr.c018;
        helperdb = new myDbAdapter(this);
        helperap = new DbAccessp(this);
        myinfodata = helperdb.getData();
        Str.attr.lang = Integer.parseInt(Getval("music", myinfodata));
        //String asdf = R.string.question;
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, APP_PERMISSION_REQUEST);
            }

            //if (Build.VERSION.SDK_INT >= 23) {
            //    int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            //        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //    }
            //}
            if(MyService.open == 0){
                startService(new Intent(MainActivity.this, MyService.class));
                MyService.open = 1;
            }

        }catch (Exception e){}
        try{
            if(!Geturldata.wifilink.contains("10.11.12.13")) {
                mainlink = Str.attr.c022 + Geturldata.wifilink + Str.attr.c018;
            }
            //Toast.makeText(this, mainlink, Toast.LENGTH_LONG).show();
        }catch (Exception e){}
        try{
            appStatus = true;
            selectedFragment = new maxuphome();
            FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
            transactions.replace(R.id.content, selectedFragment);
            transactions.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        }catch (Exception e){}
        try{
            MobileAds.initialize(this, "ca-app-pub-5996028277858503~9197612641");
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-5996028277858503/7439711791");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } catch (Exception e){}
        //android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            android_id = Build.SERIAL;
        } catch (Exception e) {
            android_id = "";
        }
        if(android_id == null || android_id.isEmpty() || android_id.equals("unknown") || android_id.length() < 8){
            android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        android_info = new String[]{String.valueOf(height),String.valueOf(width),Build.MANUFACTURER,
        Build.MODEL,Build.BOARD,Build.HARDWARE,Build.VERSION.RELEASE,Build.FINGERPRINT};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();//fill the variable false : app is not running
        appStatus = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        appStatus = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        appStatus = true;
    }
    public final void changeTitle(int toolbarId, String titlePage){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(titlePage);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(menusel > 0) {
            if(Str.attr.lang > 1) {
                inflater.inflate(R.menu.menu_vip_en, menu);
            }
            else{
                inflater.inflate(R.menu.menu_vip, menu);
            }
            this.menu = menu;
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_stat_list);
            toolbar.setOverflowIcon(drawable);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.status) {
            // todo: goto back activity from here
            getuserstats();
            return true;
        } else if (itemId == R.id.changepass) {
            // todo: goto back activity from here
            if (Integer.parseInt(wifidata[5]) > 1) {
                if (Func.wifidatainofupdate != "none" && Func.Getintval("changepass", Func.wifidatainofupdate) == 1) {
                    if (Func.wifidatainofupdate.contains("secrtnum")) {
                        secrtnum = Func.Getintval("secrtnum", Func.wifidatainofupdate);
                    }
                    changeuserandpass();
                } else {
                    msgbox(Str.attr.getstr(63));
                }
            } else {
                msgbox(Str.attr.getstr(61));
                //changeuserandpass();
            }
            return true;
        } else if (itemId == R.id.addcrds) {
            // todo: goto back activity from here
            if (Integer.parseInt(wifidata[5]) > 1) {
                if (Integer.parseInt(wifidata[10]) == 0) {
                    addcridtbox();
                } else if (Func.wifidatainofupdate != "none" && Func.Getintval("vipuser", Func.wifidatainofupdate) == 0) {
                    addcridtbox();
                } else {
                    msgbox(Str.attr.getstr(58));
                }
            } else {
                msgbox(Str.attr.getstr(61));
                //addcridtbox();
            }
            return true;
        } else if (itemId == R.id.slfni) {
            if (Integer.parseInt(wifidata[5]) > 1) {
                if (Integer.parseInt(wifidata[10]) == 0 && Func.Getintval("slfni", Func.wifidatainofupdate) == 1) {
                    slfnibox();
                } else if (Func.wifidatainofupdate != "none" && Func.Getintval("vipuser", Func.wifidatainofupdate) == 0 && Func.Getintval("slfni", Func.wifidatainofupdate) == 1) {
                    addcridtbox();
                } else {
                    msgbox(Str.attr.getstr(63));
                }
            } else {
                msgbox(Str.attr.getstr(61));
                //slfnibox();
            }
            return true;
        } else if (itemId == R.id.senduptime) {
            // todo: goto back activity from here
            if (Integer.parseInt(wifidata[5]) > 1) {
                if (Integer.parseInt(wifidata[10]) == 0 && Func.Getintval("sendval", Func.wifidatainofupdate) == 1) {
                    sendcrditbox();
                } else if (Func.wifidatainofupdate != "none" && Func.Getintval("vipuser", Func.wifidatainofupdate) == 0 && Func.Getintval("sendval", Func.wifidatainofupdate) == 1) {
                    addcridtbox();
                } else {
                    msgbox(Str.attr.getstr(63));
                }
            } else {
                msgbox(Str.attr.getstr(61));
                //sendcrditbox();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    public static String Getval(String attr, String str){
        if (!str.contains(attr))
        {
            return "";
        }
        String val = str.substring(str.indexOf(attr));
        val = val.substring(val.indexOf("=") + 1);
        int e = val.indexOf("=");
        if (e > 0)
        {
            val = val.substring(0, e);
        }
        if(val.contains("=")){
            val = "";
        }
        return val;
    }

    public void getuserstats(){
        if(wifidata.length>0){
            //Toast.makeText(this, wifidata[5], Toast.LENGTH_LONG).show();
        }
        if(Integer.parseInt(wifidata[5]) == 0 || Integer.parseInt(wifidata[5]) == 1){
            if(loginstatus == 0) {
                Toast.makeText(this, Str.attr.getstr(57), Toast.LENGTH_LONG).show();
            }
            else if(loginstatus == 1){
                // open status layout and data from wifipage
                statusbox(0);
            }
        }
        else {
            if(loginstatus == 0) {
                // open status layout and data from wifa website
                statusbox(1);
            }
            else if(loginstatus == 1){
                // open status layout and data from wifipage & wifa website
                statusbox(2);
            }
        }

    }
    int ff = 0;
    private void setcc024e(int frg){
        if(Func.wifidatainofupdate != Str.attr.c000){
            if(Func.Getval(Str.attr.c001,Func.wifidatainofupdate) != Str.attr.c000 &&
                    Func.Getintval(Str.attr.c004,Func.wifidatainofupdate) > 0 &&
                    Func.Getval(Str.attr.c001,Func.wifidatainofupdate).contains(Str.attr.c002)){
                int pos = -1;
                for (int ids=0; ids<Func.Getintval(Str.attr.c004,Func.wifidatainofupdate) ; ids++){
                    int done = Func.v09ArrayList.get(ids).getWatchedds();
                    if(done == 0){
                        pos = ids;
                    }
                }
                if(pos >= 0){

                    if((frg == 0 && Func.v09ArrayList.get(pos).getHomeds() == 0) ||
                            (frg == 1 && Func.v09ArrayList.get(pos).getActionsds() == 0) ||
                            (frg == 2 && Func.v09ArrayList.get(pos).getMsgds() == 0) ||
                            (frg == 3 && Func.v09ArrayList.get(pos).getSettingsds() == 0)){
                        //File SDCardRoot = Environment.getExternalStorageDirectory();
                        //File file = new File(SDCardRoot,Func.v09ArrayList.get(pos).getNamev());
                        //File file = new File(getApplicationContext().getCacheDir(), Func.v09ArrayList.get(pos).getNamev());
                        //File file = File.createTempFile(Func.v09ArrayList.get(pos).getNamev(), null, getCacheDir());
                        File file = new File(getApplicationContext().getCacheDir().toString());
                        File list[] = file.listFiles();
                        int adsnum = 0;
                        String lll = "";
                        for( int i=0; i< list.length; i++)
                        {
                            if(list[i].getName().contains("_adds.")){
                                adsnum = adsnum + 1;
                            }
                            if(list[i].getName().contains(Func.v09ArrayList.get(pos).getNamev())){
                                File files = new File(getApplicationContext().getCacheDir(), list[i].getName());
                                lll = files.toString();
                            }
                        }
                        if(adsnum > 4){
                            int adremv = adsnum - 4;
                            for( int i=0; i< list.length; i++)
                            {
                                if(list[i].getName().contains("_adds.")){
                                    if(!list[i].getName().contains(Func.v09ArrayList.get(pos).getNamev()) && adremv > 0){
                                        adremv = adremv - 1;
                                        File filet = new File(getApplicationContext().getCacheDir(), list[i].getName());
                                        filet.delete();
                                    }
                                }
                            }
                        }
                        if(lll != "" && Geturldata.videodon == "done"){
                            Random rn = new Random();
                            int rands = Func.v09ArrayList.get(pos).getRandomds();
                            int rand = rn.nextInt(rands - 1 + 1) + 1;
                            if(rand == 1 && Func.v09ArrayList.get(pos).getWatchedds() == 0){
                                Func.v09ArrayList.get(pos).setWatchedds(1);
                                videoclass.adspostion = pos;
                                startActivity(new Intent(MainActivity.this, videoclass.class));
                            }
                        }
                        else{
                            try{
                                if(Geturldata.filenm == ""){
                                    Geturldata.filenm = "dd";
                                    Geturldata.videodon = "none";
                                    new Geturldata.DownloadTask(getApplicationContext()).execute(Func.v09ArrayList.get(pos).getNamev());
                                }
                            } catch (Exception e){  }
                        }
                    }
                }
            }
            else if(Func.Getval(Str.attr.c001,Func.wifidatainofupdate).contains(Str.attr.c003)){
                if((frg == 0 && Func.Getintval(Str.attr.c005,Func.wifidatainofupdate) == 0) ||
                        (frg == 1 && Func.Getintval(Str.attr.c006,Func.wifidatainofupdate) == 0) ||
                        (frg == 2 && Func.Getintval(Str.attr.c007,Func.wifidatainofupdate) == 0) ||
                        (frg == 3 && Func.Getintval(Str.attr.c008,Func.wifidatainofupdate) == 0)){
                    if (!mInterstitialAd.isLoaded()) {
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
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
                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            } catch (Exception e){}
                        }
                    });
                }
            }
        }


    }
    public void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    public void msgbox(String msg) {
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.msgbox);
            TextView textmsg =(TextView) dialog.findViewById(R.id.textmsg);
            textmsg.setText(msg);
            dialog.show();
            Button butncancel = (Button) dialog.findViewById(R.id.ms_ok);
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }catch (Exception e){}
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(MainActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(MainActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_circle, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }

    // vip boxes
    int i = 0;

    private void statusbox(int lo){
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            i = 0;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if(Str.attr.lang > 1){
                dialog.setContentView(R.layout.card_used_info_en);
            }
            else {
                dialog.setContentView(R.layout.card_used_info);
            }
            final TextView tx1 = (TextView)dialog.findViewById(R.id.tx_1);
            final TextView uptime = (TextView)dialog.findViewById(R.id.tx_uptime);
            final TextView uptimelimit = (TextView)dialog.findViewById(R.id.tx_uptimelimit);
            final TextView download = (TextView)dialog.findViewById(R.id.tx_download);
            final TextView transfer = (TextView)dialog.findViewById(R.id.tx_transfer);
            final TextView val = (TextView)dialog.findViewById(R.id.tx_val);
            final TextView lastseen = (TextView)dialog.findViewById(R.id.tx_lastseen);
            final TextView cardsnum = (TextView)dialog.findViewById(R.id.tx_cardsadded);
            final LinearLayout layuptime = (LinearLayout)dialog.findViewById(R.id.lay_uptime);
            final LinearLayout layuptimelimit = (LinearLayout)dialog.findViewById(R.id.lay_uptimelimit);
            final LinearLayout laydownload = (LinearLayout)dialog.findViewById(R.id.lay_download);
            final LinearLayout laytransfer = (LinearLayout)dialog.findViewById(R.id.lay_transfer);
            final LinearLayout layval = (LinearLayout)dialog.findViewById(R.id.lay_val);
            final LinearLayout laylastseen = (LinearLayout)dialog.findViewById(R.id.lay_lastseen);
            final LinearLayout laycardsnum = (LinearLayout)dialog.findViewById(R.id.lay_cardsnum);
            final LinearLayout loginform = (LinearLayout)dialog.findViewById(R.id.loginform);
            final LinearLayout loadingLayout = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
            loadingLayout.setVisibility(View.GONE);
            loginform.setVisibility(View.GONE);
            final GifTextView loadigIcon =  (GifTextView) dialog.findViewById(R.id.image_loading);
            loadigIcon.setVisibility(View.GONE);
            dialog.show();
            final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            final Button butnsession = (Button) dialog.findViewById(R.id.btn_session);
            butnsession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    WebBrowser.url = "http://" +  mainlink + "/sessionshwo.php?code=" + MainActivity.wifiCode + "&user=" + wifidata[8] + "&lang=" + Str.attr.lang;
                    if(Str.attr.lang > 1){
                        WebBrowser.Nname = "Sessions";
                    }
                    else{
                        WebBrowser.Nname = "الجلسات";
                    }

                    WebBrowser.Nimage = wifidata[3];
                    startActivity(new Intent(MainActivity.this, WebBrowser.class));
                }
            });
            tx1.setText(Str.attr.getstr(15));
            tx1.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.VISIBLE);
            loadigIcon.setVisibility(View.VISIBLE);
            if(lo == 0 && Integer.parseInt(wifidata[6]) == 0){
                layval.setVisibility(View.GONE);
                laylastseen.setVisibility(View.GONE);
                laycardsnum.setVisibility(View.GONE);
                butnsession.setVisibility(View.GONE);
                Geturldata.urlw = "http://" + maxuphome.serveripaddr + "/status.html";
                Geturldata.data2 = "none";
                Geturldata.withpost = 0;
                try {
                    new Geturldata.SendRequest().execute();
                }
                catch(Exception e){};
                try {
                    waitTimer = new CountDownTimer(10000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            if(Geturldata.data2 != "none"){
                                if(i == 0) {
                                    i = 1;
                                    if (Geturldata.data2.contains("=uptimestatus=")) {
                                        tx1.setVisibility(View.GONE);
                                        int uptim = Func.Getintval("uptimestatus",Geturldata.data2);
                                        String upt = Func.gettimeFromsec(uptim);
                                        int uptlft = Func.Getintval("timeleftstatus",Geturldata.data2);
                                        String uptleft = Func.gettimeFromsec(uptlft);
                                        String downld = Func.Getval("downloadstatus",Geturldata.data2);
                                        String trns = Func.Getval("transferstatus",Geturldata.data2);
                                        uptime.setText(upt);
                                        uptimelimit.setText(uptleft);
                                        download.setText(downld);
                                        transfer.setText(trns);
                                        loginform.setVisibility(View.VISIBLE);
                                        loadingLayout.setVisibility(View.GONE);
                                        loadigIcon.setVisibility(View.GONE);
                                    }
                                    else{
                                        tx1.setText(Str.attr.getstr(16));
                                        loadingLayout.setVisibility(View.GONE);
                                        loadigIcon.setVisibility(View.GONE);
                                    }
                                    stoptimer();
                                }
                            }
                        }
                        public void onFinish() {
                            //After 60000 milliseconds (60 sec) finish current
                            //if you would like to execute something when time finishes
                        }
                    }.start();
                }
                catch (Exception e){}
            } else if(lo >= 1){
                Geturldata.urlw = "http://" + mainlink + "/getuserinfo.php?lang=" + Str.attr.lang;
                Geturldata.data = "none";
                Geturldata.withpost = 3;
                Geturldata.post = new String[]{"mycode","wificode","vipuser"};
                Geturldata.postdata = new String[]{Getval("code",myinfodata),wifiCode,Func.loginuser};
                try {
                    new Geturldata.SendRequest().execute();
                }
                catch(Exception e){}
                try {
                    tx1.setText(Str.attr.getstr(15));
                    waitTimer = new CountDownTimer(10000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            if(Geturldata.data != "none"){
                                if(i == 0) {
                                    i = 1;
                                    if (Geturldata.data.contains("=re=re")) {
                                        if(Geturldata.data.contains("errornotfound")){
                                            tx1.setText(Str.attr.getstr(61));
                                            loadingLayout.setVisibility(View.GONE);
                                            loadigIcon.setVisibility(View.GONE);
                                        }
                                        else{
                                            try{
                                                tx1.setVisibility(View.GONE);
                                                String upt = Getval("uptime",Geturldata.data);
                                                String uptleft = Getval("uptlimit",Geturldata.data);
                                                String downld = Getval("download",Geturldata.data);
                                                String trns = Getval("transfer",Geturldata.data);
                                                String expire = Getval("expire",Geturldata.data);
                                                String lastsen = Getval("lastseen",Geturldata.data);
                                                String cardsaddnum = Getval("cardsaddnum",Geturldata.data);
                                                uptime.setText(upt);
                                                if(Func.Getintval("enupt",Geturldata.data) == 1) {
                                                    uptimelimit.setText(uptleft);
                                                } else{ layuptimelimit.setVisibility(View.GONE); }
                                                if(Func.Getintval("endwnld",Geturldata.data) == 1) {
                                                    transfer.setText(trns);
                                                } else{ laytransfer.setVisibility(View.GONE); }
                                                download.setText(downld);
                                                if(Func.Getintval("enval",Geturldata.data) == 1) {
                                                    val.setText(expire);
                                                } else{ layval.setVisibility(View.GONE); }
                                                if(Func.Getintval("enlstsen",Geturldata.data) == 1) {
                                                    lastseen.setText(lastsen);
                                                } else{ laylastseen.setVisibility(View.GONE); }
                                                if(Func.Getintval("ensis",Geturldata.data) == 0) {
                                                    butnsession.setVisibility(View.GONE);
                                                }
                                                if(Integer.parseInt(wifidata[10]) == 0) {
                                                    cardsnum.setText(cardsaddnum);
                                                } else{ laycardsnum.setVisibility(View.GONE); }
                                                loginform.setVisibility(View.VISIBLE);
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                            } catch (Exception e){}
                                        }
                                    }
                                    else{
                                        tx1.setText(Str.attr.getstr(16));
                                        loadingLayout.setVisibility(View.GONE);
                                        loadigIcon.setVisibility(View.GONE);
                                    }
                                    stoptimer();
                                }
                            }
                        }
                        public void onFinish() {
                            //After 60000 milliseconds (60 sec) finish current
                            //if you would like to execute something when time finishes
                        }
                    }.start();
                }
                catch (Exception e){}
            }
        } catch (Exception e) {}
    }
    public void addcridtbox() {
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if(Str.attr.lang > 1){
                dialog.setContentView(R.layout.addcrdit_box_en);
            }
            else {
                dialog.setContentView(R.layout.addcrdit_box);
            }
            final EditText eduser = (EditText) dialog.findViewById(R.id.ed_username);
            final TextView tx1 = (TextView)dialog.findViewById(R.id.tx_1);
            final EditText edpass = (EditText) dialog.findViewById(R.id.ed_password);
            final LinearLayout loadingLayout = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
            loadingLayout.setVisibility(View.GONE);
            final GifTextView loadigIcon =  (GifTextView) dialog.findViewById(R.id.image_loading);
            loadigIcon.setVisibility(View.GONE);
            if(wifidata[7] != "2"){
                edpass.setVisibility(View.GONE);
            }
            dialog.show();
            final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            final Button butnsave = (Button) dialog.findViewById(R.id.btn_ok);
            butnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = Func.remvoespace(eduser.getText().toString());
                    String pass = Func.remvoespace(edpass.getText().toString());
                    i = 0;
                    if(user.length()> 0){
                        //dialog.dismiss();
                        eduser.setVisibility(View.GONE);
                        edpass.setVisibility(View.GONE);
                        butnsave.setEnabled(false);
                        butnsave.setBackgroundColor(Color.GRAY);
                        tx1.setText(Str.attr.getstr(15));
                        loadingLayout.setVisibility(View.VISIBLE);
                        loadigIcon.setVisibility(View.VISIBLE);
                        Geturldata.urlw = "http://" + mainlink + "/useraction.php?lang=" + Str.attr.lang;
                        Geturldata.data = "none";
                        Geturldata.withpost = 6;
                        Geturldata.post = new String[]{"mycode","wificode","vipuser","usercard","passcard","class"};
                        Geturldata.postdata = new String[]{Getval("code",myinfodata),wifiCode,wifidata[8],user,pass,"addrsid"};
                        try {
                            new Geturldata.SendRequest().execute();
                        }
                        catch(Exception e){};
                        try {
                            waitTimer = new CountDownTimer(10000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    if(Geturldata.data != "none"){
                                        if(i == 0) {
                                            i = 1;
                                            if (Geturldata.data.contains("=re=re")) {
                                                tx1.setText(MainActivity.Getval("result",Geturldata.data));
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butncancel.setVisibility(View.VISIBLE);
                                                butnsave.setVisibility(View.GONE);
                                            }
                                            else{
                                                tx1.setText(Str.attr.getstr(16));
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butncancel.setVisibility(View.VISIBLE);
                                                butnsave.setVisibility(View.GONE);
                                            }
                                            stoptimer();
                                        }
                                    }
                                }
                                public void onFinish() {
                                    //After 60000 milliseconds (60 sec) finish current
                                    //if you would like to execute something when time finishes
                                }
                            }.start();
                        }
                        catch (Exception e){}
                        //alpha.addcrdit(user,pass);
                    } else {
                        Toast.makeText(MainActivity.this,  Str.attr.getstr(21), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){}

    }

    public void slfnibox() {
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            i = 0;
            dialog.setContentView(R.layout.slfni_box);
            final TextView tx1 = (TextView)dialog.findViewById(R.id.tx_1);
            final TextView tx2 = (TextView)dialog.findViewById(R.id.tx_2);
            final TextView vtwifiname = (TextView)dialog.findViewById(R.id.vt_wifiname);

            final LinearLayout loadingLayout = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
            loadingLayout.setVisibility(View.GONE);
            final GifTextView loadigIcon =  (GifTextView) dialog.findViewById(R.id.image_loading);
            loadigIcon.setVisibility(View.GONE);
            dialog.show();
            final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
            final Button butnsave = (Button) dialog.findViewById(R.id.btn_ok);
            if(Str.attr.lang > 1){
                butncancel.setText("Exit");
                butnsave.setText("Get balance");
                vtwifiname.setText("Lend me service");
            }
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            butnsave.setEnabled(false);
            if(Func.wifidatainofupdate != "none") {
                int slfnien = Func.Getintval("slfni", Func.wifidatainofupdate);
                int slfnitime = Func.Getintval("slfnitime", Func.wifidatainofupdate);
                int slfnival = Func.Getintval("slfnival", Func.wifidatainofupdate);
                String timehour = "ساعة";
                String valday = " يوم صلاحية ";
                if(Str.attr.lang > 1){
                    timehour = "Hours";
                    valday = " Days as Validity";
                }
                if(slfnitime >= 60 && slfnitime % 60 == 0){
                    slfnitime = slfnitime / 60;
                }
                else{
                    timehour = "دقيقة";
                    if(Str.attr.lang > 1){
                        timehour = "minutes";
                    }
                }
                if(slfnival >= 60 && slfnival % 60 == 0){
                    slfnival = slfnival / 60;
                    valday = " ساعة صلاحية";
                    if(Str.attr.lang > 1){
                        valday = " Hours as Validity";
                    }
                    if(slfnival >= 24 && slfnival % 24 == 0){
                        slfnival = slfnival / 24;
                        valday = " يوم صلاحية";
                        if(Str.attr.lang > 1){
                            valday = " Days as Validity";
                        }
                    }
                }
                else{
                    valday = " دقيقة صلاحية";
                    if(Str.attr.lang > 1){
                        valday = " minutes as Validity";
                    }
                }

                Double slfnidownload = Func.Getdoubleval("slfnidownload", Func.wifidatainofupdate);

                String setslfni = "";
                if (slfnien == 1) {
                    setslfni = slfnitime + timehour + " ، " + slfnidownload + " ميجا ، " + slfnival + valday;
                    tx1.setText("رصيد سلفني");
                    if(Str.attr.lang > 1){
                        setslfni = slfnitime + timehour + " ، " + slfnidownload + " Mb ، " + slfnival + valday;
                        tx1.setText("Lend me service qouta");
                    }
                    butnsave.setEnabled(true);
                } else {
                    tx1.setText("");
                    setslfni = "خدمة سلفني غير مفعلة";
                    if(Str.attr.lang > 1){
                        setslfni = "Lend me service is not available";
                    }
                    butnsave.setVisibility(View.GONE);
                }
                tx2.setText(setslfni);
                tx2.setVisibility(View.VISIBLE);
            }
            else{
                tx1.setText(Str.attr.getstr(59));
            }
            butnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i = 0;
                    butnsave.setVisibility(View.GONE);
                    tx1.setText(Str.attr.getstr(15));
                    tx2.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    loadigIcon.setVisibility(View.VISIBLE);
                    Geturldata.urlw = "http://" + mainlink + "/useraction.php?lang=" + Str.attr.lang;
                    Geturldata.data = "none";
                    Geturldata.withpost = 4;
                    Geturldata.post = new String[]{"mycode","wificode","vipuser","class"};
                    Geturldata.postdata = new String[]{Getval("code",myinfodata),wifiCode,wifidata[8],"lendme"};
                    try {
                        new Geturldata.SendRequest().execute();
                    }
                    catch(Exception e){};
                    try {
                        waitTimer = new CountDownTimer(10000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                if(Geturldata.data != "none"){
                                    if(i == 0) {
                                        i = 1;
                                        if (Geturldata.data.contains("=re=re")) {
                                            tx1.setText(MainActivity.Getval("result",Geturldata.data));
                                            loadingLayout.setVisibility(View.GONE);
                                            loadigIcon.setVisibility(View.GONE);
                                        }
                                        else{
                                            tx1.setText(Str.attr.getstr(16));
                                            loadingLayout.setVisibility(View.GONE);
                                            loadigIcon.setVisibility(View.GONE);
                                        }
                                        stoptimer();
                                    }

                                }
                            }
                            public void onFinish() {
                                //After 60000 milliseconds (60 sec) finish current
                                //if you would like to execute something when time finishes
                            }
                        }.start();
                    }
                    catch (Exception e){}
                }
            });
        } catch (Exception e){}


    }
    private void changeuserandpass(){
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.card_user_info);
            final EditText eduser = (EditText) dialog.findViewById(R.id.ed_username);
            final TextView tx1 = (TextView)dialog.findViewById(R.id.tx_1);
            final TextView txusername = (TextView)dialog.findViewById(R.id.tx_username);
            final TextView txpassword = (TextView)dialog.findViewById(R.id.tx_password);
            final TextView txmycodesec = (TextView)dialog.findViewById(R.id.tx_mycode_sec);
            final TextView vtwifiname = (TextView)dialog.findViewById(R.id.vt_wifiname);
            final TextView txmycodeused = (TextView)dialog.findViewById(R.id.tx_used);
            final TextView txmycode = (TextView)dialog.findViewById(R.id.tx_mycode);
            final EditText edpass = (EditText) dialog.findViewById(R.id.ed_password);
            final LinearLayout loginform = (LinearLayout)dialog.findViewById(R.id.loginform);
            final LinearLayout loadingLayout = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
            final GifTextView loadigIcon =  (GifTextView) dialog.findViewById(R.id.image_loading);
            final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
            final Button butnsave = (Button) dialog.findViewById(R.id.btn_ok);
            loadingLayout.setVisibility(View.GONE);
            loadigIcon.setVisibility(View.GONE);

            txmycode.setText(String.valueOf(secrtnum));
            eduser.setText(wifidata[8]);
            edpass.setText(wifidata[9]);
            if(wifidata[7] != "2"){
                edpass.setVisibility(View.GONE);
                txpassword.setVisibility(View.GONE);
            }
            if(Str.attr.lang > 1){
                tx1.setText(Str.attr.getstr(15));
                txusername.setText("Username");
                txpassword.setText("Password");
                txmycodesec.setText("Secret Number");
                txmycodeused.setText("used for Balance Transfer service");
                butncancel.setText("Exit");
                butnsave.setText("Save");
                vtwifiname.setText("Account settings");
            }
            if(secrtnum <= 0){
                txmycodesec.setText("");
                txmycode.setText("");
            }
            dialog.show();
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            butnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String user = Func.remvoespace(eduser.getText().toString());
                    String ps = Func.remvoespace(edpass.getText().toString());
                    if(Integer.parseInt(wifidata[7]) == 1){ ps = user; }
                    final String pass = ps;
                    i = 0;
                    if(user.length()> 0){
                        //dialog.dismiss();
                        butnsave.setEnabled(false);
                        butnsave.setBackgroundColor(Color.GRAY);
                        tx1.setText(Str.attr.getstr(15));
                        tx1.setVisibility(View.VISIBLE);
                        loginform.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.VISIBLE);
                        loadigIcon.setVisibility(View.VISIBLE);
                        Geturldata.urlw = "http://" + mainlink + "/useraction.php?lang=" + Str.attr.lang;
                        Geturldata.data = "none";
                        Geturldata.withpost = 7;
                        Geturldata.post = new String[]{"mycode","wificode","vipuser","oldpassword","newusername", "newpassword","class"};
                        Geturldata.postdata = new String[]{Getval("code",myinfodata),wifiCode,wifidata[8],wifidata[9],user,pass,"changepassword"};
                        try {
                            new Geturldata.SendRequest().execute();
                        }
                        catch(Exception e){};
                        try {
                            waitTimer = new CountDownTimer(30000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    if(Geturldata.data != "none"){
                                        if(i == 0) {
                                            i = 1;
                                            if (Geturldata.data.contains("=re=re")) {
                                                String res = MainActivity.Getval("result",Geturldata.data);
                                                tx1.setText(res);
                                                if(res.contains("تم") || res.contains("successfully")){
                                                    helperdb.updateuserandpass(user,pass,wifiCode);
                                                    tx1.setText(Str.attr.getstr(60));
                                                }
                                                loadingLayout.setVisibility(View.GONE);
                                                eduser.setVisibility(View.GONE);
                                                edpass.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butncancel.setText("خــــــــروج");
                                                if(Str.attr.lang > 1){
                                                    butncancel.setText("Exit");
                                                }
                                                butnsave.setVisibility(View.GONE);
                                            }
                                            else{
                                                tx1.setText(Str.attr.getstr(16));
                                                eduser.setVisibility(View.GONE);
                                                edpass.setVisibility(View.GONE);
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butncancel.setText("خــــــــروج");
                                                if(Str.attr.lang > 1){
                                                    butncancel.setText("Exit");
                                                }
                                                butnsave.setVisibility(View.GONE);
                                            }
                                            stoptimer();
                                        }
                                    }
                                }
                                public void onFinish() {
                                    //After 60000 milliseconds (60 sec) finish current
                                    //if you would like to execute something when time finishes
                                }
                            }.start();
                        }
                        catch (Exception e){}
                        //alpha.addcrdit(user,pass);
                    } else {
                        Toast.makeText(MainActivity.this,  Str.attr.getstr(21), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e){
            //Toast.makeText(MainActivity.this,  "adfsesdfe", Toast.LENGTH_LONG).show();
        }
    }
    private String resultsenddata = "";
    public void sendcrditbox() {
        try{
            final Context mThis = this;
            final Dialog dialog = new Dialog(mThis);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if(Str.attr.lang > 1){
                dialog.setContentView(R.layout.senduptime_en);
            }
            else {
                dialog.setContentView(R.layout.senduptime);
            }
            final Spinner timetype = (Spinner) dialog.findViewById(R.id.spinner);
            // Spinner Drop down elements
            ArrayList<String> languages = new ArrayList<>();
            if(Str.attr.lang > 1){
                if(Func.Getintval("uptybe", Func.wifidatainofupdate) <= 0) {
                    languages.add("minutes");
                }
                else {
                    languages.add("hours");
                }
            }
            else {
                if(Func.Getintval("uptybe", Func.wifidatainofupdate) <= 0) {
                    languages.add("دقيقة");
                }
                else {
                    languages.add("ساعة");
                }
            }
            CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(this,languages);
            timetype.setAdapter(customSpinnerAdapter);
            final EditText eduptime = (EditText) dialog.findViewById(R.id.ed_uptime);
            final EditText edupload = (EditText) dialog.findViewById(R.id.ed_upload);
            final EditText eddownload = (EditText) dialog.findViewById(R.id.ed_download);
            final EditText edtransfer = (EditText) dialog.findViewById(R.id.ed_transfer);
            final LinearLayout layupload = (LinearLayout) dialog.findViewById(R.id.lay_upload);
            final LinearLayout laydownload = (LinearLayout) dialog.findViewById(R.id.lay_download);
            final LinearLayout laytransfer = (LinearLayout) dialog.findViewById(R.id.lay_transfer);
            final EditText edrecivercode = (EditText) dialog.findViewById(R.id.ed_recivercode);
            final TextView tx1 = (TextView)dialog.findViewById(R.id.tx_1);
            final LinearLayout dataform = (LinearLayout)dialog.findViewById((R.id.dataform));
            final LinearLayout loadingLayout = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
            loadingLayout.setVisibility(View.GONE);
            final GifTextView loadigIcon =  (GifTextView) dialog.findViewById(R.id.image_loading);
            final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
            final Button butnsave = (Button) dialog.findViewById(R.id.btn_ok);
            loadigIcon.setVisibility(View.GONE);
            if(Func.Getdoubleval("uplimit", Func.wifidatainofupdate) <= 0){
                layupload.setVisibility(View.GONE);
            }
            if(Func.Getdoubleval("downlimit", Func.wifidatainofupdate) <= 0){
                laydownload.setVisibility(View.GONE);
            }
            if(Func.Getdoubleval("comblimit", Func.wifidatainofupdate) <= 0){
                laytransfer.setVisibility(View.GONE);
            }
            dialog.show();

            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            butnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String uptime = eduptime.getText().toString();
                    final String download = eddownload.getText().toString();
                    final String upload = edupload.getText().toString();
                    final String transfer = edtransfer.getText().toString();
                    final String recivrcode = edrecivercode.getText().toString();
                    final String uptimet = timetype.getSelectedItem().toString();
                    i = 0;
                    if(uptime.length()> 0 && download.length()> 0 && recivrcode.length()> 0){
                        //dialog.dismiss();
                        butnsave.setEnabled(false);
                        butnsave.setBackgroundColor(Color.GRAY);
                        tx1.setText(Str.attr.getstr(15));
                        tx1.setVisibility(View.VISIBLE);
                        dataform.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.VISIBLE);
                        loadigIcon.setVisibility(View.VISIBLE);
                        Geturldata.urlw = "http://" + mainlink + "/useraction.php?lang=" + Str.attr.lang;
                        Geturldata.data = "none";
                        Geturldata.withpost = 10;
                        Geturldata.post = new String[]{"mycode","wificode","vipuser","class","uptime","uptimetype", "upload", "download", "transfer", "recevercode"};
                        Geturldata.postdata = new String[]{Getval("code",myinfodata),wifiCode,wifidata[8],"senduptanddownload",uptime,uptimet,upload,download,transfer,recivrcode};
                        try {
                            new Geturldata.SendRequest().execute();
                        }
                        catch(Exception e){}
                        try {
                            waitTimer = new CountDownTimer(32000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    if(Geturldata.data != "none"){
                                        if(i == 0) {
                                            resultsenddata = Func.Getval("result",Geturldata.data);
                                            i = 1;
                                            if (Geturldata.data.contains("=re=re")) {
                                                tx1.setText(Func.Getval("result",Geturldata.data));
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butnsave.setVisibility(View.GONE);
                                                if(resultsenddata.contains("بنجاح") || resultsenddata.contains("successfully")){
                                                    if(Integer.parseInt(wifidata[6]) == 0){
                                                        Geturldata.urlw = "http://" + maxuphome.serveripaddr + "/logout.html";
                                                        Geturldata.withpost = 0;
                                                        try {
                                                            new Geturldata.SendRequest().execute();
                                                        }
                                                        catch(Exception e){}
                                                    }
                                                    else{
                                                        Func.reconnected = 9;
                                                        selectedFragment = new maxuphome();
                                                        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                                                        transactions.replace(R.id.content, selectedFragment);
                                                        transactions.commit();
                                                    }
                                                }
                                            }
                                            else{
                                                tx1.setText(Str.attr.getstr(16));
                                                loadingLayout.setVisibility(View.GONE);
                                                loadigIcon.setVisibility(View.GONE);
                                                butnsave.setVisibility(View.GONE);
                                            }
                                        }

                                    }
                                }
                                public void onFinish() {
                                    try {
                                        if(resultsenddata.contains("بنجاح")){
                                            Func.reconnected = 9;
                                            selectedFragment = new maxuphome();
                                            FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                                            transactions.replace(R.id.content, selectedFragment);
                                            transactions.commit();
                                        }
                                    }
                                    catch(Exception e){}
                                }
                            }.start();
                        }
                        catch (Exception e){}
                    } else {
                        Toast.makeText(MainActivity.this,  Str.attr.getstr(21), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e){}

    }

    public void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }



}
