package com.wi_fa.wifag.maxupmenu;

/**
 * Created by ahmedalalimi on 13/09/2017.
 */

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wi_fa.wifag.BuildConfig;
import com.wi_fa.wifag.Func;
import com.wi_fa.wifag.Geturldata;
import com.wi_fa.wifag.LaunchVPN;
import com.wi_fa.wifag.MainActivity;
import com.wi_fa.wifag.MyService;
import com.wi_fa.wifag.R;
import com.wi_fa.wifag.Str;
import com.wi_fa.wifag.VpnProfile;
import com.wi_fa.wifag.WebBrowser;
import com.wi_fa.wifag.activities.DisconnectVPN;
import com.wi_fa.wifag.core.OpenVPNService;
import com.wi_fa.wifag.core.ProfileManager;
import com.wi_fa.wifag.core.VpnStatus;
import com.wi_fa.wifag.networkinfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import pl.droidsonroids.gif.GifTextView;

public class maxuphome extends Fragment {
    private TextView titleName, titleStatus, titlephone,statcntxt;
    private String mymacaddr,myipaddr,myhostname,wifissid,wifimacaddr;
    public static String serveripaddr;
    private String urlw;
    private AdView mAdView;
    private String readpage;
    private TextView contxtstat,txhome;
    private GifTextView loadigIcon1 = null;
    private GifTextView loadigIcon2 = null;
    private ImageView wifiimg,adsimg;
    private LinearLayout loadingLayout1 = null;
    private LinearLayout loadingLayout2 = null;
    private EditText edloginuser,edloginpass;
    private Button btnlogin,btnstatusinfo;
    private int logntype,servtype,cookieuser;
    public static Handler customHandler2 = new Handler();
    private int contatus = 1;
    private int rad;
    private LinearLayout homeall_lay,bottomlayer01,bottomlayer00,bottomgoogleads,bottomlayer22,lineartxhome;
    private RelativeLayout cookielay;
    private FrameLayout statuscon;
    private String wifiCode;
    private Switch cookiecheckbox;
    networkinfo networkcalss;
    CountDownTimer waitTimer,waitTimer2;
    Intent launchintent;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(Str.attr.lang > 1){
            view = inflater.inflate(R.layout.maxupmenu_home_en, container, false);
        }
        else{
            view = inflater.inflate(R.layout.maxupmenu_home, container, false);
        }
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, Str.attr.getstr(0));
        try{
            networkcalss = new networkinfo(getActivity());
            MyService.dayli = 10000;
        } catch (Exception ex) { }
        try{
            difindviewslayout();
            loadingicon1(0);
        } catch (Exception ex) { }
        try{
            wifissid  = networkcalss.getapinfo("ssid");
            wifimacaddr  = networkcalss.getapinfo("mac");
            mymacaddr = networkcalss.getMACAddress("wlan0");
            myipaddr = networkcalss.getdhcpinfo("myip");
            myhostname = networkcalss.getHostName("d");
            serveripaddr = networkcalss.getdhcpinfo("serverip");
            //Toast.makeText(getActivity(), MainActivity.android_info, Toast.LENGTH_LONG).show();
        } catch (Exception ex) { }

        if (networkcalss.iswificonnect(getActivity())) {
            contxtstat.setTextColor(Color.YELLOW);
            contxtstat.setText("....");
            settitleviw();
        }
        else{
            networknofound(Str.attr.getstr(4));
        }
        //setadsimg();
        if(Func.wifidatainofupdate != "none"){
        }
        return view;
    }

    int ff = 0;

    private void difindviewslayout(){
        try{
            loadingLayout1 = (LinearLayout)view.findViewById(R.id.Linearlay_loading1);
            loadingLayout1.setVisibility(View.GONE);
            bottomlayer01 = (LinearLayout)view.findViewById(R.id.bottom_layer01);
            bottomlayer00 = (LinearLayout)view.findViewById(R.id.bottom_layer00);
            bottomlayer22 = (LinearLayout)view.findViewById(R.id.bottom_layer22);
            lineartxhome = (LinearLayout)view.findViewById(R.id.linear_tx_home);
            loadigIcon1 =  (GifTextView) view.findViewById(R.id.image_loading1);
            loadigIcon1.setVisibility(View.GONE);
            statuscon = (FrameLayout)view.findViewById(R.id.statusconn);
            loadingLayout2 = (LinearLayout)view.findViewById(R.id.Linearlay_loading2);
            bottomgoogleads = (LinearLayout)view.findViewById(R.id.bottom_googleads);
            loadigIcon2 =  (GifTextView) view.findViewById(R.id.image_loading2);
            homeall_lay = (LinearLayout)view.findViewById(R.id.all_home);
            titleName = (TextView)view.findViewById(R.id.title_name);
            titleStatus = (TextView)view.findViewById(R.id.title_status);
            titlephone = (TextView)view.findViewById(R.id.title_phone);
            statcntxt = (TextView)view.findViewById(R.id.statcontxt);
            txhome = (TextView)view.findViewById(R.id.tx_home);
            wifiimg =  (ImageView) view.findViewById(R.id.circleView);
            adsimg =  (ImageView) view.findViewById(R.id.image_ads);
            mAdView = (AdView)view.findViewById(R.id.adView);
            edloginpass = (EditText) view.findViewById(R.id.ed_loginpass);
            edloginuser = (EditText) view.findViewById(R.id.ed_loginuser);
            btnlogin = (Button) view.findViewById(R.id.loginbtn);
            btnstatusinfo = (Button) view.findViewById(R.id.statinfo_btn);
            contxtstat = (TextView)view.findViewById(R.id.contxtstat);
            cookielay = (RelativeLayout)view.findViewById(R.id.Cookielay);
            cookiecheckbox = (Switch)view.findViewById(R.id.Cookiecheckbox);
            Button cards = (Button)view.findViewById(R.id.act_cards);
            Button selers = (Button)view.findViewById(R.id.act_sellers);
            Button cards2 = (Button)view.findViewById(R.id.act_cards2);
            Button selers2 = (Button)view.findViewById(R.id.act_sellers2);
            cards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swhobrowser(0);
                }
            });
            selers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swhobrowser(1);
                }
            });
            cards2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swhobrowser(0);
                }
            });
            selers2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swhobrowser(1);
                }
            });
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edloginuser.getWindowToken(), 0);
                    if(contatus == 0){
                        logout();
                    }
                    else{
                        loginbybutn();
                    }
                }
            });
            edloginuser.setOnKeyListener(new View.OnKeyListener(){
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event){
                    if(keyCode == event.KEYCODE_ENTER){
                        //do what you want
                        if(edloginpass.getVisibility() == View.GONE){
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edloginuser.getWindowToken(), 0);
                            if(contatus == 0){
                                logout();
                                return true;
                            }
                            else{
                                loginbybutn();
                                return true;
                            }
                        }
                        else{
                            edloginpass.setFocusable(true);
                        }
                    }
                    return false;
                }
            });
            edloginpass.setOnKeyListener(new View.OnKeyListener(){
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event){
                    if(keyCode == event.KEYCODE_ENTER){
                        //do what you want
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edloginuser.getWindowToken(), 0);
                        if(contatus == 0){
                            logout();
                            return true;
                        }
                        else{
                            loginbybutn();
                            return true;
                        }
                    }
                    return false;
                }
            });
            btnstatusinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getuserstats();
                }
            });
            cookiecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                    // true if the switch is in the On position
                    if(isChecked){
                        MainActivity.helperdb.updatecookielogin(MainActivity.wifiCode,0);
                    } else{
                        MainActivity.helperdb.updatecookielogin(MainActivity.wifiCode,1);
                    }

                }
            });
        }
        catch (Exception e){}

    }
    int iii = 0;
    private void setadsimg(){
        try {
            waitTimer2 = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(!Func.ads_bottom.contains("none") && Func.Getval(Str.attr.c023,Func.wifidatainofupdate).contains(Str.attr.c002)){
                        if(iii == 0){
                            iii = 1;
                            if(Func.Getval(Str.attr.c024,Func.wifidatainofupdate).contains(Str.attr.c022)){
                                adsimg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Str.attr.c021 + Func.Getval(Str.attr.c024,Func.wifidatainofupdate)));
                                        startActivity(browserIntent);
                                    }
                                });
                            }
                            String urlimg = Str.attr.c017 + Str.attr.c019 + Func.ads_bottom;
                            Glide
                                    .with(getActivity())                 // <--this being the current activity I'm trying to show the gif in.
                                    .load(urlimg)
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            return false;
                                        }
                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            bottomlayer00.setVisibility(View.VISIBLE);
                                            bottomlayer01.setVisibility(View.GONE);
                                            bottomgoogleads.setVisibility(View.GONE);
                                            adsimg.setVisibility(View.VISIBLE);
                                            return false;
                                        }
                                    })
                                    .into(adsimg);
                        }
                    }
                    else if(Func.Getval(Str.attr.c023,Func.wifidatainofupdate).contains(Str.attr.c003)){
                        try{
                            bottomlayer00.setVisibility(View.VISIBLE);
                            bottomlayer01.setVisibility(View.GONE);
                            bottomgoogleads.setVisibility(View.VISIBLE);
                            mAdView.setVisibility(View.VISIBLE);
                            adsimg.setVisibility(View.GONE);
                            MobileAds.initialize(getActivity(), "ca-app-pub-5996028277858503~9197612641");
                            AdView adView = new AdView(getActivity());
                            adView.setAdSize(AdSize.BANNER);
                            adView.setAdUnitId("ca-app-pub-5996028277858503/1319122620");
                            AdRequest adRequest = new AdRequest.Builder().build();
                            mAdView.loadAd(adRequest);
                        } catch (Exception e){}


                    }
                }
                public void onFinish() {
                }
            }.start();
        }
        catch (Exception e){}
    }
    private boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager)context. getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service already","running");
                return true;
            }
        }
        Log.i("Service not","running");
        return false;
    }
    private void swhobrowser(int pg){
        try{
            if(pg == 0){
                WebBrowser.url = "http://" +  MainActivity.mainlink + "/cardsprice.php?code=" + MainActivity.wifiCode + "&lang=" + Str.attr.lang;
                WebBrowser.Nname = Str.attr.getstr(5);
                WebBrowser.Nimage = MainActivity.wifidata[3];
                startActivity(new Intent(getActivity(), WebBrowser.class));
            }
            else if(pg == 1){
                WebBrowser.url = "http://" +  MainActivity.mainlink + "/sellers_show.php?code=" + MainActivity.wifiCode + "&lang=" + Str.attr.lang;
                WebBrowser.Nname = Str.attr.getstr(6);
                WebBrowser.Nimage = MainActivity.wifidata[3];
                startActivity(new Intent(getActivity(), WebBrowser.class));
            }
        }
        catch (Exception e){}

    }
    private void settitleviw(){
        try{
            if(networkcalss.networkinf[0] != "none" && !networkcalss.networkinf[0].contains("10.11.12.13")){
                wifiCode = networkcalss.networkinf[0];
                MainActivity.wifiCode = networkcalss.networkinf[0];
                MyService.wificode = networkcalss.networkinf[0];
                long chek = MainActivity.helperdb.iscodefound(wifiCode);
                if(chek>0){
                    MainActivity.wifidata = MainActivity.helperdb.getnetworkData(wifiCode);
                    removeatwifafromuser();
                    int appstate = Integer.parseInt(MainActivity.wifidata[15]);
                    String versionName = BuildConfig.VERSION_NAME;
                    if(Integer.parseInt(MainActivity.wifidata[13]) == 0){
                        cookiecheckbox.setChecked(true);
                    }
                    if(appstate <= 0){
                        serveripaddr = MainActivity.wifidata[16];
                        //Toast.makeText(getActivity(), MainActivity.wifidata[11], Toast.LENGTH_LONG).show();
                        String wimg = MainActivity.helperdb.getnamephonimg(wifiCode)[2];
                        networkcalss.networkinf[1] = MainActivity.helperdb.getnamephonimg(wifiCode)[0];
                        networkcalss.networkinf[2] = MainActivity.helperdb.getnamephonimg(wifiCode)[1];
                        titleName.setText(MainActivity.helperdb.getnamephonimg(wifiCode)[0]);
                        titlephone.setText(MainActivity.helperdb.getnamephonimg(wifiCode)[1]);
                        if(Integer.parseInt(MainActivity.wifidata[7])<=1){
                            edloginpass.setVisibility(View.GONE);
                        }
                        if(wimg != "none"){
                            wifiimg.setImageBitmap(Geturldata.StringToBitMap(wimg));
                            networkcalss.networkinf[3] = wimg;
                            networkcalss.networkinf[4] = Func.remvoespace(MainActivity.helperdb.getnamephonimg(wifiCode)[3]);
                        }
                        else{
                            wifiimg.setVisibility(View.GONE);
                        }
                        loadingicon1(1);
                        MainActivity.menusel = 1;
                        if(MainActivity.wifidata[8].length() > 0){
                            loadingicon2(0);
                            checkifconnected();
                        }
                        if(Func.uponce <= 5){
                            Func.uponce = Func.uponce + 1;
                            //Toast.makeText(getActivity(),  "تحديث " + Func.adfsedse, Toast.LENGTH_LONG).show();
                            setupdtwifi(1);
                        }
                        logntype = Integer.parseInt(MainActivity.wifidata[7]);
                        servtype = Integer.parseInt(MainActivity.wifidata[6]);
                        rad = Integer.parseInt(MainActivity.wifidata[5]);
                        cookieuser = Integer.parseInt(MainActivity.wifidata[13]);
                    }
                    else if(appstate == 1){
                        networknofound(Str.attr.c031);
                    }
                    else if(appstate == 2){
                        networknofound(Str.attr.c032);
                    }
                    else if(appstate == 3){
                        setupdtwifi(1);
                        networknofound(Str.attr.c037);
                    }
                    else if(appstate == 4){
                        Toast.makeText(getActivity(), Str.attr.c034, Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    setnewnetwork();
                }
            }
            else{
                networknofound(Str.attr.getstr(62));
                networkcalss.findnetworkcode();
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), Str.attr.getstr(7), Toast.LENGTH_LONG).show();
        }

    }

    private void settitleviw2(){
        try{
            if(networkcalss.networkinf[0] != "none" && !networkcalss.networkinf[0].contains("10.11.12.13")){
                wifiCode = networkcalss.networkinf[0];
                MainActivity.wifiCode = networkcalss.networkinf[0];
                MyService.wificode = networkcalss.networkinf[0];
                long chek = MainActivity.helperdb.iscodefound(wifiCode);
                if(chek>0){
                    MainActivity.wifidata = MainActivity.helperdb.getnetworkData(wifiCode);
                    removeatwifafromuser();
                    int appstate = Integer.parseInt(MainActivity.wifidata[15]);
                    String versionName = BuildConfig.VERSION_NAME;
                    if(Integer.parseInt(MainActivity.wifidata[13]) == 0){
                        cookiecheckbox.setChecked(true);
                    }
                    if(appstate <= 0){
                        serveripaddr = MainActivity.wifidata[16];
                        //Toast.makeText(getActivity(), MainActivity.wifidata[11], Toast.LENGTH_LONG).show();
                        String wimg = MainActivity.helperdb.getnamephonimg(wifiCode)[2];
                        networkcalss.networkinf[1] = MainActivity.helperdb.getnamephonimg(wifiCode)[0];
                        networkcalss.networkinf[2] = MainActivity.helperdb.getnamephonimg(wifiCode)[1];
                        titleName.setText(MainActivity.helperdb.getnamephonimg(wifiCode)[0]);
                        titlephone.setText(MainActivity.helperdb.getnamephonimg(wifiCode)[1]);
                        if(Integer.parseInt(MainActivity.wifidata[7])<=1){
                            edloginpass.setVisibility(View.GONE);
                        }
                        if(wimg != "none"){
                            wifiimg.setImageBitmap(Geturldata.StringToBitMap(wimg));
                            networkcalss.networkinf[3] = wimg;
                            networkcalss.networkinf[4] = Func.remvoespace(MainActivity.helperdb.getnamephonimg(wifiCode)[3]);
                        }
                        else{
                            wifiimg.setVisibility(View.GONE);
                        }
                        loadingicon1(1);
                        MainActivity.menusel = 1;
                        if(MainActivity.wifidata[8].length() > 0){
                            loadingicon2(0);
                            checkifconnected();
                        }
                        if(Func.uponce <= 5){
                            Func.uponce = Func.uponce + 1;
                            setupdtwifi(1);
                        }
                        logntype = Integer.parseInt(MainActivity.wifidata[7]);
                        servtype = Integer.parseInt(MainActivity.wifidata[6]);
                        rad = Integer.parseInt(MainActivity.wifidata[5]);
                        cookieuser = Integer.parseInt(MainActivity.wifidata[13]);
                    }
                    else if(appstate == 1){
                        networknofound(Str.attr.c031);
                    }
                    else if(appstate == 2){
                        networknofound(Str.attr.c032);
                    }
                    else if(appstate == 3){
                        setupdtwifi(1);
                        networknofound(Str.attr.c037);
                    }
                    else if(appstate == 4){
                        Toast.makeText(getActivity(), Str.attr.c034, Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    setnewnetwork();
                }
            }
            else{
                //loadingicon1(1);
                networknofound(Str.attr.getstr(62));
                networkcalss.findnetworkcode();
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), Str.attr.getstr(7), Toast.LENGTH_LONG).show();
        }

    }

    public void networknofound(String str){
        try{
            loadingicon1(1);
        } catch (Exception e) {}
        try{
            bottomlayer22.setVisibility(View.GONE);
        }catch (Exception e) {}
        try{
            edloginpass.setVisibility(View.GONE);
            lineartxhome.setVisibility(View.VISIBLE);
            txhome.setVisibility(View.VISIBLE);
            txhome.setText(str);
        }catch (Exception e) {}
        try{
            MainActivity.menusel = 0;
            homeall_lay.setVisibility(View.GONE);
        }catch (Exception e) {}
    }
    int ii = 0;
    public void setnewnetwork(){
        if(networkcalss.networkinf[0] != "none" && !networkcalss.networkinf[0].contains("10.11.12.13") && networkcalss.networkinf[1] != "name"){
            if(networkcalss.networkinf[1] != "nonetwork") {
                String lastupdt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                long chek = MainActivity.helperdb.iscodefound(networkcalss.networkinf[0]);
                if(chek==0){
                    long idd = MainActivity.helperdb.insertnewnetwork(networkcalss.networkinf[1], networkcalss.networkinf[2], networkcalss.networkinf[0], networkcalss.networkinf[3], networkcalss.networkinf[4], Integer.parseInt(networkcalss.networkinf[5]), Integer.parseInt(networkcalss.networkinf[6]), Integer.parseInt(networkcalss.networkinf[7]), "", "", 1, networkcalss.networkinf[8], Integer.parseInt(networkcalss.networkinf[9]), 1, lastupdt, Integer.parseInt(networkcalss.networkinf[10]),networkcalss.networkinf[11],Geturldata.wifilink);
                    if (idd <= 0) {
                    } else {
                        String welcomemsg = Func.Getval("welcomemsg", Func.wifidatainofupdate);
                        if(!welcomemsg.contains("none")){
                            String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            long iddd = MainActivity.helperdb.insertmsgdata(networkcalss.networkinf[0], welcomemsg, 0, datenow, 1, 0, 1);
                            if (iddd <= 0) {
                            } else {
                            }
                        }
                        settitleviw2();
                    }
                }
                else{
                    networknofound(Str.attr.getstr(62));
                }
            }
            else{
                networknofound(Str.attr.getstr(62));
            }
        }
        else{
            new Thread(new Runnable() {
                public void run() {
                    networkcalss.getallinfntwrkbycod(Func.Getval("code",MainActivity.helperdb.getData()),"",wifissid,wifimacaddr,myhostname,mymacaddr,myipaddr,"");
                }
            }).start();
            try {
                ii = 0;
                waitTimer = new CountDownTimer(20000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(Func.wifidatainofupdate != "none" && Func.wifidatainofupdate.contains("=wifi=enable")){
                            if(ii == 0){
                                ii = 1;
                                if(networkcalss.networkinf[1] != "nonetwork") {
                                    String lastupdt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                    long chek = MainActivity.helperdb.iscodefound(networkcalss.networkinf[0]);
                                    if(chek==0){
                                        long idd = MainActivity.helperdb.insertnewnetwork(networkcalss.networkinf[1], networkcalss.networkinf[2], networkcalss.networkinf[0], networkcalss.networkinf[3], networkcalss.networkinf[4], Integer.parseInt(networkcalss.networkinf[5]), Integer.parseInt(networkcalss.networkinf[6]), Integer.parseInt(networkcalss.networkinf[7]), "", "", 1, networkcalss.networkinf[8], Integer.parseInt(networkcalss.networkinf[9]), 1, lastupdt, Integer.parseInt(networkcalss.networkinf[10]),networkcalss.networkinf[11],Geturldata.wifilink);
                                        if (idd <= 0) {
                                        } else {
                                            String welcomemsg = Func.Getval("welcomemsg", Func.wifidatainofupdate);
                                            if(!welcomemsg.contains("none")){
                                                String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                                long iddd = MainActivity.helperdb.insertmsgdata(networkcalss.networkinf[0], welcomemsg, 0, datenow, 1, 0, 1);
                                                if (iddd <= 0) {
                                                } else {
                                                }
                                            }
                                            settitleviw2();
                                        }
                                    }
                                    else{
                                        networknofound(Str.attr.getstr(62));
                                    }
                                }
                                else{
                                    networknofound(Str.attr.getstr(62));
                                }
                                stoptimer();
                            }

                        }
                    }
                    public void onFinish() {
                        if(ii == 0){
                            networknofound(Str.attr.c036);
                        }
                        else{
                            if(wifiCode != networkcalss.networkinf[0]){
                                settitleviw2();
                            }
                        }
                    }
                }.start();
            }
            catch (Exception e){}
        }

    }
    private void setupdtwifi(final int vip){
        try{
            if(networkcalss.networkinf[3].length()<100){
                networkcalss.networkinf[4] = "";
            }
        }catch (Exception e){}

        new Thread(new Runnable() {
            public void run() {
                networkcalss.getallinfntwrkbycod(Func.Getval("code",MainActivity.myinfodata),Func.loginuser,wifissid,wifimacaddr,myhostname,mymacaddr,myipaddr,networkcalss.networkinf[4]);
                ii = 0;
                if(Func.wifidatainofupdate != "none" && Func.doneupdtinfo == 1 && Func.wifidatainofupdate.contains("=wifi=enable")){
                    ii = 1;
                    String lastupdt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String[] offr = MainActivity.helperdb.getoffer(MainActivity.wifiCode);
                    int idd = 0;
                    if(Func.Getintval("statapp", Func.wifidatainofupdate) >= 0){
                        if(offr[0].contains("contestwifa")) {
                            idd = MainActivity.helperdb.updateinfowifi(MainActivity.wifiCode, Func.Getval("name", Func.wifidatainofupdate), Func.Getval("phone", Func.wifidatainofupdate), Func.Getintval("rad", Func.wifidatainofupdate), Func.Getval("serverip", Func.wifidatainofupdate), Func.Getintval("servt", Func.wifidatainofupdate), Func.Getintval("logint", Func.wifidatainofupdate), "contestwifa", Integer.parseInt(offr[1]), lastupdt, Func.Getintval("statapp", Func.wifidatainofupdate));
                        }
                        else{
                            idd = MainActivity.helperdb.updateinfowifi(MainActivity.wifiCode, Func.Getval("name", Func.wifidatainofupdate), Func.Getval("phone", Func.wifidatainofupdate), Func.Getintval("rad", Func.wifidatainofupdate), Func.Getval("serverip", Func.wifidatainofupdate), Func.Getintval("servt", Func.wifidatainofupdate), Func.Getintval("logint", Func.wifidatainofupdate), Func.Getval("offer", Func.wifidatainofupdate), Func.Getintval("offertype", Func.wifidatainofupdate), lastupdt, Func.Getintval("statapp", Func.wifidatainofupdate));
                        }
                        if (idd <= 0) {
                        } else {
                            String[] codeap = MainActivity.helperap.getcodebyssid(wifissid,wifimacaddr);
                            if(codeap[0] != "notfound"){
                                MainActivity.helperap.insert(MainActivity.wifiCode,Geturldata.wifilink,wifissid,wifimacaddr);
                            }
                            if(vip == 0){
                                MainActivity.helperdb.updatevipuser(MainActivity.wifiCode,Func.Getintval("vipuser",Func.wifidatainofupdate));
                            }
                            else{
                                if(Func.Getintval("vipuser",Func.wifidatainofupdate) == 0){
                                    MainActivity.helperdb.updatevipuser(MainActivity.wifiCode,Func.Getintval("vipuser",Func.wifidatainofupdate));
                                }
                                updatwifiimg();
                            }
                            MainActivity.wifidata = MainActivity.helperdb.getnetworkData(wifiCode);
                            removeatwifafromuser();
                        }
                    }
                    else{
                        try{
                            setupdtwifi(1);
                        } catch (Exception e){}
                    }

                }
            }
        }).start();
    }
    public void removeatwifafromuser(){
        try{
            if(MainActivity.wifidata[8].contains("@wifa")) {
                if (MainActivity.wifidata[8].contains("@wifa-man")) {
                    MainActivity.wifidata[8] = MainActivity.wifidata[8].replace("@wifa-man", "");
                } else {
                    MainActivity.wifidata[8] = MainActivity.wifidata[8].replace("@wifa", "") + MainActivity.wifidata[9];
                    MainActivity.wifidata[9] = "";
                }
            }
        } catch (Exception e) {}
    }
    private void updatwifiimg(){
        try{
            String imurl = Func.Getval("img",Func.wifidatainofupdate);
            if(!MainActivity.wifidata[4].contains(imurl) || MainActivity.wifidata[3].length() < 200){
                int idd = MainActivity.helperdb.updateimgwifi(MainActivity.wifiCode,networkcalss.networkinf[3],imurl);
                if (idd <= 0) {
                    //Toast.makeText(context, "حدث خطأ", Toast.LENGTH_LONG).show();
                } else {
                }
            }
        } catch (Exception e){}

    }

    private void checkifconnected(){
        //loadigIcon.post(new Starter());
        urlw = "http://" + serveripaddr + "/login.html";
        //urlw = "http://10.0.0.1/login.html";
        Func.loginuser = MainActivity.helperdb.getnetworkData(wifiCode)[8];
        Func.loginpass = MainActivity.helperdb.getnetworkData(wifiCode)[9];
        logntype = Integer.parseInt(MainActivity.helperdb.getnetworkData(wifiCode)[7]);
        servtype = Integer.parseInt(MainActivity.helperdb.getnetworkData(wifiCode)[6]);
        rad = Integer.parseInt(MainActivity.helperdb.getnetworkData(wifiCode)[5]);
        cookieuser = Integer.parseInt(MainActivity.helperdb.getnetworkData(wifiCode)[13]);
        if(MyService.loginstate == 0 && Func.loginuser.length() > 0){
            if (!isMyServiceRunning(OpenVPNService.class, getActivity())) {
                if(servtype == 0){
                    if(Func.loginuser.length() > 0){
                        //Toast.makeText(getActivity(), loginuser, Toast.LENGTH_LONG).show();
                        if(cookieuser == 0){
                            readpage = "none";
                            try {
                                new SendRequest().execute();
                            }
                            catch(Exception e){}
                            geturldata(0);
                        }
                        else{
                            readpage = "none";
                            urlw = "http://" + serveripaddr + "/alogin.html";
                            try {
                                new SendRequest().execute();
                            }
                            catch(Exception e){}
                            try {
                                waitTimer = new CountDownTimer(15000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        if(readpage != "none"){
                                            if(readpage.contains("Exception::Exception")){
                                                contxtstat.setTextColor(Color.RED);
                                                contxtstat.setText(Str.attr.getstr(9));
                                                loadingicon2(1);
                                            }
                                            else if(readpage.contains("false::false")){
                                                contxtstat.setTextColor(Color.RED);
                                                contxtstat.setText(Str.attr.getstr(9));
                                                loadingicon2(1);
                                            }
                                            else{
                                                if(!readpage.contains("doLogin()")){
                                                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                                                    contxtstat.setText(Str.attr.getstr(8));
                                                    MainActivity.loginstatus = 1;
                                                    MyService.loginstate = 1;
                                                    contatus = 0;
                                                    btnstatusinfo.setVisibility(View.VISIBLE);
                                                    edloginpass.setVisibility(View.GONE);
                                                    edloginuser.setVisibility(View.GONE);
                                                    cookielay.setVisibility(View.GONE);
                                                    btnlogin.setText(Str.attr.getstr(10));
                                                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                                                    loadingicon2(1);
                                                    String[] cccode = MainActivity.helperap.getcodebyssid(wifissid,wifimacaddr);
                                                    if(cccode[0].contains("notfound")){
                                                        long ap = MainActivity.helperap.insert(networkcalss.networkinf[0],Geturldata.wifilink,wifissid,wifimacaddr);
                                                    }
                                                }
                                                else{
                                                    openformlogin();
                                                    loadingicon2(1);
                                                }
                                            }
                                            stoptimer();

                                        }
                                    }
                                    public void onFinish() {
                                        openformlogin();
                                        loadingicon2(1);
                                    }
                                }.start();
                            }
                            catch (Exception e){}
                        }
                    }
                    else{
                        openformlogin();
                        loadingicon2(1);
                    }
                }
                else{
                    if(cookieuser == 0){
                        contxtstat.setTextColor(Color.YELLOW);
                        contxtstat.setText(Str.attr.getstr(11));
                        int dd = MainActivity.helperdb.updatevpnstatus("Connecting");
                        loadingicon2(0);
                        if (getPM().getProfileByName(wifiCode) != null) {
                            VpnProfile profile;
                            profile = getPM().getProfileByName(wifiCode);
                            profile.setMwifiName(MainActivity.wifidata[1]);
                            profile.setMwifilogo(MainActivity.wifidata[3]);
                            startOrStopVPN(profile);
                            getstatusvpn();
                            urlw = "http://" + serveripaddr + "/logout.html";
                            try {
                                new SendRequest().execute();
                            }
                            catch(Exception e){}
                        }
                    }
                    else{
                        openformlogin();
                        loadingicon2(1);
                    }
                }
            }
            else{
                if(servtype == 0){
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    contatus = 0;
                    btnstatusinfo.setVisibility(View.VISIBLE);
                    edloginpass.setVisibility(View.GONE);
                    edloginuser.setVisibility(View.GONE);
                    cookielay.setVisibility(View.GONE);
                    btnlogin.setText(Str.attr.getstr(10));
                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                    loadingicon2(1);
                }
                else {
                    String st = MainActivity.helperdb.getvpnstate();
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    if(contatus == 1){
                        loadingicon2(1);
                        contatus = 0;
                        MainActivity.loginstatus = 1;
                        btnstatusinfo.setVisibility(View.VISIBLE);
                        btnstatusinfo.setEnabled(false);
                        edloginpass.setVisibility(View.GONE);
                        edloginuser.setVisibility(View.GONE);
                        cookielay.setVisibility(View.GONE);
                        btnlogin.setText(Str.attr.getstr(10));
                        btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                    }
                    btnstatusinfo.setText(st);
                    if(Func.reconnected == 9){
                        startOrStopVPN(getPM().getProfileByName(wifiCode));
                        Func.reconnected = 0;
                        getstatusvpn();
                    }
                    else{
                        customHandler2.postDelayed(updateTimerThread, 1000);
                    }
                }
            }
        }
        else if(MyService.loginstate == 1 && Func.loginuser.length() > 0){
            if(servtype == 0){
                if(MainActivity.loginstatus == 1){
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    contatus = 0;
                    btnstatusinfo.setVisibility(View.VISIBLE);
                    edloginpass.setVisibility(View.GONE);
                    edloginuser.setVisibility(View.GONE);
                    cookielay.setVisibility(View.GONE);
                    btnlogin.setText(Str.attr.getstr(10));
                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                    loadingicon2(1);
                    if(Func.reconnected == 9){
                        Func.reconnected = 0;
                        try {
                            waitTimer = new CountDownTimer(4000, 1000) {
                                public void onTick(long millisUntilFinished) {

                                }
                                public void onFinish() {
                                    urlw = "http://" + serveripaddr + "/login.html";
                                    try {
                                        new SendRequest().execute();
                                    }
                                    catch(Exception e){}
                                }
                            }.start();
                        }
                        catch (Exception e){}
                    }
                }
                else{
                    MyService.loginstate = 1;
                    openformlogin();
                    loadingicon2(1);
                }
            }
            else {
                String st = MainActivity.helperdb.getvpnstate();
                contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                contxtstat.setText(Str.attr.getstr(8));
                if(contatus == 1){
                    loadingicon2(1);
                    contatus = 0;
                    MainActivity.loginstatus = 1;
                    btnstatusinfo.setVisibility(View.VISIBLE);
                    btnstatusinfo.setEnabled(false);
                    edloginpass.setVisibility(View.GONE);
                    edloginuser.setVisibility(View.GONE);
                    cookielay.setVisibility(View.GONE);
                    btnlogin.setText(Str.attr.getstr(10));
                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                }
                btnstatusinfo.setText(st);
                if(Func.reconnected == 9){
                    startOrStopVPN(getPM().getProfileByName(wifiCode));
                    Func.reconnected = 0;
                    getstatusvpn();
                }
                else{
                    customHandler2.postDelayed(updateTimerThread, 1000);
                }
            }
        }
        else if(MyService.loginstate == 2 && Func.loginuser.length() > 0){
            getstatusvpn();
        }
        else{
            openformlogin();
            loadingicon2(1);
        }
    }
    int i = 0;
    private void geturldatawithouttimer(int ww){
        try {
            if(readpage.contains("Exception::Exception")){
                contxtstat.setTextColor(Color.RED);
                contxtstat.setText(Str.attr.getstr(7));
                loadingicon2(1);
            }
            else if(readpage.contains("false::false")){
                contxtstat.setTextColor(Color.RED);
                contxtstat.setText(Str.attr.getstr(7));
                loadingicon2(1);
            }
            else{
                if(readpage.contains("wifag-login-page") || readpage.contains("doLogin()")){
                    //Toast.makeText(getActivity(), Func.Getval("loginerror",readpage), Toast.LENGTH_LONG).show();
                    contxtstat.setTextColor(Color.RED);
                    contxtstat.setText(Func.errorarabic(Func.Getval("loginerror",readpage)));
                    loadingicon2(1);
                }
                else{
                    int a = MainActivity.helperdb.updateuserandpass(Func.loginuser,Func.loginpass,wifiCode);
                    if(a<=0){
                    }
                    else{
                    }
                    if(ww >= 1){
                        Toast.makeText(getActivity(), Str.attr.getstr(13), Toast.LENGTH_LONG).show();
                        if(rad > 1){
                            if(Func.loginuser.contains("@wifa")) {
                                if (Func.loginuser.contains("@wifa-man")) {
                                    Func.loginuser = Func.loginuser.replace("@wifa-man", "");
                                } else {
                                    Func.loginuser = Func.loginuser.replace("@wifa", "") + Func.loginpass;
                                    Func.loginpass = "";
                                }
                            }
                            setupdtwifi(0);
                        }
                    }
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    MainActivity.loginstatus = 1;
                    MyService.loginstate = 1;
                    contatus = 0;
                    btnstatusinfo.setVisibility(View.VISIBLE);
                    edloginpass.setVisibility(View.GONE);
                    edloginuser.setVisibility(View.GONE);
                    cookielay.setVisibility(View.GONE);
                    btnlogin.setText(Str.attr.getstr(10));
                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                    loadingicon2(1);
                }
            }
        }
        catch (Exception e){}
    }

    private void geturldata(final int w){
        i = 0;
        try {
            waitTimer = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if (readpage != "none") {
                        if (i == 0) {
                            i = 1;
                            geturldatawithouttimer(w);
                            if(readpage.contains("Exception::Exception")){
                                contxtstat.setTextColor(Color.RED);
                                contxtstat.setText(Str.attr.getstr(7));
                                loadingicon2(1);
                            }
                            else if(readpage.contains("false::false")){
                                contxtstat.setTextColor(Color.RED);
                                contxtstat.setText(Str.attr.getstr(7));
                                loadingicon2(1);
                            }
                            else{
                                if(readpage.contains("wifag-login-page") || readpage.contains("doLogin()")){
                                    Toast.makeText(getActivity(), Str.attr.getstr(19), Toast.LENGTH_LONG).show();
                                    if(w == 1 && Func.Getval("loginerror",readpage).contains("not found") && rad >= 2){
                                        tryloginbyrad();
                                    }
                                    else{
                                        contxtstat.setTextColor(Color.RED);
                                        contxtstat.setText(Func.errorarabic(Func.Getval("loginerror",readpage)));
                                        loadingicon2(1);
                                    }
                                }
                                else{
                                    int a = MainActivity.helperdb.updateuserandpass(Func.loginuser,Func.loginpass,wifiCode);
                                    if(a<=0){
                                        //Toast.makeText(getActivity(), "لم يتم الحفظ", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        //Toast.makeText(getActivity(), "تم الحفظ", Toast.LENGTH_LONG).show();
                                    }
                                    if(w >= 1){
                                        Toast.makeText(getActivity(), Str.attr.getstr(13), Toast.LENGTH_LONG).show();
                                        if(rad > 1){
                                            if(Func.loginuser.contains("@wifa")) {
                                                if (Func.loginuser.contains("@wifa-man")) {
                                                    Func.loginuser = Func.loginuser.replace("@wifa-man", "");
                                                } else {
                                                    Func.loginuser = Func.loginuser.replace("@wifa", "") + Func.loginpass;
                                                    Func.loginpass = "";
                                                }
                                            }
                                            setupdtwifi(0);
                                        }
                                    }
                                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                                    contxtstat.setText(Str.attr.getstr(8));
                                    MainActivity.loginstatus = 1;
                                    MyService.loginstate = 1;
                                    contatus = 0;
                                    btnstatusinfo.setVisibility(View.VISIBLE);
                                    edloginpass.setVisibility(View.GONE);
                                    edloginuser.setVisibility(View.GONE);
                                    cookielay.setVisibility(View.GONE);
                                    btnlogin.setText(Str.attr.getstr(10));
                                    btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                                    loadingicon2(1);
                                    String[] cccode = MainActivity.helperap.getcodebyssid(wifissid,wifimacaddr);
                                    if(cccode[0].contains("notfound")){
                                        long ap = MainActivity.helperap.insert(networkcalss.networkinf[0],Geturldata.wifilink,wifissid,wifimacaddr);
                                    }
                                }
                            }
                            stoptimer();
                        }
                    }
                }

                public void onFinish() {
                    //After 60000 milliseconds (60 sec) finish current
                    //if you would like to execute something when time finishes
                    //Toast.makeText(getActivity(), "كمل التيمر", Toast.LENGTH_LONG).show();
                    //loadingicon2(1);
                }
            }.start();
        }
        catch (Exception e){}
    }
    private void tryloginbyrad(){
        urlw = "http://" + serveripaddr + "/login.html";
        //urlw = "http://10.0.0.1/login.html";
        if(!Func.loginuser.contains("@wifa")) {
            if(rad == 4){
                if(Func.loginuser.length() == 14){
                    Func.loginuser = Func.loginuser.substring(0,9) + "@wifa";
                    Func.loginpass = Func.loginuser.substring(10,13);
                    readpage = "none";
                    try {
                        readpage = new SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    geturldatawithouttimer(2);
                }
                else{
                    Func.loginuser = Func.loginuser + "@wifa-man";
                    readpage = "none";
                    try {
                        readpage = new SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    geturldatawithouttimer(2);
                }
            }
            else if(rad == 3){
                if(Func.loginuser.length() == 14){
                    Func.loginuser = Func.loginuser.substring(0,9) + "@wifa";
                    Func.loginpass = Func.loginuser.substring(10,13);
                    readpage = "none";
                    try {
                        readpage = new SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    geturldatawithouttimer(2);
                }
            }
            else if(rad == 2){
                Func.loginuser = Func.loginuser + "@wifa-man";
                readpage = "none";
                try {
                    readpage = new SendRequest().execute().get();
                }
                catch(Exception e){}
                geturldatawithouttimer(2);
            }
        }
        else{
            try {
                contxtstat.setTextColor(Color.RED);
                contxtstat.setText(Str.attr.getstr(9));
                Func.loginuser = Func.loginuser.split("@")[0];
                loadingicon2(1);
                openformlogin();
            } catch(Exception e){}
        }


    }

    private void loginbybutn(){
        contxtstat.setTextColor(Color.YELLOW);
        contxtstat.setText("....");
        loadingicon2(0);
        i = 0;
        urlw = "http://" + serveripaddr + "/login.html";

        //urlw = "http://10.0.0.1/login.html";
        //removefrmviews();
        if(servtype == 0){
            Func.loginuser =  Func.remvoespace(edloginuser.getText().toString());
            Func.loginpass = Func.remvoespace(edloginpass.getText().toString());
            if(!Func.loginuser.isEmpty()){
                if(logntype == 1){
                    Func.loginpass = Func.remvoespace(edloginuser.getText().toString());
                }
                else if(logntype == 0){
                    Func.loginpass = "";
                }
                readpage = "none";
                try {
                    new SendRequest().execute();
                }
                catch(Exception e){}
                geturldata(1);
                //Toast.makeText(getActivity(), "oooo", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), Str.attr.getstr(21), Toast.LENGTH_LONG).show();
                contxtstat.setTextColor(Color.RED);
                contxtstat.setText(Str.attr.getstr(9));
                loadingicon2(1);
            }
        }
        else {
            Func.loginuser =  Func.remvoespace(edloginuser.getText().toString());
            Func.loginpass = Func.remvoespace(edloginpass.getText().toString());
            if(!Func.loginuser.isEmpty()){
                int dd = MainActivity.helperdb.updatevpnstatus("Connecting");
                if(logntype == 1){
                    Func.loginpass = Func.remvoespace(edloginuser.getText().toString());
                }
                else if(logntype == 0){
                    Func.loginpass = "";
                }
                String namee = wifiCode;
                urlw = "http://" + serveripaddr + "/logout.html";
                try {
                    new SendRequest().execute();
                }
                catch(Exception e){}
                if (getPM().getProfileByName(namee) == null) {
                    VpnProfile profile;
                    profile = new VpnProfile(namee);
                    profile.setmServerName(serveripaddr);
                    profile.setmUsername(Func.loginuser);
                    profile.setmPassword(Func.loginpass);
                    profile.setMwifiName(MainActivity.wifidata[1]);
                    profile.setMwifilogo(MainActivity.wifidata[3]);
                    if(Func.wifidatainofupdate.contains(Str.attr.c029)){
                        profile.setmCipher(Func.Getval(Str.attr.c029,Func.wifidatainofupdate));
                    }
                    if(Func.wifidatainofupdate.contains(Str.attr.c030)){
                        profile.setmmAuth(Func.Getval(Str.attr.c030,Func.wifidatainofupdate));
                    }
                    if(Func.wifidatainofupdate.contains("=myca=") && !Func.Getval("myca", Func.wifidatainofupdate).contains("none")){
                        String myca = "[[NAME]]cert_export_myca.crt[[INLINE]]-----BEGIN CERTIFICATE-----\n" +
                                Func.Getvalca("myca", Func.wifidatainofupdate) + "\n" +
                                "-----END CERTIFICATE-----\n";
                        profile.setmCaFilename(myca);
                    }
                    addProfile(profile);
                    startOrStopVPN(getPM().getProfileByName(namee));
                    getstatusvpn();
                } else {
                    VpnProfile profile;
                    profile = getPM().getProfileByName(namee);
                    profile.setmServerName(serveripaddr);
                    profile.setmUsername(Func.loginuser);
                    profile.setmPassword(Func.loginpass);
                    profile.setMwifiName(MainActivity.wifidata[1]);
                    profile.setMwifilogo(MainActivity.wifidata[3]);
                    if(Func.wifidatainofupdate.contains(Str.attr.c029)){
                        profile.setmCipher(Func.Getval(Str.attr.c029,Func.wifidatainofupdate));
                    }
                    if(Func.wifidatainofupdate.contains(Str.attr.c030)){
                        profile.setmmAuth(Func.Getval(Str.attr.c030,Func.wifidatainofupdate));
                    }
                    if(Func.wifidatainofupdate.contains("=myca=") && !Func.Getval("myca", Func.wifidatainofupdate).contains("none")){
                        String myca = "[[NAME]]cert_export_myca.crt[[INLINE]]-----BEGIN CERTIFICATE-----\n" +
                                Func.Getvalca("myca", Func.wifidatainofupdate) + "\n" +
                                "-----END CERTIFICATE-----\n";
                        profile.setmCaFilename(myca);
                    }

                    startOrStopVPN(profile);
                    getstatusvpn();
                }
                //onAddOrDuplicateProfile(null);
            }
            else{
                contxtstat.setText(Str.attr.getstr(9));
                Toast.makeText(getActivity(), Str.attr.getstr(21), Toast.LENGTH_LONG).show();
                contxtstat.setTextColor(Color.RED);
                //if(getPM().getLastConnectedProfile(getActivity()).getName().contains(wifiCode)){
                    //contxtstat.setText(String.valueOf(counterrr));
                //}

                loadingicon2(1);

                //if (getPM().getProfileByName(wifiCode).getUUIDString().equals(VpnStatus.getLastConnectedVPNProfile())) {
                    //contxtstat.setTextColor(Color.GREEN);
                    //contxtstat.setText(mLastStatusMessage);
                //}
            }

        }

    }

    private void logout(){
        MainActivity.helperdb.updatecookielogin(MainActivity.wifiCode,1);
        if(servtype == 0){
            contxtstat.setTextColor(Color.RED);
            contxtstat.setText(Str.attr.getstr(14));
            urlw = "http://" + serveripaddr + "/logout.html";
            //urlw = "http://10.0.0.1/logout.html";
            try {
                new SendRequest().execute();
            }
            catch(Exception e){}
            contatus = 1;
            MyService.loginstate = 0;
            MainActivity.loginstatus = 0;
            Func.reconnected = 0;
            btnstatusinfo.setVisibility(View.GONE);
            if (logntype > 1) {
                edloginpass.setVisibility(View.VISIBLE);
                edloginpass.setText(Func.loginpass);
            }
            else{
                edloginpass.setVisibility(View.GONE);
            }
            edloginuser.setVisibility(View.VISIBLE);
            if(Func.loginuser.contains("@wifa")) {
                if (Func.loginuser.contains("@wifa-man")) {
                    Func.loginuser = Func.loginuser.replace("@wifa-man", "");
                } else {
                    Func.loginuser = Func.loginuser.replace("@wifa", "") + Func.loginpass;
                    Func.loginpass = "";
                }
            }
            edloginuser.setText(Func.loginuser);
            cookielay.setVisibility(View.VISIBLE);
            btnlogin.setText(Str.attr.getstr(12));
            btnlogin.setBackgroundColor(Color.argb(255, 1, 184, 34));
        }
        else{
            Intent disconnectVPN = new Intent(getActivity(), DisconnectVPN.class);
            startActivity(disconnectVPN);

        }

    }

    private void openformlogin(){
        //frm.removeAllViews();
        //removefrmviews();
        //loadigIcon.post(new Starter());
        contxtstat.setTextColor(Color.RED);
        contxtstat.setText(Str.attr.getstr(9));

        try {
            if (Func.loginuser.contains("@wifa")) {
                if (Func.loginuser.contains("@wifa-man")) {
                    Func.loginuser = Func.loginuser.replace("@wifa-man", "");
                } else {
                    Func.loginuser = Func.loginuser.replace("@wifa", "") + Func.loginpass;
                    Func.loginpass = "";
                }
            }
        } catch (Exception ex) { }
        try {
            btnstatusinfo.setVisibility(View.GONE);
            edloginuser.setText(Func.loginuser);
            edloginpass.setText(Func.loginpass);
            if (logntype < 2) {
                edloginpass.setVisibility(View.GONE);
            }
        } catch (Exception ex) { }

    }
    public void loadingicon1(int status){
        if(status == 0){
            //homeall_lay.setEnabled(false);
            //homeall_lay.setVisibility(View.GONE);
            loadingLayout1.setVisibility(View.VISIBLE);
            loadigIcon1.setVisibility(View.VISIBLE);
        }
        else if(status == 1){
            //homeall_lay.setEnabled(true);
            //homeall_lay.setVisibility(View.VISIBLE);
            loadingLayout1.setVisibility(View.GONE);
            loadigIcon1.setVisibility(View.GONE);
        }
    }
    public void loadingicon2(int status){
        if(status == 0){
            loadingLayout2.setVisibility(View.VISIBLE);
            loadigIcon2.setVisibility(View.VISIBLE);
            //statuscon.setEnabled(false);
            statuscon.setVisibility(View.GONE);
        }
        else if(status == 1){
            loadingLayout2.setVisibility(View.GONE);
            loadigIcon2.setVisibility(View.GONE);
            //statuscon.setEnabled(true);
            statuscon.setVisibility(View.VISIBLE);
        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(urlw);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", Func.loginuser);
                postDataParams.put("password", Func.loginpass);

                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        //MainActivity.str[i] = line.toString();
                        //MainActivity.listt.add(line);
                        //break;
                        readpage =line.toString();
                    }

                    in.close();
                    //MainActivity.str[5] = sb.toString();
                    //MainActivity.listt.add(sb.toString());
                    return sb.toString();

                }
                else {
                    return new String("false::false"+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception::Exception");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), result,
            //       Toast.LENGTH_LONG).show();
            //strtxt.setText(result);
            readpage =result;
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public void getuserstats(){
        if(Integer.parseInt(MainActivity.wifidata[5]) < 2){
            statusbox(0);
        }
        else {
            statusbox(2);
        }
    }
    private void statusbox(int lo){
        i = 0;
        final Dialog dialog = new Dialog(getActivity());
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
        final LinearLayout loadingLayoutg = (LinearLayout)dialog.findViewById(R.id.Linearlay_loading);
        loadingLayoutg.setVisibility(View.GONE);
        loginform.setVisibility(View.GONE);
        final GifTextView loadigIcong =  (GifTextView) dialog.findViewById(R.id.image_loading);
        loadigIcong.setVisibility(View.GONE);
        dialog.show();
        final Button butncancel = (Button) dialog.findViewById(R.id.btn_cancel);
        butncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final Button butnsession = (Button) dialog.findViewById(R.id.btn_session);
        tx1.setText(Str.attr.getstr(15));
        tx1.setVisibility(View.VISIBLE);
        loadingLayoutg.setVisibility(View.VISIBLE);
        loadigIcong.setVisibility(View.VISIBLE);
        if(lo >= 0){
            layval.setVisibility(View.GONE);
            laylastseen.setVisibility(View.GONE);
            laycardsnum.setVisibility(View.GONE);
            butnsession.setVisibility(View.GONE);
            Geturldata.urlw = "http://" + serveripaddr + "/status.html";
            //Geturldata.urlw = "http://10.0.0.1/status.html";
            Geturldata.data2 = "none";
            Geturldata.withpost = 0;
            try {
                new Geturldata.SendRequest().execute();
            }
            catch(Exception e){}
            try {
                waitTimer2 = new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(Geturldata.data2 != "none"){
                            if(i == 0) {
                                i = 1;
                                if (Geturldata.data2.contains("=uptimestatus=")) {
                                    tx1.setVisibility(View.GONE);
                                    try{
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
                                        loadingLayoutg.setVisibility(View.GONE);
                                        loadigIcong.setVisibility(View.GONE);
                                    }catch (Exception e) {}
                                }
                                else{
                                    tx1.setText(Str.attr.getstr(16));
                                    loadingLayoutg.setVisibility(View.GONE);
                                    loadigIcong.setVisibility(View.GONE);
                                }
                                stoptimer2();
                            }
                        }
                    }
                    public void onFinish() {
                        if(i == 0) {
                            tx1.setText(Str.attr.getstr(16));
                            loadingLayoutg.setVisibility(View.GONE);
                            loadigIcong.setVisibility(View.GONE);
                        }
                        //After 60000 milliseconds (60 sec) finish current
                        //if you would like to execute something when time finishes
                    }
                }.start();
            }
            catch (Exception e){}
        } else if(lo >= 1){
            Geturldata.urlw = "http://" + MainActivity.mainlink + "/getuserinfo.php?lang=" + Str.attr.lang;
            Geturldata.data = "none";
            Geturldata.withpost = 3;
            Geturldata.post = new String[]{"mycode","wificode","vipuser"};
            Geturldata.postdata = new String[]{Func.Getval("code",MainActivity.myinfodata),wifiCode,Func.loginuser};
            try {
                new Geturldata.SendRequest().execute();
            }
            catch(Exception e){}
            try {
                tx1.setText(Str.attr.getstr(15));
                waitTimer = new CountDownTimer(26000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(Geturldata.data != "none"){
                            if(i == 0) {
                                i = 1;
                                if (Geturldata.data.contains("=re=re")) {
                                    tx1.setVisibility(View.GONE);
                                    String upt = Func.Getval("uptime",Geturldata.data);
                                    String uptleft = Func.Getval("uptlimit",Geturldata.data);
                                    String downld = Func.Getval("download",Geturldata.data);
                                    String trns = Func.Getval("transfer",Geturldata.data);
                                    String expire = Func.Getval("expire",Geturldata.data);
                                    String lastsen = Func.Getval("lastseen",Geturldata.data);
                                    String cardsaddnum = Func.Getval("cardsaddnum",Geturldata.data);
                                    uptime.setText(upt);
                                    if(Func.Getintval("enupt",Geturldata.data) == 0) {
                                        uptimelimit.setText(uptleft);
                                    } else{ layuptimelimit.setVisibility(View.GONE); }
                                    if(Func.Getintval("endwnld",Geturldata.data) == 0) {
                                        transfer.setText(trns);
                                    } else{ laytransfer.setVisibility(View.GONE); }
                                    download.setText(downld);
                                    if(Func.Getintval("enval",Geturldata.data) == 0) {
                                        val.setText(expire);
                                    } else{ layval.setVisibility(View.GONE); }
                                    if(Func.Getintval("enlstsen",Geturldata.data) == 0) {
                                        lastseen.setText(lastsen);
                                    } else{ laylastseen.setVisibility(View.GONE); }
                                    if(Func.Getintval("ensis",Geturldata.data) == 1) {
                                        butnsession.setVisibility(View.GONE);
                                    }
                                    if(Integer.parseInt(MainActivity.wifidata[10]) == 0) {
                                        cardsnum.setText(cardsaddnum);
                                    } else{ laycardsnum.setVisibility(View.GONE); }
                                    loginform.setVisibility(View.VISIBLE);
                                    loadingLayoutg.setVisibility(View.GONE);
                                    loadigIcong.setVisibility(View.GONE);
                                }
                                else{
                                    tx1.setText(Str.attr.getstr(16));
                                    loadingLayoutg.setVisibility(View.GONE);
                                    loadigIcong.setVisibility(View.GONE);
                                }
                                stoptimer();
                            }
                        }
                    }
                    public void onFinish() {
                        if(i == 0) {
                            tx1.setText(Str.attr.getstr(16));
                            loadingLayoutg.setVisibility(View.GONE);
                            loadigIcong.setVisibility(View.GONE);
                        }
                        //After 60000 milliseconds (60 sec) finish current
                        //if you would like to execute something when time finishes
                    }
                }.start();
            }
            catch (Exception e){}
        }
    }

    public void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    public void stoptimer2(){
        if(waitTimer2 != null) {
            waitTimer2.cancel();
            waitTimer2 = null;
        }
    }
    int bbt = 0;

    public Runnable updateTimerThread = new Runnable() {

        public void run() {
            try{
                String st = MainActivity.helperdb.getvpnstate();
                if(st.contains("Authenticating")){
                    contxtstat.setTextColor(Color.argb(255, 255, 162, 0));
                    contxtstat.setText(Str.attr.getstr(17));
                    if(MainActivity.loginstatus == 1) {
                        loadingicon2(1);
                    }
                    MainActivity.loginstatus = 2;
                    MyService.loginstate = 2;
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("Getting client")){
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    if(contatus == 1){
                        loadingicon2(1);
                        contatus = 0;
                        MainActivity.loginstatus = 1;
                        MyService.loginstate = 1;
                        btnstatusinfo.setVisibility(View.VISIBLE);
                        btnstatusinfo.setEnabled(false);
                        edloginpass.setVisibility(View.GONE);
                        edloginuser.setVisibility(View.GONE);
                        cookielay.setVisibility(View.GONE);
                        btnlogin.setText(Str.attr.getstr(10));
                        int a = MainActivity.helperdb.updateuserandpass(Func.loginuser,Func.loginpass,wifiCode);
                        btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                        setupdtwifi(0);
                    }
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("Connecting")){
                    contxtstat.setTextColor(Color.argb(255, 255, 162, 0));
                    contxtstat.setText(Str.attr.getstr(11));
                    if(MainActivity.loginstatus == 1) {
                        loadingicon2(1);
                    }
                    MainActivity.loginstatus = 2;
                    MyService.loginstate = 2;
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("Waiting")){
                    contxtstat.setTextColor(Color.argb(255, 255, 162, 0));
                    contxtstat.setText(Str.attr.getstr(18));
                    if(MainActivity.loginstatus == 1) {
                        loadingicon2(1);
                    }
                    MainActivity.loginstatus = 2;
                    MyService.loginstate = 2;
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("disconnected")){
                    contxtstat.setTextColor(Color.RED);
                    contxtstat.setText(Str.attr.getstr(14));
                    contatus = 1;
                    MainActivity.loginstatus = 0;
                    MyService.loginstate = 0;
                    btnstatusinfo.setVisibility(View.GONE);
                    if (logntype > 1) {
                        edloginpass.setVisibility(View.VISIBLE);
                    }
                    else{
                        edloginpass.setVisibility(View.GONE);
                        edloginpass.setText(Func.loginpass);
                    }
                    edloginuser.setVisibility(View.VISIBLE);
                    edloginuser.setText(Func.loginuser);
                    cookielay.setVisibility(View.VISIBLE);
                    btnlogin.setText(Str.attr.getstr(12));
                    btnlogin.setBackgroundColor(Color.argb(255, 1, 184, 34));
                }
                else  if(st.contains("reconnecting")){
                    contxtstat.setTextColor(Color.argb(255, 255, 162, 0));
                    contxtstat.setText(Str.attr.getstr(18));
                    if(MainActivity.loginstatus == 1) {
                        loadingicon2(1);
                    }
                    contatus = 1;
                    MyService.loginstate = 2;
                    MainActivity.loginstatus = 2;
                    loadingicon2(0);
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("No process")){
                    contxtstat.setTextColor(Color.RED);
                    contxtstat.setText(Str.attr.getstr(20));
                    contatus = 1;
                    MyService.loginstate = 0;
                    MainActivity.loginstatus = 0;
                    btnstatusinfo.setVisibility(View.GONE);
                    if (logntype > 1) {
                        edloginpass.setVisibility(View.VISIBLE);
                    }
                    else{
                        edloginpass.setVisibility(View.GONE);
                    }
                    edloginuser.setVisibility(View.VISIBLE);
                    cookielay.setVisibility(View.VISIBLE);
                    btnlogin.setText(Str.attr.getstr(12));
                    btnlogin.setBackgroundColor(Color.argb(255, 1, 184, 34));
                    loadingicon2(1);
                }
                else if(st.contains("Connected") || st.contains("SUCCESS")){
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    if(contatus == 1){
                        loadingicon2(1);
                        contatus = 0;
                        MainActivity.loginstatus = 1;
                        MyService.loginstate = 1;
                        btnstatusinfo.setVisibility(View.VISIBLE);
                        btnstatusinfo.setEnabled(false);
                        edloginpass.setVisibility(View.GONE);
                        edloginuser.setVisibility(View.GONE);
                        cookielay.setVisibility(View.GONE);
                        btnlogin.setText(Str.attr.getstr(10));
                        int a = MainActivity.helperdb.updateuserandpass(Func.loginuser,Func.loginpass,wifiCode);
                        btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                        setupdtwifi(0);
                        String[] cccode = MainActivity.helperap.getcodebyssid(wifissid,wifimacaddr);
                        if(cccode[0].contains("notfound")){
                            long ap = MainActivity.helperap.insert(wifiCode,Geturldata.wifilink,wifissid,wifimacaddr);
                        }
                    }
                    customHandler2.postDelayed(this, 1000);
                }
                else if(st.contains("/s")){
                    contxtstat.setTextColor(Color.argb(255, 0, 227, 21));
                    contxtstat.setText(Str.attr.getstr(8));
                    if(contatus == 1){
                        loadingicon2(1);
                        contatus = 0;
                        MyService.loginstate = 1;
                        MainActivity.loginstatus = 1;
                        btnstatusinfo.setVisibility(View.VISIBLE);
                        btnstatusinfo.setEnabled(false);
                        edloginpass.setVisibility(View.GONE);
                        edloginuser.setVisibility(View.GONE);
                        cookielay.setVisibility(View.GONE);
                        btnlogin.setText(Str.attr.getstr(10));
                        int a = MainActivity.helperdb.updateuserandpass(Func.loginuser,Func.loginpass,wifiCode);
                        btnlogin.setBackgroundColor(Color.argb(255, 255, 64, 129));
                        setupdtwifi(0);
                        String[] cccode = MainActivity.helperap.getcodebyssid(wifissid,wifimacaddr);
                        if(cccode[0].contains("notfound")){
                            long ap = MainActivity.helperap.insert(wifiCode,Geturldata.wifilink,wifissid,wifimacaddr);
                        }
                    }
                    btnstatusinfo.setText(st);
                    customHandler2.postDelayed(this, 1000);
                }
                else{
                    //contxtstat.setText(st);
                    customHandler2.postDelayed(this, 1000);
                }
            }
            catch(Exception e){}

        }

    };
    private void getstatusvpn(){
        contxtstat.setTextColor(Color.argb(255, 255, 162, 0));
        try {
            customHandler2.postDelayed(updateTimerThread, 0);
        }
        catch (Exception e){}
    }
    private void startOrStopVPN(VpnProfile profile) {
        if (VpnStatus.isVPNActive() && profile.getUUIDString().equals(VpnStatus.getLastConnectedVPNProfile())) {
            Intent disconnectVPN = new Intent(getActivity(), DisconnectVPN.class);
            startActivity(disconnectVPN);
        } else {
            startVPN(profile);
        }
    }


    private void addProfile(VpnProfile profile) {
        try{
            getPM().addProfile(profile);
        }
        catch (Exception e){}
        try{
            getPM().saveProfileList(getActivity());
        }
        catch (Exception e){}
        try{
            getPM().saveProfile(getActivity(), profile);
        }
        catch (Exception e){}
    }

    private ProfileManager getPM() {
        return ProfileManager.getInstance(getActivity());
    }



    private void startVPN(VpnProfile profile) {
        try{
            getPM().saveProfile(getActivity(), profile);
            launchintent = new Intent(getActivity(), LaunchVPN.class);
            launchintent.putExtra(LaunchVPN.EXTRA_KEY, profile.getUUID().toString());
            launchintent.setAction(Intent.ACTION_MAIN);
            startActivity(launchintent);
        } catch (Exception e) {}
    }



}
