package com.wi_fa.wifag;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ahmedalalimi on 04/10/2017.
 */

public class networkinfo {
    public static DhcpInfo dhcpinf;
    public static Context mContext;
    public static CountDownTimer waitTimer;
    public networkinfo(Context context) {
        this.mContext = context;
    }
    public static String[] networkinf = {"54jug02d6dg7v8wnme7fs7vk2w9biopm", "شبكة كلاود نت اللاسلكية", "714141373", "img", "imgurl", "2", "0", "0", "offer", "offertype", "0", "10.0.0.1"};
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }
    public static String getapinfo(String itm){
        String str = "none";
        WifiManager wifiManager = (WifiManager) mContext.getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();
        if(itm == "ssid") {
            str = info.getSSID();
            str = str.replace("\"","");
        }
        else if(itm == "mac") {
            str = info.getBSSID();
        }
        return str;
    }
    public static String getdhcpinfo(String itm){
        String str = "none";
        WifiManager wifiManager = (WifiManager) mContext.getSystemService (Context.WIFI_SERVICE);
        dhcpinf = wifiManager.getDhcpInfo();
        if(itm == "myip") {
            str = intToIp(dhcpinf.ipAddress);
        }
        else if(itm == "serverip") {
            str = intToIp(dhcpinf.serverAddress);
        }
        else if(itm == "gateway") {
            str = intToIp(dhcpinf.dns1);
        }
        return str;
    }

    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }
    public static void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    public static void findnetworkcode(){
    }

    public static void getallinfntwrkbycod(String code, String user, String apssid, String apmac, String myhostname, String mymac, String myipaddr, String withimg){
        Func.doneupdtinfo = 0;
        Geturldata.urlw = "http://www."+ Geturldata.wifilink +"/wifagapi/getnetworkinfo.php?rand=" + new Random().nextInt(61) + 20;
        Geturldata.data = "none";
        Geturldata.withpost = 10;
        Geturldata.post = new String[]{"wificode","mycode", "user", "ssidap","macap","hostname","mac","ipaddress","andrid","versin"};
        Geturldata.postdata = new String[]{networkinf[0],code,user,apssid,apmac,myhostname,mymac,myipaddr,MainActivity.android_id,"1.1.3"};
        try {
            Geturldata.data = new Geturldata.SendRequest().execute().get();
        }
        catch(Exception e){}
        geturlnetworkinfodata(withimg);
        setV09data();
    }
    public static int ii = 0;
    public static void geturlnetworkinfodata(final String withimg){
        ii = 0;
        try {
            if(Geturldata.data != "none"){
                ii = 1;
                Func.wifidatainofupdate = Geturldata.data;
                if(Geturldata.data.contains("=re=re")){
                    //Toast.makeText(context, "dd", Toast.LENGTH_LONG).show();
                    if(Geturldata.data.contains("=wifi=enable")){
                        networkinf[1] = Getval("name", Geturldata.data);
                        networkinf[2] = Getval("phone", Geturldata.data);
                        String imgg = Getval("img", Geturldata.data);
                        networkinf[4] = imgg;
                        networkinf[5] = Getval("rad", Geturldata.data);
                        networkinf[6] = Getval("servt", Geturldata.data);
                        networkinf[7] = Getval("logint", Geturldata.data);
                        networkinf[8] = Getval("offer", Geturldata.data);
                        networkinf[9] = Getval("offertype", Geturldata.data);
                        networkinf[10] = Getval("statapp", Geturldata.data);
                        networkinf[11] = Getval("serverip", Geturldata.data);
                        String adsimg = Getval("adsbottomimg",Geturldata.data);
                        if(adsimg != "" && !adsimg.contains("none")){
                            Func.ads_bottom = adsimg;
                        }
                        if(!imgg.contains("none") && !withimg.contains(imgg)){
                            Geturldata.bitmabdata = "none";
                            String urlimg = "http://www."+ Geturldata.wifilink +"/wifauserman/images/" +networkinf[4];
                            try {
                                Geturldata.bitmabdata = new Geturldata.DownloadImageTask().execute(urlimg).get();
                            }
                            catch(Exception e){}
                            if(Geturldata.bitmabdata != "none"){
                                networkinf[3] = Geturldata.bitmabdata;
                            }
                        }
                        else{
                            networkinf[3] = imgg;
                        }
                        Func.doneupdtinfo = 1;
                    }
                    else if(Geturldata.data.contains("=wifi=disable")){

                    }
                    else if(Geturldata.data.contains("=wifi=expire")){

                    }
                    else if(Geturldata.data.contains("=wifi=notfound")){

                    }
                }
            }

        }
        catch (Exception e){}
    }
    public static void setV09data(){
        if(Func.wifidatainofupdate != Str.attr.c000){
            if(Func.Getval(Str.attr.c001,Func.wifidatainofupdate) != Str.attr.c000 &&
                    Func.Getintval(Str.attr.c004,Func.wifidatainofupdate) > 0 &&
                    Func.Getval(Str.attr.c001,Func.wifidatainofupdate).contains(Str.attr.c002)){
                String vv = "";
                try{
                    vv = Func.wifidatainofupdate.split(Str.attr.c001 + "=" + Str.attr.c002)[1];
                }catch (Exception e) {}
                if(vv != "" && vv.contains(Str.attr.c015)){
                    Func.setV09ArrayListlistdata(setV09datalist(Func.wifidatainofupdate));
                }
            }

        }
    }
    public static List<V09Array> setV09datalist(String maindata){
        List<V09Array> data = new ArrayList<>();


        String[] n = maindata.split("line");
        for (int i=0; i<n.length-1; i++){
            V09Array item = new V09Array();
            item.setNumds(i);
            item.setActionsds(Func.Getintval(Str.attr.c006, n[i]));
            item.setHomeds(Func.Getintval(Str.attr.c005, n[i]));
            item.setMsgds(Func.Getintval(Str.attr.c007, n[i]));
            item.setContestds(Func.Getintval(Str.attr.c010, n[i]));
            item.setSettingsds(Func.Getintval(Str.attr.c008, n[i]));
            item.setRandomds(Func.Getintval(Str.attr.c009, n[i]));
            item.setTimeallds(Func.Getintval(Str.attr.c012, n[i]));
            item.setTimewatchds(Func.Getintval(Str.attr.c013, n[i]));
            item.setNamev(Func.Getval(Str.attr.c011, n[i]));
            item.setUrlclick(Func.Getval(Str.attr.c014, n[i]));
            item.setWatchedds(0);
            data.add(item);
        }
        return data;
    }
    private static String Getval(String attr, String str){
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
        return val;
    }
    public static boolean iswificonnect(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(mWifi.isConnected()){
            return true;
        }
        return false;
    }
    public static void getdatafromhostname(){
        try {
            CountDownTimer waitTimer;
            waitTimer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(Geturldata.hostname != "none"){
                        networkinf[0] = Geturldata.hostname.replace(" ", "");
                        stoptimer();
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
    public static String intToIp(int i) {
        return ( i & 0xFF) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ((i >> 24 ) & 0xFF ) ;
    }
}
