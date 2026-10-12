package com.wi_fa.wifag;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.wi_fa.wifag.recylcerchat.ChatData;
import com.wi_fa.wifag.recylcerchat.ConversationRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmedalalimi on 13/09/2017.
 */


public class Conversation extends BaseActivity {
    private CountDownTimer waitTimer;
    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private int pos;
    private int fristposnotwatched = 0;
    private Button send;
    private String Nname;
    private String Nimage;
    private String Ncode;
    myDbAdapter helperdb;
    private String mainlink = "www.wifaglocalserver.net/wifagapi";
    public static int finsh = 0;
    private Handler customHandler22 = new Handler();
    private Handler customHandler23 = new Handler();
    private Menu menu;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        helperdb = new myDbAdapter(this);
        Nname = helperdb.getnameimg(id)[0];
        Nimage = helperdb.getnameimg(id)[1];
        Ncode = helperdb.getnameimg(id)[2];
        MyService.dayli = 30000;
        setupToolbarWithUpNav(R.id.toolbar, Nname, R.drawable.ic_action_back);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //long idw = helperdb.updatewtchmsg(Ncode,1);
        //if(idw<=0){
        //} else {}
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(this,setData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);
        pos = mRecyclerView.getAdapter().getItemCount() -1;
        updtwtchmsgtoweb();
        text = (EditText) findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }, 500);
            }
        });
        text.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == event.KEYCODE_ENTER){
                    //do what you want
                    sendmsgbybtn();
                }
                return false;
            }
        });
        send = (Button) findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmsgbybtn();
            }
        });
        try {
            customHandler22.postDelayed(updateTimerThread22, 0);
            customHandler23.postDelayed(updateTimerThread223, 0);
        }
        catch (Exception e){}
    }
    private void sendmsgbybtn(){
        try{
            if (text.getText().length()>0){
                List<ChatData> data = new ArrayList<ChatData>();
                ChatData item = new ChatData();
                String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String timenow = new SimpleDateFormat("HH:mm:ss").format(new Date());
                String mssgid = "0";
                long idd = helperdb.insertmsgdata(Ncode, text.getText().toString(), 0, datenow, 2, 1, 1);
                if (idd <= 0) {
                } else {
                    mssgid = helperdb.getlastmsgid();
                }
                item.setTime(timenow);
                item.setType("2");
                item.setImage("0");
                item.setText(text.getText().toString());
                data.add(item);
                mAdapter.addItem(data);
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                pos = mRecyclerView.getAdapter().getItemCount() -1;
                if(fristposnotwatched == 0){
                    fristposnotwatched = pos -1;
                }
                msgs(text.getText().toString(),pos,mssgid);
                text.setText("");
            }
            else {
                updateView(pos);
            }
        }
        catch (Exception e){}

    }
    private void msgs(String msgg, final int position, final String mssgid){
        final String mmssgg = msgg;
        final String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String wifilink = helperdb.getnetworkData(Ncode)[17];
        Geturldata.urlw = "http://" + mainlink + "/sendmsg.php";
        Geturldata.data = "none";
        Geturldata.withpost = 4;
        Geturldata.post = new String[]{"code","user","msg","msgtype"};
        Geturldata.postdata = new String[]{Ncode,Func.Getval("code",MainActivity.myinfodata),msgg,"0"};
        new Thread(new Runnable() {
            public void run() {
                try {
                    Geturldata.data = new Geturldata.SendRequest().execute().get();
                    if(Geturldata.data != "none"){
                        if(Geturldata.data.contains("=re=re")){
                            if(Geturldata.data.contains("=msg=sent")){
                                if(mssgid != "0") {
                                    int idd = helperdb.updatedonemsg(mssgid);
                                    if (idd <= 0) {
                                    } else {
                                        updateView(position);
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e){}
            }
        }).start();


    }
    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();
        String msgdata = helperdb.getDatamsg(Ncode);
        if(msgdata.length()>0){
            String[] itemss = msgdata.split("=end=");
            String day = "";
            for (int i=0; i<itemss.length -1; i++){
                String dttim = Func.Getval("datem",itemss[i]);
                String dtt = dttim.substring(0,10);
                String[] dtt2 = dttim.split(" ");
                if(!day.contains(dtt)){
                    day = dtt;
                    ChatData item2 = new ChatData();
                    item2.setType("0");
                    item2.setText(dtt);
                    item2.setTime("");
                    item2.setImage(Nimage);
                    data.add(item2);
                }
                ChatData item = new ChatData();
                String type = Func.Getval("sender",itemss[i]);
                item.setType(type);
                item.setText(Func.Getval("msg",itemss[i]));
                String[] ttim = dtt2[1].split(":");
                int h = Integer.parseInt(ttim[0]);
                String time = "";
                if(h>12){
                    h = h - 12;
                    String hh = "";
                    if(h < 10){
                        hh = "0" + String.valueOf(h);
                    }
                    time = "م" + hh + ":" + ttim[1] + ":" +ttim[2];
                    if(Str.attr.lang > 1){
                        time = hh + ":" + ttim[1] + ":" +ttim[2] + "pm";
                    }
                }
                else {
                    time =  "ص" + dtt2[1];
                    if(Str.attr.lang > 1){
                        time =  dtt2[1] + "am";
                    }
                }
                item.setTime(time);
                if(type.contains("2")){
                    int done = Func.Getintval("done",itemss[i]);
                    int watch = Func.Getintval("watch",itemss[i]);
                    if(done==1){
                        item.setImage("0");
                    } else {
                        if(watch == 1){
                            item.setImage("1");
                            if(fristposnotwatched == 0){
                                fristposnotwatched = i;
                            }
                        } else { item.setImage("2"); }
                    }
                }
                else{
                    item.setImage(Nimage);
                }
                data.add(item);
            }
        }
        else{
            ChatData item = new ChatData();
            item.setType("0");
            item.setText("جديد");
            if(Str.attr.lang > 1){
                item.setText("New");
            }
            item.setTime("");
            item.setImage(Nimage);
            data.add(item);
        }

        //String text[] = {"15 September","Hi, Julia! How are you?", "Hi, Joe, looks great! :) ", "I'm fine. Wanna go out somewhere?", "Yes! Coffe maybe?", "Great idea! You can come 9:00 pm? :)))", "Ok!", "Ow my good, this Kit is totally awesome", "Can you provide other kit?", "I don't have much time, :`("};
        //String time[] = {"", "5:30pm", "5:35pm", "5:36pm", "5:40pm", "5:41pm", "5:42pm", "5:40pm", "5:41pm", "5:42pm"};
        //String type[] = {"0", "2", "1", "1", "2", "1", "2", "2", "2", "1"};



        return data;
    }

    private void updateView(int index){
        mAdapter.items.get(index).setImage("1");
        mAdapter.changeItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userphoto, menu);
        this.menu = menu;
        updateMenuTitles();
        return true;
    }
    private void updateMenuTitles() {
        MenuItem mm = menu.findItem(R.id.user_photo);
        mm.setTitle(Nname);
        Bitmap bitmap = Geturldata.StringToBitMap(Nimage);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        mm.setIcon(drawable);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                //Intent intent = new Intent(Conversation.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
                finsh = 1;
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        finsh = 1;
        this.finish();
    }
    public Runnable updateTimerThread22 = new Runnable() {

        public void run() {
            String dtmsgnew = helperdb.getDatamsgby_s(Ncode,"1");
            if(dtmsgnew.length()>0){
                if(dtmsgnew.contains("=id=")){
                    List<ChatData> data = new ArrayList<ChatData>();
                    String[] itemss = dtmsgnew.split("=end=");
                    for (int i=0; i<itemss.length -1; i++){
                        String dttim = Func.Getval("datem",itemss[i]);
                        String[] dtt2 = dttim.split(" ");
                        ChatData item = new ChatData();
                        item.setType("1");
                        item.setText(Func.Getval("msg",itemss[i]));
                        String[] ttim = dtt2[1].split(":");
                        int h = Integer.parseInt(ttim[0]);
                        String time = "";
                        if(h>12){
                            h = h - 12;
                            String hh = "";
                            if(h < 10){
                                hh = "0" + String.valueOf(h);
                            }
                            time = hh + ttim[1] + ttim[2] + "م";
                        }
                        else { time =  "ص" + dtt2[1]; }
                        item.setTime(time);
                        item.setImage(Nimage);
                        data.add(item);
                        mAdapter.addItem(data);
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                        pos = mRecyclerView.getAdapter().getItemCount() -1;
                    }
                    long idw = helperdb.updatewtchmsg(Ncode,1);
                    if(idw<=0){
                    } else {}
                    updtwtchmsgtoweb();
                    updateitmwatch();
                }
            }
            if(finsh == 0){
                customHandler22.postDelayed(this, 2000);
            }

        }

    };


    private void updtwtchmsgtoweb(){
        String wifilink = helperdb.getnetworkData(Ncode)[17];
        Geturldata.urlw = "http://" + mainlink + "/watchmsg.php";
        Geturldata.data = "none";
        Geturldata.withpost = 2;
        Geturldata.post = new String[]{"code", "user"};
        Geturldata.postdata = new String[]{Ncode,Func.Getval("code", MainActivity.myinfodata)};
        try {
            new Geturldata.SendRequest().execute();
        }
        catch(Exception e){}
    }

    private void updateitmwatch(){
        int nn = 0;
        if(fristposnotwatched>0 && fristposnotwatched<pos){
            for (int i=fristposnotwatched-1; i<pos; i++){
                String t = mAdapter.items.get(i).getType();
                if(t == "2"){
                    String s = mAdapter.items.get(i).getImage();
                    if(s == "1"){
                        mAdapter.items.get(i).setImage("2");
                        nn++;
                    }
                }
            }
            if(nn>0){
                mAdapter.changeItem();
            }
        }

    }

    private void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    public Runnable updateTimerThread223 = new Runnable() {
        public void run() {
            if(getdatas == 0){
                checknotfandmsg();
            }
            if(finsh == 0){
                customHandler23.postDelayed(this, 4000);
            }
        }
    };

    int co = 0;
    int getdatas = 0;
    public void checknotfandmsg(){
        try{
            getdatas = 1;
            new Thread(new Runnable() {
                public void run() {
                    String minurl = "http://www.wifaglocalserver.net/wifagapi/msgandnotf2.php";
                    String wifilink = helperdb.getnetworkData(Ncode)[17];
                    if(co == 0){
                        if(MainActivity.wifiCode != null && MainActivity.wifiCode != "" && MainActivity.wifiCode != "10.11.12.13"){
                            minurl = "http://" + mainlink + "/msgandnotf2.php";
                        }
                        Geturldata.s_urlw = minurl;
                        Geturldata.s_data = "none";
                        Geturldata.s_withpost = 6;
                        Geturldata.s_post = new String[]{"mycode", "mac","hostname","ssidap","macap","ipaddress"};
                        Geturldata.s_postdata = new String[]{Func.Getval("code", MainActivity.myinfodata),Func.Getval("mac", MainActivity.myinfodata),Func.Getval("hostname", MainActivity.myinfodata),"","",""};
                        try {
                            new Geturldata.s_SendRequest().execute();
                            co = 1;
                        }
                        catch(Exception e){}
                    }
                    try {
                        if(Geturldata.s_data != "none"){
                            if(Geturldata.s_data.contains("=re=re")){
                                String data = Geturldata.s_data;
                                int msgnum = Func.Getintval("msgnum", data);
                                if(msgnum>0){
                                    String msgdata = data.substring(0, data.indexOf("=donemsg=done"));
                                    String[] n = msgdata.split("line");
                                    for (int i=0; i<n.length-1; i++){
                                        String wificode = Func.Getval("wificode", n[i]);
                                        String msg = Func.Getval("message", n[i]);
                                        int msgtype = Func.Getintval("msgtype", n[i]);
                                        String dattim = Func.Getval("dattim", n[i]);
                                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date date 	= sd.parse(dattim);
                                        dattim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                                        long idd = helperdb.insertmsgdata(wificode, msg, msgtype, dattim, 1, 0, 1);
                                        if (idd <= 0) {
                                        } else {
                                            long idw = helperdb.updatewtchmsg(wificode,2);
                                        }
                                    }
                                }

                            }
                            getdatas = 0;
                            co = 0;
                        }
                        else{
                            getdatas = 0;
                        }
                    }
                    catch (Exception e){
                        getdatas = 0;
                    }
                }
            }).start();
        } catch (Exception e) {}





    }

}
