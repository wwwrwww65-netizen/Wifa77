package com.wi_fa.wifag;

/**
 * Created by ahmedalalimi on 18/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import pl.droidsonroids.gif.GifTextView;

public class LoginActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int EDITTEXT_DELAY = 300;
    public static final int BUTTON_DELAY = 500;
    public static final int VIEW_DELAY = 400;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context = LoginActivity.this;
    private Fragment selectedFragment = null;
    private static String[] android_info = null;
    private GifTextView loadigIcon = null;
    private LinearLayout loadingLayout = null;
    private static String name,urlw,readpage,hostname,mac,android_id;
    private String wifissid,wifimacaddr,myipaddr,lllng;
    private TextView tx1,tx2;
    private EditText named;
    private Button login;
    private int bigncreate = 0;
    myDbAdapter helper;
    DbAccessp helperap;
    networkinfo networkclass;
    CountDownTimer waitTimer,waitTimer2;
    private String[] networkinf = {"54jug02d6dg7v8wnme7fs7vk2w9biopm", "شبكة كلاود نت اللاسلكية", "714141373", "img", "19063010.png", "2", "0", "0", "offer", "offertype", "0", "10.0.0.1"};
    private String mainlink = "www.wifaglocalserver.net/wifagapi";
    private String welcomemsg = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new myDbAdapter(this);
        helperap = new DbAccessp(this);
        long chek = helper.getrowCount(1);
        String sdfew = String.valueOf(chek);

        //String dd = String.valueOf(ds.indexOf("=startresult=")+14);
        //String dd = Getval("image", ds);
        //String dsd = helper.getData();
        //Toast.makeText(context, Geturldata.wifilink, Toast.LENGTH_LONG).show();
        //helper.delete("8");
        //netwokdb.delete("3");
        //setContentView(R.layout.activity_login);
        if(chek == 1){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
        else if(chek == 0){
            setContentView(R.layout.activity_login);
            //findnetwork();
            lllng = Locale.getDefault().getDisplayLanguage();
            defineloadingimg();
            if(lllng.contains("English")){
                tx1.setText("app settings");
                tx2.setText("Enter Your name");
                named.setHint("Real name Or NickName");
                login.setText("Start");
            }
            networkclass = new networkinfo(this);
            android_id = Build.SERIAL;
            if(Build.SERIAL == "" || Build.SERIAL == "unknown" || Build.SERIAL.length() < 8){
                android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            int width = dm.widthPixels;
            android_info = new String[]{String.valueOf(height),String.valueOf(width),Build.MANUFACTURER,
                    Build.MODEL,Build.BOARD,Build.HARDWARE,Build.VERSION.RELEASE,Build.FINGERPRINT};



            getdatafromhostname();
            ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
            ViewGroup container = (ViewGroup) findViewById(R.id.container);

            ViewCompat.animate(logoImageView)
                    .translationY(-250)
                    .setStartDelay(STARTUP_DELAY)
                    .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                    new DecelerateInterpolator(1.2f)).start();

            for (int i = 0; i < container.getChildCount(); i++) {
                View v = container.getChildAt(i);
                ViewPropertyAnimatorCompat viewAnimator;

                if (v instanceof EditText) {
                    viewAnimator = ViewCompat.animate(v)
                            .scaleY(1).scaleX(1)
                            .setStartDelay((EDITTEXT_DELAY * i) + 500)
                            .setDuration(500);
                } else if (v instanceof Button) {
                    viewAnimator = ViewCompat.animate(v)
                            .scaleY(1).scaleX(1)
                            .setStartDelay((BUTTON_DELAY * i) + 500)
                            .setDuration(500);
                } else {
                    viewAnimator = ViewCompat.animate(v)
                            .translationY(50).alpha(1)
                            .setStartDelay((VIEW_DELAY * i) + 500)
                            .setDuration(1000);
                }
                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            }
            login.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {
                    loginbybtn();
                }
            });
            named.setOnKeyListener(new View.OnKeyListener(){
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event){
                    if(keyCode == event.KEYCODE_ENTER){
                        //do what you want
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(named.getWindowToken(), 0);
                        loginbybtn();
                        return true;
                        //Toast.makeText(context, Geturldata.wifilink, Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
            // end else
        }
    }

    private void loginbybtn(){
        try{
            name =  named.getText().toString();
            if(name.length() > 0){
                if (networkinfo.iswificonnect(getBaseContext())) {
                    urlw = "http://" + mainlink + "/newuser.php";
                    mac = networkclass.getMACAddress("wlan0");
                    hostname = networkclass.getHostName("d");
                    wifissid  = networkclass.getapinfo("ssid");
                    wifimacaddr  = networkclass.getapinfo("mac");
                    myipaddr = networkclass.getdhcpinfo("myip");
                    readpage = "none";
                    loadingicon1(0);
                    try {
                        new SendRequest().execute();
                    }
                    catch(Exception e){}

                    geturldata();
                }
                else{

                    if(lllng.contains("English")){
                        Toast.makeText(context, "Internet Offline", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "غير متصل بالإنترنت", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                if(lllng.contains("English")){
                    Toast.makeText(context, "Do not leave the name empty", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "لا تترك الاسم فارغاً", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){}

    }

    private void defineloadingimg(){
        tx1 = (TextView) findViewById(R.id.tx_1);
        tx2 = (TextView) findViewById(R.id.tx_2);
        login = (Button) findViewById(R.id.login);
        named = (EditText)findViewById(R.id.nameed);
        loadingLayout = (LinearLayout)findViewById(R.id.Linearlay_loading);
        loadigIcon = (GifTextView) findViewById(R.id.image_loading);

        //DrawableCompat.setTint(loadigIcon.getDrawable(), ContextCompat.getColor(context, R.color.white));
    }
    private void loadingicon1(int status){
        if(status == 0){
            if(lllng.contains("English")){
                tx1.setText("setting ....");
            }
            else {
                tx1.setText("يتم الإعداد ....");
            }
            tx2.setVisibility(View.GONE);
            named.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            loadigIcon.setVisibility(View.VISIBLE);
        }
        else if(status == 1){
            if(lllng.contains("English")){
                tx1.setText("app settings");
            }
            else {
                tx1.setText("إعداد التطبيق");
            }
            tx2.setVisibility(View.VISIBLE);
            named.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            loadigIcon.setVisibility(View.GONE);
        }
    }
    int i = 0;
    private void geturlnetworkinfodata(){
        stoptimer();
        try {
            waitTimer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(Geturldata.data != "none"){
                        if(i == 0){
                            i = 1;
                            if(Geturldata.data.contains("=re=re")){
                                //Toast.makeText(context, Getval("phone", Geturldata.data), Toast.LENGTH_LONG).show();
                                if(Geturldata.data.contains("=wifi=enable")){
                                    networkinf[1] = Getval("name", Geturldata.data);
                                    networkinf[2] = Getval("phone", Geturldata.data);
                                    networkinf[3] = Getval("img", Geturldata.data);
                                    networkinf[4] = Getval("img", Geturldata.data);
                                    networkinf[5] = Getval("rad", Geturldata.data);
                                    networkinf[6] = Getval("servt", Geturldata.data);
                                    networkinf[7] = Getval("logint", Geturldata.data);
                                    networkinf[8] = Getval("offer", Geturldata.data);
                                    networkinf[9] = Getval("offertype", Geturldata.data);
                                    networkinf[10] = Getval("statapp", Geturldata.data);
                                    networkinf[11] = Getval("serverip", Geturldata.data);
                                    welcomemsg = Getval("welcomemsg", Geturldata.data);
                                    if(!networkinf[3].contains("none")){
                                        Geturldata.bitmabdata = "none";
                                        // الاي بي لازم يتغير
                                        String urlimg = "http://www.wifaglocalserver.net/wifauserman/images/" +networkinf[3];
                                        try {
                                            new Geturldata.DownloadImageTask().execute(urlimg);
                                        }
                                        catch(Exception e){}
                                        getimgstring();
                                    }
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
                }
                public void onFinish() {
                    //After 60000 milliseconds (60 sec) finish current
                    //if you would like to execute something when time finishes
                }
            }.start();
        }
        catch (Exception e){}
        try {

        }
        catch (Exception e){}
    }
    private void stoptimer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }
    private String Getval(String attr, String str){
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
    private void getimgstring(){
        stoptimer();
        try {
            waitTimer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(Geturldata.bitmabdata != "none"){
                        networkinf[3] = Geturldata.bitmabdata;
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
    int ii = 0;
    private void getdatafromhostname(){
        try {
            waitTimer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(ii == 0){
                        if(Geturldata.hostname != "none"){
                            ii = 1;
                            if(!Geturldata.hostname.contains("10.11.12.13")){
                                networkinf[0] = Geturldata.hostname;
                                Geturldata.urlw = "http://" + mainlink + "/getnetworkinfo.php";
                                Geturldata.data = "none";
                                Geturldata.withpost = 3;
                                Geturldata.post = new String[]{"wificode","andrid","versin"};
                                Geturldata.postdata = new String[]{networkinf[0],android_id,"1.1.3"};
                                try {
                                    new Geturldata.SendRequest().execute();
                                }
                                catch(Exception e){}
                                geturlnetworkinfodata();
                            }
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
    int iii = 0;
    private void geturldata(){
        iii = 0;
        try {
            waitTimer2 = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if (iii == 0) {
                        if (readpage != "none") {
                            iii = 1;
                            if (readpage.contains("Exception")) {
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                                loadingicon1(1);
                            } else if (readpage.contains("false")) {
                                Toast.makeText(context, "Error 404", Toast.LENGTH_LONG).show();
                                loadingicon1(1);
                            } else {
                                if (readpage.contains("=re=re")) {
                                    gobign(readpage);
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                    loadingicon1(1);
                                }
                            }
                            stoptimer();
                        }
                    }
                }
                public void onFinish() {
                    //After 60000 milliseconds (60 sec) finish current
                    //if you would like to execute something when time finishes
                    if (readpage == "none") {
                        if(lllng.contains("English")){
                            Toast.makeText(context, "Error, try again", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(context, "هناك خطأ حاول مرة أخرى", Toast.LENGTH_LONG).show();
                        }
                        loadingicon1(1);
                    }
                }
            }.start();
        }
        catch (Exception e){}
    }

    private void gobign(String dtdt){
        try{
            stoptimer();
            int codeU = 0;
            String link = "";
            if(dtdt.contains("done")){
                codeU = Integer.parseInt(Getval("code", dtdt));
                link = Getval("link", dtdt);

                //Toast.makeText(context, networkinf[2], Toast.LENGTH_LONG).show();

                String lastupdt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                //Toast.makeText(context, "الى هنا تم", Toast.LENGTH_LONG).show();
                int music = 0;
                if(lllng.contains("English")){
                    music = 2;
                }
                long id = helper.insertmyinfo(name,"0",codeU,hostname,mac,0,music,link,lastupdt);
                if(id<=0)
                {
                    Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                }
                else {
                    if(networkinf[0] != "none"){
                        try{
                            String namen = networkinf[1];
                            String phonen = networkinf[2];
                            String img = networkinf[3];
                            String imgur = networkinf[4];
                            int rad = Integer.parseInt(networkinf[5]);
                            int servt = Integer.parseInt(networkinf[6]);
                            int logint = Integer.parseInt(networkinf[7]);
                            String offer = networkinf[8];
                            int offert = Integer.parseInt(networkinf[9]);
                            int statapp = Integer.parseInt(networkinf[10]);
                            long idd = helper.insertnewnetwork(namen, phonen, networkinf[0], img, imgur, rad, servt, logint, "", "", 1, offer, offert, 1, lastupdt, statapp,networkinf[11],Geturldata.wifilink);
                            if (idd <= 0) {
                                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                            } else {
                                long ap = helperap.insert(networkinf[0],Geturldata.wifilink,wifissid,wifimacaddr);
                                if(!welcomemsg.contains("none")){
                                    String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                    long iddd = helper.insertmsgdata(networkinf[0], welcomemsg, 0, datenow, 1, 0, 1);
                                    if (idd <= 0) {
                                    } else {
                                    }
                                }
                            }
                        }catch (Exception e){}
                    }
                    if(lllng.contains("English")){
                        Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "تم بنجاح", Toast.LENGTH_LONG).show();
                    }

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
            }
            else {
                if(lllng.contains("English")){
                    Toast.makeText(context, "There is an error that is not registered", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "هناك خطأ لم يتم التسجيل", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e){}


    }
    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(urlw);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("wificode", networkinf[0]);
                postDataParams.put("name", name);
                postDataParams.put("phone", "");
                postDataParams.put("mac", mac);
                postDataParams.put("hostname", hostname);
                postDataParams.put("ssidap", wifissid);
                postDataParams.put("macap", wifimacaddr);
                postDataParams.put("ipaddress", myipaddr);
                postDataParams.put("andrid", android_id);
                postDataParams.put("versin", "1.1.3");

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
                    return new String("false:"+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception");
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
}
