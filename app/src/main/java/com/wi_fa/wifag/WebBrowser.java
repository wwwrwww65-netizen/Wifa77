package com.wi_fa.wifag;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ahmedalalimi on 13/10/2017.
 */

public class WebBrowser extends BaseActivity {
    public static String Nname,Nimage,url;
    private Menu menu;

    WebView myWebView;
    MenuItem mm;

    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_browser);

        setupToolbarWithUpNav(R.id.toolbar, Nname, R.drawable.ic_action_back);

        myWebView = (WebView) findViewById(R.id.myWebView);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        //myWebView.setWebViewClient(new DemoWebViewClient());
        //String ur = "http://192.168.9.1/user";
        myWebView.loadUrl(url);
        //onPageFinished(myWebView, ur);

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
        mm = menu.findItem(R.id.user_photo);
        mm.setTitle(Nname);
        if(Nimage != null && Nimage != "" && Nimage.length() > 100){
            Bitmap bitmap = Geturldata.StringToBitMap(Nimage);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            mm.setIcon(drawable);
        }
        else if(Nimage == "wifalogo"){
            mm.setIcon(R.drawable.wifalogo);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                //Intent intent = new Intent(WebBrowser.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class DemoWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
