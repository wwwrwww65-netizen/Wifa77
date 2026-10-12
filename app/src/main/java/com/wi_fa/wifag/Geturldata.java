package com.wi_fa.wifag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ahmedalalimi on 09/10/2017.
 */

public class Geturldata {
    public static String data,s_data,data2,ipforhostname;
    public static String hostname = "54jug02d6dg7v8wnme7fs7vk2w9biopm";
    public static String wifilink = "wifaglocalserver.net";
    public static String filenm = "";
    public static String[] post;
    public static String[] postdata;
    public static int withpost,s_withpost,response,response2;
    public static String urlw;
    public static String[] s_post;
    public static String[] s_postdata;
    public static String s_urlw;
    public static String videodon = "done";
    public static String bitmabdata,bitmabdata2;
    public static class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(urlw);

                JSONObject postDataParams = new JSONObject();
                if(withpost > 0){
                    for(int i= 0;i<withpost;i++)
                    {
                        postDataParams.put(post[i], postdata[i]);
                    }
                }
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
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
                response = responseCode;
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        //MainActivity.str[i] = line.toString();
                        //MainActivity.listt.add(line);
                        //break;
                        data =line.toString();
                        data2 =line.toString();
                    }

                    in.close();
                    //MainActivity.str[5] = sb.toString();
                    //MainActivity.listt.add(sb.toString());
                    return sb.toString();

                }
                else {
                    return new String("false:false:"+responseCode);
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
            data =result;
            data2 =result;
        }
    }

    public static class s_SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(s_urlw);

                JSONObject postDataParams = new JSONObject();
                if(s_withpost > 0){
                    for(int i= 0;i<s_withpost;i++)
                    {
                        postDataParams.put(s_post[i], s_postdata[i]);
                    }
                }
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
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
                response2 = responseCode;
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        //MainActivity.str[i] = line.toString();
                        //MainActivity.listt.add(line);
                        //break;
                        s_data =line.toString();
                    }

                    in.close();
                    //MainActivity.str[5] = sb.toString();
                    //MainActivity.listt.add(sb.toString());
                    return sb.toString();

                }
                else {
                    return new String("false:false:"+responseCode);
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
            s_data =result;
        }
    }

    public static String getPostDataString(JSONObject params) throws Exception {

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

    public static class NetworkSniffTask extends AsyncTask<Void, Void, Void> {

        //private static final String TAG = Constants.TAG + "nstask";

        private WeakReference<Context> mContextRef;

        public NetworkSniffTask(Context context) {
            mContextRef = new WeakReference<Context>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Log.d(TAG, "Let's sniff the network");

            try {
                Context context = mContextRef.get();

                if (context != null) {

                    //ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    //NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    //WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                    //WifiInfo connectionInfo = wm.getConnectionInfo();
                    //int ipAddress = connectionInfo.getIpAddress();
                    //String ipString = Formatter.formatIpAddress(ipAddress);
                    String ipserv = ipforhostname;
                    InetAddress address2 = InetAddress.getByName(ipserv);
                    //boolean reachable = address.isReachable(1000);
                    String hostName2 = address2.getCanonicalHostName();
                    //Toast.makeText(context, String.valueOf(activeNetwork), Toast.LENGTH_LONG).show();
                    //Log.d(TAG, "activeNetwork: " + String.valueOf(activeNetwork));
                    //Log.d(TAG, "ipString: " + String.valueOf(ipString));

                    //String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
                    //Log.d(TAG, "prefix: " + prefix);
                    hostname = Str.attr.c038;
                    wifilink = Str.attr.c039;

                }
            } catch (Throwable t) {
                //Log.e(TAG, "Well that's not good.", t);
            }

            return null;
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){}
        protected String doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            String outp = BitMapToString(mIcon11);
            return outp;
        }

        protected void onPostExecute(String result) {
            //bmImage.setImageBitmap(result);
            bitmabdata = result;
            bitmabdata2 = result;
        }
    }

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public static File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
            filenm = file.toString();
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }
    public static class DownloadTask extends AsyncTask<String, Integer, String> {

        private WeakReference<Context> mContextRef;

        public DownloadTask(Context context) {
            mContextRef = new WeakReference<Context>(context);
        }
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                Context context = mContextRef.get();

                if (context != null) {
                    String urlmain = Str.attr.c017 + Str.attr.c020 + sUrl[0];
                    URL url = new URL(urlmain);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);

                    //connect
                    urlConnection.connect();
                    //File SDCardRoot = Environment.getExternalStorageDirectory();
                    //set the path where we want to save the file
                    //create a new file, to save the downloaded file
                    //File file = new File(SDCardRoot,sUrl[0]);
                    File file = getTempFile(context,urlmain);
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    //FileOutputStream fileOutput = openFileOutput(filename, Context.MODE_PRIVATE);;
                    //Stream used for reading the data from the internet
                    InputStream inputStream = urlConnection.getInputStream();

                    //this is the total size of the file which we are downloading
                    //totalSize = urlConnection.getContentLength();


                    //create a buffer...
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;

                    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                        fileOutput.write(buffer, 0, bufferLength);
                        //downloadedSize += bufferLength;
                    }
                    //close the output stream when complete //
                    fileOutput.close();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false

        }

        @Override
        protected void onPostExecute(String result) {
            videodon = "done";
            filenm = "";
        }
    }
}
