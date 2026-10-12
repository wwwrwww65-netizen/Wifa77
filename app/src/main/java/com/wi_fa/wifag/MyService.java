package com.wi_fa.wifag;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;

import com.wi_fa.wifag.activities.DisconnectVPN;
import com.wi_fa.wifag.core.ProfileManager;
import com.wi_fa.wifag.core.VpnStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ahmedalalimi on 29/10/2017.
 */

public class MyService extends Service {
    public static int s = 0;
    public static int i = 0;
    public static int dayli = 7000;
    public static int floutstate = 0;
    public static Handler customHandler = new Handler();
    private String notificationTitle,notificationText,notfwificode,notfurl;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private Bitmap icon;
    public static int open = 0;
    public static int loginstate = 0;
    private myDbAdapter helperdb;
    private DbAccessp helperap;
    public static int servstate = 0;
    private String mydbdata;
    public CountDownTimer waitTimer;
    private int currentNotificationID = 0;
    public static int wifi_interface = 0;
    public static int formsgandnotf = 0;
    public static int timeconwifi = 0;
    public static String wificode,wifilink,android_id;
    public String myipaddr,wifissid,wifimacaddr;
    public int wincrd = 0;
    public int continutimer = 0;
    networkinfo networkcalss;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        helperdb = new myDbAdapter(this);
        helperap = new DbAccessp(this);
        mydbdata = helperdb.getData();

        try{
            networkcalss = new networkinfo(this);
            wifissid  = networkcalss.getapinfo("ssid");
            wifimacaddr  = networkcalss.getapinfo("mac");
            myipaddr = networkcalss.getdhcpinfo("myip");
            android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }catch (Exception e){}
        //Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_wifa);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        s++;
        // For time consuming an long tasks you can launch a new thread here...
        try{
            networkcalss = new networkinfo(this);
            wifissid  = networkcalss.getapinfo("ssid");
            wifimacaddr  = networkcalss.getapinfo("mac");
            myipaddr = networkcalss.getdhcpinfo("myip");
        }catch (Exception e){}
        open = 1;
        if(wifilink == null){
            wifi_interface = 2;
            //Geturldata.ipforhostname = "10.11.12.13";
            wificode = "54jug02d6dg7v8wnme7fs7vk2w9biopm";
            wifilink = Geturldata.wifilink;
            Geturldata.hostname = "54jug02d6dg7v8wnme7fs7vk2w9biopm";
            try {
                //new Geturldata.NetworkSniffTask(this).execute();
            }
            catch(Exception e){}
        }
        //Toast.makeText(this, " Service Started " + String.valueOf(startId), Toast.LENGTH_LONG).show();
        try {
            customHandler.postDelayed(updateTimerThread, 0);
        }
        catch (Exception e){}
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }
    int co = 0;
    public Runnable updateTimerThread = new Runnable() {

        public void run() {
            try{
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    if(wifi_interface == 1 && floutstate == 0){
                        wifi_interface = 2;
                        wififaceconnow();
                    }
                    if(servstate == 0){
                        if(formsgandnotf > 2){
                            if(wincrd == 0){
                                checknotfandmsg();
                                //Toast.makeText(MyService.this, " Service Started 000", Toast.LENGTH_LONG).show();
                            }
                            formsgandnotf = 0;
                        }
                        else{
                            if(wincrd == 0) {
                                checkwincard();
                                formsgandnotf = formsgandnotf + 1;
                            }
                            else{
                            }
                        }
                    }
                }
                else{
                    wifi_interface = 1;
                    //networkcalss.networkinf[0] = "none";
                    //Geturldata.hostname = "";
                    //Geturldata.wifilink = "";
                    wificode = "54jug02d6dg7v8wnme7fs7vk2w9biopm";
                    wifilink = Geturldata.wifilink;
                }
                if(continutimer == 0){
                    customHandler.postDelayed(this, dayli);
                }
            }
            catch(Exception e){}

        }

    };
    public void wififaceconnow(){
        wifi_interface = 2;
        try{
            networkcalss = new networkinfo(this);
            wifissid  = networkcalss.getapinfo("ssid");
            wifimacaddr  = networkcalss.getapinfo("mac");
            myipaddr = networkcalss.getdhcpinfo("myip");
        }catch (Exception e){}
        String[] codeap = helperap.getcodebyssid(wifissid,wifimacaddr);
        if(codeap[0] != "notfound"){
            wificode = "54jug02d6dg7v8wnme7fs7vk2w9biopm";
            wifilink = Geturldata.wifilink;
            opennow();
        }

    }
    public void opennow(){
        long chek = helperdb.iscodefound(wificode);
        if(chek>0) {
            try {
                int servertype = Integer.parseInt(helperdb.getnetworkData(wificode)[6]);
                int cooki = Integer.parseInt(helperdb.getnetworkData(wificode)[13]);
                if(!MainActivity.appStatus){
                    if(servertype == 1  && cooki == 0){
                        if (ProfileManager.getInstance(MyService.this).getProfileByName(wificode) != null) {
                            VpnProfile profile;
                            profile = ProfileManager.getInstance(MyService.this).getProfileByName(wificode);
                            profile.setmServerName(helperdb.getnetworkData(wificode)[16]);
                            startOrStopVPN(profile);
                        }
                    }
                    else{
                        if(FloatWidgetService.statopen == 0){
                            FloatWidgetService.statopen = 1;
                            FloatWidgetService.wificode = wificode;
                            FloatWidgetService.servtype = servertype;
                            startService(new Intent(MyService.this, FloatWidgetService.class));
                        }
                    }
                }
                else{
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    System.exit(0);
                }
            } catch (Exception e) {}
        }
        else {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            System.exit(0);
        }
    }
    private void startOrStopVPN(VpnProfile profile) {
        try{
            if (VpnStatus.isVPNActive() && profile.getUUIDString().equals(VpnStatus.getLastConnectedVPNProfile())) {
                Intent disconnectVPN = new Intent(MyService.this, DisconnectVPN.class);
                disconnectVPN.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(disconnectVPN);
            } else {
                startVPN(profile);
            }
        }catch (Exception e){}
    }
    private void startVPN(VpnProfile profile) {
        try{
            ProfileManager.getInstance(MyService.this).saveProfile(MyService.this, profile);
            Intent intent = new Intent(MyService.this, LaunchVPN.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(LaunchVPN.EXTRA_KEY, profile.getUUID().toString());
            intent.setAction(Intent.ACTION_MAIN);
            startActivity(intent);
        }catch (Exception e){}
    }
    int ii = 0;
    public void checkwincard(){
        if(wincrd == 0){
            wincrd = 1;
            try{
                if(wificode != null && wificode != "" && wificode != "10.11.12.13"){
                    String[] offr = helperdb.getoffer(wificode);
                    if(offr[0].contains("contestwifa")){
                        ii = 0;
                        Geturldata.s_urlw = "http://www."+ wifilink +"/wifagapi/finshcontest.php";
                        Geturldata.s_data = "none";
                        Geturldata.s_withpost = 4;
                        Geturldata.s_post = new String[]{"wificode","mycode", "station","result"};
                        Geturldata.s_postdata = new String[]{wificode,Func.Getval("code", mydbdata),offr[1],"0"};
                        try {
                            new Geturldata.s_SendRequest().execute();
                        }
                        catch(Exception e){}
                        try {
                            CountDownTimer waitTimer;
                            waitTimer = new CountDownTimer(25000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    if(ii == 0) {
                                        if (Geturldata.s_data != "none") {
                                            ii = 1;
                                            if(Geturldata.s_data.contains("=re=re")){
                                                if(Geturldata.s_data.contains("=win=")){
                                                    String data = Geturldata.s_data;
                                                    String usrnam = Func.Getval("username",data);
                                                    String pass = Func.Getval("password",data);
                                                    String usernamelng = "رقم الكرت";
                                                    String passlng = "كلمة المرور : ";
                                                    String cardwinlng = "كرت المسابقة الذي ربحته";
                                                    String masgcardlng = "رسالة تلقائية : لقد ربحت كرت من المسابقة رقم الكرت";
                                                    if(helperdb.getlang() > 1){
                                                        usernamelng = "Username";
                                                        passlng = "password : ";
                                                        cardwinlng = "quiz game card you won";
                                                        masgcardlng = "Automatic message: You won a card from the quiz game username ";
                                                    }
                                                    String crd = usernamelng + ":" + usrnam;
                                                    if(!pass.contains("none") && pass != usrnam){ crd = crd + passlng + pass;}
                                                    notfwificode = wificode;
                                                    setNotificationData(cardwinlng,crd);
                                                    setDataForSimpleNotification(helperdb.getnamephonimg(wificode)[2]);
                                                    String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                                    long idd = helperdb.insertmsgdata(wificode,   masgcardlng + crd + passlng + pass, 0, datenow, 1, 0, 1);
                                                    int do1 = helperdb.updateoffer(wificode,0);
                                                    if (do1 <= 0) {
                                                    } else {
                                                        wincrd = 0;
                                                    }
                                                }
                                            }
                                            else{
                                                wincrd = 0;
                                            }
                                        }
                                        else{
                                            //loadingicon(1);
                                            //rsltstatus1.setText(Geturldata.data);
                                        }
                                    }
                                }
                                public void onFinish() {
                                    wincrd = 0;
                                }
                            }.start();
                        }
                        catch (Exception e){}
                    }
                    else{
                        wincrd = 0;
                    }
                }
                else{
                    wincrd = 0;
                }

            }
            catch (Exception e){}
        }

    }
    public void checknotfandmsg(){
        try{
            servstate = 1;
            new Thread(new Runnable() {
                public void run() {
                    String minurl = "http://www." + wifilink + "/wifagapi/msgandnotfv112.php";
                    String minurlads = "wi-fa.org";
                    if(co == 0){
                        if(wificode != null && wificode != "" && wificode != "10.11.12.13"){
                            minurl = "http://www." + wifilink + "/wifagapi/msgandnotfv112.php?mycode=";
                            minurl = minurl + Func.Getval("code", mydbdata) + "&andrid=" + android_id + "&ssidap=" +
                                    wifissid + "&macap=" + wifimacaddr + "&ipaddress=" + myipaddr;
                            minurlads = wifilink;
                        }
                        Geturldata.s_urlw = minurl;
                        Geturldata.s_data = "none";
                        Geturldata.s_withpost = 0;
                        try {
                            Geturldata.s_data = new Geturldata.s_SendRequest().execute().get();
                            co = 1;
                        }
                        catch(Exception e){}
                    }
                    try {
                        if(Geturldata.s_data != "none"){
                            if(Geturldata.s_data.contains("=re=re")){
                                String data = Geturldata.s_data;
                                continutimer = Func.Getintval("continutimer", data);
                                int daylitime = Func.Getintval("daylitime", data);
                                if(daylitime > 5000){
                                    dayli = daylitime;
                                }
                                int msgnum = Func.Getintval("msgnum", data);
                                int notfnum = Func.Getintval("notfnum", data);
                                int m_notreadfnum = Func.Getintval("msgsentnotread", data);
                                if(msgnum>0){
                                    String msgdata = data.substring(0, data.indexOf("=donemsg=done"));
                                    String[] n = msgdata.split("line");
                                    for (int i=0; i<n.length-1; i++){
                                        String wificode2 = Func.Getval("wificode", n[i]);
                                        String msg = Func.Getval("message", n[i]);
                                        int msgtype = Func.Getintval("msgtype", n[i]);
                                        String dattim = Func.Getval("dattim", n[i]);
                                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date date 	= sd.parse(dattim);
                                        dattim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                                        long idd = helperdb.insertmsgdata(wificode2, msg, msgtype, dattim, 1, 0, 1);
                                        if (idd <= 0) {
                                        } else {
                                            long idw = helperdb.updatewtchmsg(wificode2,2);
                                        }
                                    }
                                }
                                if(notfnum>0){
                                    String notfdata = data.substring(data.indexOf("=notfnum="), data.indexOf("=donenof=done"));
                                    String[] n = notfdata.split("line");
                                    for (int i=0; i<n.length-1; i++){
                                        String wificode2 = Func.Getval("wificode", n[i]);
                                        notfwificode = wificode2;
                                        String msg = Func.Getval("message", n[i]);
                                        notfurl = Func.Getval("notfurl", n[i]);
                                        String title = Func.Getval("title", n[i]);
                                        String pictnotf = Func.Getval("pictnotf", n[i]);
                                        if(!wificode2.contains("wifa")){
                                            setNotificationData(title,msg);
                                            setDataForSimpleNotification(helperdb.getnamephonimg(wificode2)[2]);
                                        }
                                        else {
                                            if(!pictnotf.contains("none")){
                                                Geturldata.bitmabdata = "none";
                                                String urlimg = "http://www." + wifilink + "/wifauserman/images/" +pictnotf;
                                                try {
                                                    Geturldata.bitmabdata = new Geturldata.DownloadImageTask().execute(urlimg).get();
                                                }
                                                catch(Exception e){};
                                            }
                                            setNotificationData(title,msg);
                                            setDataForSimpleNotification(Geturldata.bitmabdata);
                                        }
                                    }
                                }
                                if(m_notreadfnum>0){
                                    String msgdata = data.substring(data.indexOf("=msgsentnotread="), data.indexOf("=donenotread=done"));
                                    String[] n = msgdata.split("line");
                                    for (int i=0; i<n.length-1; i++){
                                        String wificode2 = Func.Getval("wificode", n[i]);
                                        int count = Func.Getintval("count", n[i]);
                                        if(count==0){
                                            long idw = helperdb.updatewtchmsg(wificode2,2);
                                        }
                                    }
                                }
                                else{
                                    long idw = helperdb.updatewtchmsgall();
                                }
                                String msgnotsent = helperdb.getmsgnotsent();
                                if(!msgnotsent.contains("none")){
                                    String[] n = msgnotsent.split("line");
                                    for (int i=0; i<n.length-1; i++) {
                                        String id = Func.Getval("id", n[i]);
                                        String wificode3 = Func.Getval("code", n[i]);
                                        String msg = Func.Getval("mssg", n[i]);
                                        String  msgtype = Func.Getval("msgtype", n[i]);
                                        Geturldata.s_urlw = "http://www." + wifilink + "/wifagapi/sendmsg.php";
                                        Geturldata.s_data = "none";
                                        Geturldata.s_withpost = 4;
                                        Geturldata.s_post = new String[]{"code","user","msg","msgtype"};
                                        Geturldata.s_postdata = new String[]{wificode3,Func.Getval("code", mydbdata),msg,msgtype};
                                        try {
                                            Geturldata.s_data =new Geturldata.s_SendRequest().execute().get();
                                        }
                                        catch(Exception e){};
                                        if(Geturldata.s_data != "none"){
                                            if(Geturldata.s_data.contains("=re=re")){
                                                if(Geturldata.s_data.contains("=msg=sent")){
                                                    int idd = helperdb.updatedonemsg(id);
                                                    if (idd <= 0) {
                                                    } else {
                                                        Geturldata.s_data = "none";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if(continutimer <= 0){
                                servstate = 0;
                            }
                            co = 0;
                        }
                        else{
                            if(continutimer <= 0){
                                servstate = 0;
                            }
                        }
                    }
                    catch (Exception e){
                        if(continutimer <= 0){
                            servstate = 0;
                        }
                    }
                }
                }).start();
        } catch (Exception e) {}






    }
    public void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    private void setNotificationData(String title, String txt) {
        notificationTitle = title;
        notificationText = txt;
    }
    private void setDataForSimpleNotification(String iconn) {
        Bitmap bitmap = Geturldata.StringToBitMap(iconn);
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_wifa)
                .setColor(getResources().getColor(R.color.wifacolor))
                .setLargeIcon(Bitmap.createScaledBitmap(bitmap, 100, 100, false))
                .setContentTitle(notificationTitle)
                .setContentText(notificationText);
        sendNotification();
    }
    private void sendNotification() {
        Intent notificationIntent = new Intent(this, Notification_show.class);
        if(notfwificode.contains("wifa")){
            notificationIntent.setAction("=code="+notfwificode+"=title="+notificationTitle+"=msg="+notificationText+"=notfurl="+notfurl);
            notificationIntent.putExtra(Notification_show.ADS_IMG, Geturldata.bitmabdata);
        }
        else
            notificationIntent.setAction("=code="+notfwificode+"=title="+notificationTitle+"=msg="+notificationText);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notificationManager.notify(notificationId, notification);
    }
}
