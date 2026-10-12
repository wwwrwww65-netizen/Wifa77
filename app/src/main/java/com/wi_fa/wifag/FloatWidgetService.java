package com.wi_fa.wifag;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

import com.wi_fa.wifag.activities.DisconnectVPN;
import com.wi_fa.wifag.core.ProfileManager;
import com.wi_fa.wifag.core.VpnStatus;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by ahmedalalimi on 12/11/2017.
 */

public class FloatWidgetService extends Service {
    private WindowManager mWindowManager;
    private View mFloatingWidget;
    public static String wificode = "none";
    public static int statopen = 0;
    private myDbAdapter helperdb;
    private EditText eduser,edpass;
    private Switch cookiecheckbox;
    private FrameLayout statuscon;
    private TextView header,wifinametxt;
    private LinearLayout loadingLayout;
    private RelativeLayout cookielay;
    public static int servtype = 0;
    private Button loginbtn,statinfo;
    public static Handler customHandler2 = new Handler();
    private GifTextView loadingimg;
    private String loginuser,loginpass,serveripaddr;
    private int rad,servert,logint,vip;
    private String[] wifidata = null;
    private int loginstat = 1;
    public int lang = 0;
    public String[] arbiclng = {"لا تترك فارغاً","تم قطع الاتصال","لقد إتصلت بشبكة جديدة متاحة ضمن التطبيق هل تريد المتابعة ؟","تسجيل الدخـــــــول","قطع الاتصال","نــعــم"};
    public String[] englishlng = {"Do not leave empty","Disconnected","you connected to a new network available in the app, Do you want to continue?","Login","Disconnect","Yes"};
    View collapsedView,expandedView;
    CountDownTimer waitTimer;
    public FloatWidgetService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        helperdb = new myDbAdapter(this);
        lang = helperdb.getlang();
        if(lang > 1){
            mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget_en, null);
        }
        else {
            mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        eduser = (EditText) mFloatingWidget.findViewById(R.id.ed_loginuser);
        edpass = (EditText) mFloatingWidget.findViewById(R.id.ed_loginpass);
        cookiecheckbox = (Switch)mFloatingWidget.findViewById(R.id.Cookiecheckbox);
        loadingLayout = (LinearLayout)mFloatingWidget.findViewById(R.id.Linearlay_loading);
        cookielay = (RelativeLayout)mFloatingWidget.findViewById(R.id.Cookielay);
        loginbtn = (Button) mFloatingWidget.findViewById(R.id.loginbtn);
        statinfo = (Button) mFloatingWidget.findViewById(R.id.statinfo_btn);
        ImageView wifiimg = (ImageView)mFloatingWidget.findViewById(R.id.wifiimg);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingWidget, params);
        loadingimg = (GifTextView)mFloatingWidget.findViewById(R.id.image_loading);
        statuscon = (FrameLayout)mFloatingWidget.findViewById(R.id.statusconn);
        wifinametxt = (TextView)mFloatingWidget.findViewById(R.id.wifiname_text);
        collapsedView = mFloatingWidget.findViewById(R.id.collapse_view);
        expandedView = mFloatingWidget.findViewById(R.id.expanded_container);
        ImageView closeButtonCollapsed = (ImageView) mFloatingWidget.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        ImageView closeButton = (ImageView) mFloatingWidget.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        cookiecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    helperdb.updatecookielogin(wificode,0);
                } else{
                    helperdb.updatecookielogin(wificode,1);
                }
            }
        });
        ImageView openactivti = (ImageView) mFloatingWidget.findViewById(R.id.open_activity_button);
        openactivti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                stopSelf();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wificode == "new"){
                    Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    stopSelf();
                }
                else{
                    if(loginstat == 1){
                        loginbybutn();
                    }
                    else if(loginstat == 0){
                        logout();
                    }
                }
            }
        });
        eduser.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == event.KEYCODE_ENTER){
                    //do what you want
                    if(edpass.getVisibility() == View.GONE){
                        if(loginstat == 0){
                            logout();
                        }
                        else{
                            loginbybutn();
                        }
                    }
                    else{
                        edpass.setFocusable(true);
                    }
                }
                return false;
            }
        });
        edpass.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == event.KEYCODE_ENTER){
                    //do what you want
                    if(loginstat == 0){
                        logout();
                    }
                    else{
                        loginbybutn();
                    }
                }
                return false;
            }
        });
        mFloatingWidget.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingWidget, params);
                        return true;
                }
                return false;
            }
        });
        if(wificode != "none"){
            long chek = helperdb.iscodefound(wificode);
            if(chek>0){
                try{
                    loadingicon(0);
                    wifidata = helperdb.getnetworkData(wificode);
                    int appstate = Integer.parseInt(wifidata[15]);
                    if(appstate == 0) {
                        serveripaddr = wifidata[16];
                        loginuser = wifidata[8];
                        loginpass = wifidata[9];
                        servert = Integer.parseInt(wifidata[6]);
                        logint = Integer.parseInt(wifidata[7]);
                        rad = Integer.parseInt(wifidata[5]);
                        vip = Integer.parseInt(wifidata[10]);
                        String usern = Func.domainrad(loginuser,loginpass);
                        if(usern.length()==14){
                            edpass.setText("");
                        }
                        else{
                            edpass.setText(wifidata[9]);
                        }
                        eduser.setText(usern);
                        if(logint < 2){
                            edpass.setVisibility(View.GONE);
                        }
                        String wimg = wifidata[3];
                        wifinametxt.setText(wifidata[1].toString());
                        if (wimg != "none") {
                            wifiimg.setImageBitmap(Geturldata.StringToBitMap(wimg));
                            wifiimg.setVisibility(View.VISIBLE);
                        }
                        int cookie = Integer.parseInt(wifidata[13]);
                        if(cookie == 0){

                            cookiecheckbox.setChecked(true);
                        }
                        checkifconnected();
                        //loadingicon(1);
                    }
                    else{
                        stopSelf();
                    }
                } catch (Exception e){}
            }
            else{
                wifiimg.setVisibility(View.GONE);
                wificode = "new";
                statinfo.setVisibility(View.GONE);
                networknofound(getstrlng(2));
                // new wifi
            }
        }
        else{
            stopSelf();
        }
    }
    private boolean isViewCollapsed() {
        return mFloatingWidget == null || mFloatingWidget.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingWidget != null) mWindowManager.removeView(mFloatingWidget);
    }

    private void networknofound(String str){
        try{
            wifinametxt.setText(str);
            statuscon.setVisibility(View.VISIBLE);
            edpass.setVisibility(View.GONE);
            eduser.setVisibility(View.GONE);
            cookielay.setVisibility(View.GONE);
            statinfo.setVisibility(View.GONE);
            loginbtn.setText(getstrlng(5));

        } catch (Exception ex) { }

    }
    public String getstrlng(int num){
        String str = arbiclng[num];
        if(lang > 1){
            str = englishlng[num];
        }
        return  str;
    }
    public void loadingicon(int status){
        if(status == 0){
            loadingLayout.setVisibility(View.VISIBLE);
            statuscon.setVisibility(View.GONE);
            statinfo.setVisibility(View.GONE);
            loadingimg.setVisibility(View.VISIBLE);
        }
        else if(status == 1){
            loadingLayout.setVisibility(View.GONE);
            statuscon.setVisibility(View.VISIBLE);
            statinfo.setVisibility(View.GONE);
            loadingimg.setVisibility(View.GONE);
        }
        else if(status == 2){
            try{
                loginstat = 0;
                loadingLayout.setVisibility(View.GONE);
                statuscon.setVisibility(View.VISIBLE);
                loadingimg.setVisibility(View.GONE);
                statinfo.setVisibility(View.VISIBLE);
                edpass.setVisibility(View.GONE);
                eduser.setVisibility(View.GONE);
                cookielay.setVisibility(View.GONE);
                loginbtn.setText(getstrlng(4));
                loginbtn.setBackgroundColor(Color.argb(255, 255, 64, 129));
            } catch (Exception e){}
        }
    }
    private void checkifconnected(){
        String loginuser = wifidata[8];
        String loginpass = wifidata[9];
        int servtype = Integer.parseInt(wifidata[6]);
        int cookie = Integer.parseInt(wifidata[13]);
        rad = Integer.parseInt(wifidata[5]);
        if(loginuser.length() > 0){
            if(servtype == 0){
                if(cookie == 0){
                    try {
                        Geturldata.urlw = "http://" + serveripaddr + "/login.html";
                        Geturldata.data = "none";
                        Geturldata.withpost = 2;
                        Geturldata.post = new String[]{"username","password"};
                        Geturldata.postdata = new String[]{loginuser,loginpass};
                        new Geturldata.SendRequest().execute();
                    }
                    catch(Exception e){}
                    geturldata(0);
                }
                else{
                    Geturldata.data = "none";
                    Geturldata.urlw = "http://" + serveripaddr + "/alogin.html";
                    Geturldata.response = 0;
                    Geturldata.withpost = 0;
                    try {
                        new Geturldata.SendRequest().execute();
                    }
                    catch(Exception e){}
                    try {
                        waitTimer = new CountDownTimer(15000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                if(Geturldata.data != "none"){
                                    if(Geturldata.data.contains("Exception")){
                                        loadingicon(1);
                                    }
                                    else if(Geturldata.data.contains("false:false:")){
                                        loadingicon(1);
                                    }
                                    else{
                                        if(Geturldata.data.contains("doLogin()")){
                                            loadingicon(1);
                                        }
                                        else{
                                            loadingicon(2);
                                        }
                                    }

                                    stoptimer();
                                }
                            }
                            public void onFinish() {

                            }
                        }.start();
                    }
                    catch (Exception e){}
                }
            }
            else{
                if(cookie == 0){
                    if (ProfileManager.getInstance(FloatWidgetService.this).getProfileByName(wificode) != null) {
                        try {
                            int dd = helperdb.updatevpnstatus("Connecting");
                            VpnProfile profile;
                            profile = ProfileManager.getInstance(FloatWidgetService.this).getProfileByName(wificode);
                            profile.setmServerName(helperdb.getnetworkData(wificode)[16]);
                            startOrStopVPN(profile);
                            customHandler2.postDelayed(updateTimerThread, 0);
                        }
                        catch (Exception e){
                            Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            stopSelf();
                        }
                    }
                    else{
                        loadingicon(1);
                    }
                }
                else{
                    loadingicon(1);
                }
            }
        }
        else{
            loadingicon(1);
        }
    }
    public void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    int i = 0;
    private void geturldata(final int w){
        i = 0;
        try {
            waitTimer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if (!Geturldata.data.contains("none")) {
                        if (i == 0) {
                            i = 1;
                            if(Geturldata.data.contains("Exception")){
                                loadingicon(1);
                            }
                            else if(Geturldata.data.contains("false::false")){
                                loadingicon(1);
                            }
                            else{
                                if(Geturldata.data.contains("wifag-login-page") || Geturldata.data.contains("doLogin()")){
                                    if(w == 1 && Func.Getval("loginerror",Geturldata.data).contains("not found") && rad >= 2){
                                        tryloginbyrad();
                                    }
                                    else{
                                        loadingicon(1);
                                    }
                                }
                                else{
                                    loginstat = 0;
                                    int a = helperdb.updateuserandpass(loginuser,loginpass,wificode);
                                    if(a<=0){
                                    }
                                    else{
                                    }
                                    loadingicon(2);
                                }
                            }
                            stoptimer();
                        }
                    }
                }
                public void onFinish() {
                    if(Geturldata.data.contains("wifag-login-page") || Geturldata.data.contains("doLogin()")){
                    }
                    else{
                        loadingicon(2);
                    }
                }
            }.start();
        }
        catch (Exception e){}
    }
    private void tryloginbyrad(){
        Geturldata.urlw = "http://" + serveripaddr + "/login.html";
        Geturldata.data = "none";
        Geturldata.withpost = 2;
        Geturldata.post = new String[]{"username","password"};

        if(!loginuser.contains("@wifa")) {
            if(rad == 4){
                if(loginuser.length() == 14){
                    loginuser = loginuser.substring(0,9) + "@wifa";
                    loginpass = loginuser.substring(10,13);
                    try {
                        Geturldata.postdata = new String[]{loginuser,loginpass};
                        Geturldata.data = new Geturldata.SendRequest().execute().get();
                    }
                    catch(Exception e){};
                    geturldata(1);
                }
                else{
                    loginuser = loginuser + "@wifa-man";
                    try {
                        Geturldata.postdata = new String[]{loginuser,loginpass};
                        Geturldata.data = new Geturldata.SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    geturldata(1);
                }
            }
            else if(rad == 3){
                if(loginuser.length() == 14){
                    loginuser = loginuser.substring(0,9) + "@wifa";
                    loginpass = loginuser.substring(10,13);
                    try {
                        Geturldata.postdata = new String[]{loginuser,loginpass};
                        Geturldata.data = new Geturldata.SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    geturldata(1);
                }
            }
            else if(rad == 2){
                loginuser = loginuser + "@wifa-man";
                try {
                    Geturldata.postdata = new String[]{loginuser,loginpass};
                    Geturldata.data = new Geturldata.SendRequest().execute().get();
                }
                catch(Exception e){}
                geturldata(1);
            }
        }
        else{
            try {
                loginuser = loginuser.split("@")[0];
                loadingicon(1);
            } catch(Exception e){}
        }


    }

    private void loginbybutn(){
        loadingicon(0);
        i = 0;
        loginuser =  eduser.getText().toString();
        loginpass = edpass.getText().toString();
        if(!loginuser.isEmpty()){
            if(servert == 0){
                if(logint == 1){
                    loginpass = loginuser;
                }
                else if(logint == 0){
                    loginpass = "";
                }
                Geturldata.urlw = "http://" + serveripaddr + "/login.html";
                Geturldata.data = "none";
                Geturldata.withpost = 2;
                Geturldata.post = new String[]{"username","password"};
                Geturldata.postdata = new String[]{loginuser,loginpass};
                try {
                    new Geturldata.SendRequest().execute();
                }
                catch(Exception e){}
                geturldata(1);
            }
            else {
                // pptp login type
                if(logint == 1){
                    loginpass = loginuser;
                }
                else if(logint == 1){
                    loginpass = "";
                }
                if (ProfileManager.getInstance(FloatWidgetService.this).getProfileByName(wificode) != null) {

                    try {
                        int dd = helperdb.updatevpnstatus("Connecting");
                        VpnProfile profile;
                        profile = ProfileManager.getInstance(FloatWidgetService.this).getProfileByName(wificode);
                        profile.setmServerName(helperdb.getnetworkData(wificode)[16]);
                        startOrStopVPN(profile);
                        customHandler2.postDelayed(updateTimerThread, 0);
                    }
                    catch (Exception e){
                        Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        stopSelf();
                    }
                }
                else{
                    loadingicon(1);
                }
            }

            //Toast.makeText(getActivity(), "oooo", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, getstrlng(0), Toast.LENGTH_LONG).show();
            loadingicon(1);
        }


    }

    private void logout(){
        if(servtype == 0){
            Toast.makeText(this, getstrlng(1), Toast.LENGTH_LONG).show();
            Geturldata.urlw = "http://" + serveripaddr + "/logout.html";
            Geturldata.data = "none";
            Geturldata.withpost = 0;
            loginstat = 1;
            MyService.loginstate = 0;
            Func.reconnected = 0;
            try {
                new Geturldata.SendRequest().execute();
            }
            catch(Exception e){}
            if (logint > 1) {
                edpass.setVisibility(View.VISIBLE);
            }
            else{
                edpass.setVisibility(View.GONE);
            }
            eduser.setVisibility(View.VISIBLE);
            cookielay.setVisibility(View.VISIBLE);
            statinfo.setVisibility(View.GONE);
            loginbtn.setText(getstrlng(3));
            loginbtn.setBackgroundColor(Color.argb(255, 1, 184, 34));
        }
        else{
            collapsedView.setVisibility(View.VISIBLE);
            expandedView.setVisibility(View.GONE);
            Intent disconnectVPN = new Intent(FloatWidgetService.this, DisconnectVPN.class);
            disconnectVPN.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(disconnectVPN);
            try {
                customHandler2.postDelayed(updateTimerThread, 2000);
            }
            catch (Exception e){}
        }

    }

    private void startOrStopVPN(VpnProfile profile) {
        try{
            if (VpnStatus.isVPNActive() && profile.getUUIDString().equals(VpnStatus.getLastConnectedVPNProfile())) {
                Intent disconnectVPN = new Intent(FloatWidgetService.this, DisconnectVPN.class);
                disconnectVPN.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(disconnectVPN);
            } else {
                startVPN(profile);
            }
        }catch (Exception e){
            Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            stopSelf();
        }

    }
    private void startVPN(VpnProfile profile) {
        try{
            ProfileManager.getInstance(FloatWidgetService.this).saveProfile(FloatWidgetService.this, profile);
            Intent intent = new Intent(FloatWidgetService.this, LaunchVPN.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(LaunchVPN.EXTRA_KEY, profile.getUUID().toString());
            intent.setAction(Intent.ACTION_MAIN);
            startActivity(intent);
        }catch (Exception e){
            Intent intent = new Intent(FloatWidgetService.this, InFrist.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            stopSelf();
        }

    }

    public Runnable updateTimerThread = new Runnable() {

        public void run() {
            try{
                String st = helperdb.getvpnstate();
                if(st.contains("disconnected")){
                    loadingicon(1);
                    Toast.makeText(FloatWidgetService.this, getstrlng(1), Toast.LENGTH_LONG).show();
                }
                else if(st.contains("No process")){
                    loadingicon(1);
                    Toast.makeText(FloatWidgetService.this, getstrlng(4), Toast.LENGTH_LONG).show();
                }
                else if(st.contains("Connected") || st.contains("Getting client") || st.contains("SUCCESS")){
                    loadingicon(2);
                }
                else if(st.contains("/s")){
                    loadingicon(2);
                }
                else{
                    //contxtstat.setText(st);
                    customHandler2.postDelayed(this, 1000);
                }
            }
            catch(Exception e){}

        }

    };
}
