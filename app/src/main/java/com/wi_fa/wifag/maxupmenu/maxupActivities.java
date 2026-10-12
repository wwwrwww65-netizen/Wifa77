package com.wi_fa.wifag.maxupmenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wi_fa.wifag.Func;
import com.wi_fa.wifag.Geturldata;
import com.wi_fa.wifag.MainActivity;
import com.wi_fa.wifag.MyService;
import com.wi_fa.wifag.Quiz_explane;
import com.wi_fa.wifag.R;
import com.wi_fa.wifag.Str;
import com.wi_fa.wifag.myDbAdapter;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by ahmedalalimi on 07/10/2017.
 */

public class maxupActivities extends Fragment {
    View view,vlinewifiname,vlinewin,vlineoffer,vlinecontest;
    myDbAdapter helperdb;
    private LinearLayout LinearLayloading,alllay,bottomgoogleads,Linearlaytxhome;
    private TextView vt_wifiname,vt_wintitle,vt_winoffer,vt_offer,vt_geftcontest,txhome;
    private GifTextView loadigIcon = null;
    private ImageView offerimg,imgcontext,imageads;
    private AdView mAdView;
    CountDownTimer waitTimer;
    public maxupActivities(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maxupmenu_activities, container, false);
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, Str.attr.getstr(1));
        MyService.dayli = 10000;
        definedviewslayout();
        loadingicon(0);
        helperdb = new myDbAdapter(getActivity());
        definenetworkservice();
        imgcontext = (ImageView)view.findViewById(R.id.question_book);
        imgcontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent(getActivity(), Quiz_explane.class);
                startActivity(result);
            }
        });
        vt_geftcontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent(getActivity(), Quiz_explane.class);
                startActivity(result);
            }
        });
        return view;
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
    private void definedviewslayout(){
        LinearLayloading = (LinearLayout)view.findViewById(R.id.Linearlay_loading);
        alllay = (LinearLayout)view.findViewById(R.id.all_lay);
        Linearlaytxhome = (LinearLayout)view.findViewById(R.id.Linearlay_tx_home);
        LinearLayloading.setVisibility(View.GONE);
        loadigIcon =  (GifTextView) view.findViewById(R.id.image_loading);
        imageads = (ImageView) view.findViewById(R.id.image_ads);
        loadigIcon.setVisibility(View.GONE);
        vlinewifiname = (View)view.findViewById(R.id.line_wifiname);
        vlinewin = (View)view.findViewById(R.id.line_win);
        vlineoffer = (View)view.findViewById(R.id.line_offer);
        vlinecontest = (View)view.findViewById(R.id.line_contest);
        vt_wifiname = (TextView)view.findViewById(R.id.vt_wifiname);
        mAdView = (AdView)view.findViewById(R.id.adView);
        vt_wintitle = (TextView)view.findViewById(R.id.vt_winoffer_title);
        vt_winoffer = (TextView)view.findViewById(R.id.vt_winoffer_txt);
        txhome = (TextView)view.findViewById(R.id.tx_home);
        vt_offer = (TextView)view.findViewById(R.id.vt_offer_txt);
        bottomgoogleads = (LinearLayout)view.findViewById(R.id.bottom_googleads);
        vt_geftcontest = (TextView)view.findViewById(R.id.act_gift);
        offerimg = (ImageView) view.findViewById(R.id.offer_img);
        if(Str.attr.lang > 1){
            vt_wintitle.setText("Free Balance Service");
            txhome.setText("Does not support this network");
        }
        //setadsimg();

    }
    int i = 0;
    private void setadsimg(){
        i = 0;
        try {
            waitTimer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(!Func.ads_bottom.contains("none") && Func.Getval(Str.attr.c023,Func.wifidatainofupdate).contains(Str.attr.c002)){
                        if(Func.Getval(Str.attr.c024,Func.wifidatainofupdate).contains(Str.attr.c022)){
                            imageads.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Str.attr.c021 + Func.Getval(Str.attr.c024,Func.wifidatainofupdate)));
                                    startActivity(browserIntent);
                                }
                            });
                        }
                        if(i == 0){
                            i = 1;
                            String urlimg = Str.attr.c017 + Str.attr.c019 + Func.ads_bottom;
                            Glide
                                    .with(getActivity())
                                    .load(urlimg)
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }
                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            imageads.setVisibility(View.VISIBLE);
                                            bottomgoogleads.setVisibility(View.GONE);
                                            stoptimer();
                                            return false;
                                        }
                                    })
                                    .into(imageads);
                        }
                    }
                    else if(Func.Getval(Str.attr.c023,Func.wifidatainofupdate).contains(Str.attr.c003)){

                        try{
                            bottomgoogleads.setVisibility(View.VISIBLE);
                            imageads.setVisibility(View.GONE);
                            AdView adView = new AdView(getActivity());
                            adView.setAdSize(AdSize.BANNER);
                            MobileAds.initialize(getActivity(), "ca-app-pub-5996028277858503~9197612641");
                            adView.setAdUnitId("ca-app-pub-5996028277858503/1319122620");

                            AdRequest adRequest = new AdRequest.Builder().build();
                            mAdView.loadAd(adRequest);


                        } catch (Exception e){}
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
    private void definenetworkservice(){
        String vipds = "1";
        if(MainActivity.wifidata != null){
            if(MainActivity.wifidata[10] == "0"){
                vipds = "0";
            }
            vt_wifiname.setText(MainActivity.wifidata[1]);
            if(Func.wifidatainofupdate != "none"){
                int winen = Func.Getintval("winenb",Func.wifidatainofupdate);
                if(winen == 1) {
                    int wintype = Func.Getintval("wintype", Func.wifidatainofupdate);
                    int wincardsnum = Func.Getintval("wincardsnum", Func.wifidatainofupdate);
                    int windays = Func.Getintval("windays", Func.wifidatainofupdate);
                    int numcrdleft = Func.Getintval("numcrdleft", Func.wifidatainofupdate);
                    int windaysleft = Func.Getintval("windaysleft", Func.wifidatainofupdate);
                    String aa = Str.attr.getstr(22);
                    int bb = 0;
                    if(numcrdleft > 0){
                        wincardsnum = numcrdleft;
                        aa = Str.attr.getstr(23);
                        bb = 1;
                    }
                    if(windaysleft > 0){
                        windays = windaysleft;
                    } else { aa = Str.attr.getstr(22); }
                    String winoffr = "";
                    if(wintype == 0){
                        if(Str.attr.lang > 1){
                            winoffr = aa + wincardsnum + " cards within" + windays + " days and get free credit ";
                        }
                        else{
                            winoffr = aa + "عدد " + wincardsnum + " خلال " + windays + " يوم واحصل على رصيد كرت مجاني ";
                        }
                    }
                    else if(wintype == 1){
                        if(Str.attr.lang > 1){
                            if(bb == 0){
                                winoffr = "Add " + wincardsnum + " credits to your account within " + windays + " days and get free credit ";
                            }
                            else{
                                winoffr = "Continue to add " + wincardsnum + " credits to your account within " + windays + " days and get free credit ";
                            }
                        }
                        else{
                            winoffr = aa + " بسعر " + wincardsnum + " خلال " + windays + " يوم واحصل على رصيد كرت مجاني ";
                        }
                    }
                    if (winoffr != "") {
                        vt_winoffer.setText(winoffr);
                    } else {
                        vt_wintitle.setVisibility(View.GONE);
                        vt_winoffer.setVisibility(View.GONE);
                        vlinewin.setVisibility(View.GONE);
                    }
                }
                else{
                    vt_wintitle.setVisibility(View.GONE);
                    vt_winoffer.setVisibility(View.GONE);
                    vlinewin.setVisibility(View.GONE);
                }
                String giftcontest = Func.Getval("contest", Func.wifidatainofupdate);
                if(giftcontest.contains("enable")){
                    vt_geftcontest.setText(Str.attr.getstr(24));
                }
                else{
                    vt_geftcontest.setText(Str.attr.getstr(25));
                }
            }
            else{
                vt_wintitle.setVisibility(View.GONE);
                vt_winoffer.setVisibility(View.GONE);
                vlinewin.setVisibility(View.GONE);
                vt_geftcontest.setText(Str.attr.getstr(25));
            }
            String offr = MainActivity.wifidata[11];
            int offrt = Integer.parseInt(MainActivity.wifidata[12]);
            if(!offr.contains("none") && offr.length()>2){
                if(!offr.contains("contestwifa")){
                    if(offrt == 0){
                        vt_offer.setText(offr);
                        offerimg.setVisibility(View.GONE);
                    }
                    else if(offrt == 1){
                        vt_offer.setVisibility(View.GONE);
                        Bitmap bitmap = Geturldata.StringToBitMap(offr);
                        offerimg.setImageBitmap(bitmap);
                    }
                }
                else{
                    vt_offer.setVisibility(View.GONE);
                    offerimg.setVisibility(View.GONE);
                    vlineoffer.setVisibility(View.GONE);
                }
            }
            else{
                vt_offer.setVisibility(View.GONE);
                offerimg.setVisibility(View.GONE);
                vlineoffer.setVisibility(View.GONE);
            }
            loadingicon(1);
        }
        else{
            //vt_wifiname.setVisibility(View.GONE);
           // vlinewifiname.setVisibility(View.GONE);
            //txhome.setText(aabb);
            //txhome.setText(networkinfo.networkinf[7] + "  -  " + networkinfo.networkinf[8] + "  - "  + networkinfo.networkinf[9] + " - " + networkinfo.networkinf[10] + "  - " + networkinfo.networkinf[11] + " -  "  + networkinfo.networkinf[6]);
            txhome.setVisibility(View.VISIBLE);
            Linearlaytxhome.setVisibility(View.VISIBLE);
            alllay.setVisibility(View.GONE);
            loadingicon(1);
        }
    }
    public void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
}
