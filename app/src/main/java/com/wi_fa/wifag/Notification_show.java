package com.wi_fa.wifag;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ahmedalalimi on 09/11/2017.
 */

public class Notification_show extends AppCompatActivity {
    private TextView vtwifiname,titlenotf,notfmsg,title;
    private ImageView circleview;
    private myDbAdapter helperdb;
    public static final String ADS_IMG = "";
    private Button button;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notf_layout);
        setupToolbarWithUpNav(R.id.toolbar, "Notifications");
        vtwifiname = (TextView)findViewById(R.id.vt_wifiname);
        titlenotf = (TextView)findViewById(R.id.title_notf);
        notfmsg = (TextView)findViewById(R.id.notf_msg);
        circleview = (ImageView)findViewById(R.id.circleView);
        button = (Button)findViewById(R.id.btn_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification_show.this.finish();
            }
        });
        helperdb = new myDbAdapter(this);
        String data = getIntent().getAction();
        String code = Func.Getval("code",data);
        String title = Func.Getval("title",data);
        String msg = Func.Getval("msg",data);
        String url = Func.Getval("notfurl",data);
        String img = "";
        String wifiname = "";
        if(!code.contains("wifa") && code != ""){
            long chek = helperdb.iscodefound(code);
            if(chek>0){
                wifiname = helperdb.getnetworkData(code)[1];
                img = helperdb.getnetworkData(code)[3];
            }
            notfmsg.setText(msg);
            titlenotf.setText(title);
            vtwifiname.setText(wifiname);
            if(!img.contains("none") && img != ""){
                circleview.setImageBitmap(Geturldata.StringToBitMap(img));
            }
            else{
                circleview.setVisibility(View.GONE);
            }
        }
        else{
            if(url.contains("www.")){
                openweb(url,title,ADS_IMG);
                finish();
            }
            else{
                notfmsg.setText(msg);
                titlenotf.setText(title);
                vtwifiname.setText("إعـــــــلان");
                if(!getIntent().getStringExtra(ADS_IMG).contains("none") && getIntent().getStringExtra(ADS_IMG) != ""){
                    circleview.setImageBitmap(Geturldata.StringToBitMap(getIntent().getStringExtra(ADS_IMG)));
                }
                else{
                    circleview.setVisibility(View.GONE);
                }
            }
        }



    }

    public void openweb(String url, String name, String icon){
        WebBrowser.url = url;
        WebBrowser.Nname = name;
        WebBrowser.Nimage = icon;
        startActivity(new Intent(this, WebBrowser.class));
    }

    public void setupToolbarWithUpNav(int toolbarId, String titlePage){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(titlePage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
