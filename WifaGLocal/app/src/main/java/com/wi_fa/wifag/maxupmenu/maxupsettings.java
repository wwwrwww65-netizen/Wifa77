package com.wi_fa.wifag.maxupmenu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.wi_fa.wifag.Func;
import com.wi_fa.wifag.Geturldata;
import com.wi_fa.wifag.MainActivity;
import com.wi_fa.wifag.MyService;
import com.wi_fa.wifag.R;
import com.wi_fa.wifag.Str;
import com.wi_fa.wifag.WebBrowser;
import com.wi_fa.wifag.myDbAdapter;

import java.util.ArrayList;

/**
 * Created by ahmedalalimi on 07/10/2017.
 */

public class maxupsettings extends Fragment {
    View view;
    myDbAdapter myDbinfo;
    private TextView stname, stphone;
    int ddd = 0;
    public int musicenb;
    public maxupsettings(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDbinfo = new myDbAdapter(getActivity());
        String myinfodata = myDbinfo.getData();
        int soundenb = Integer.parseInt(Getval("sound", myinfodata));
        musicenb = Integer.parseInt(Getval("music", myinfodata));
        Str.attr.lang = musicenb;
        if(musicenb > 1){
            view = inflater.inflate(R.layout.maxupmenu_settings_en, container, false);
        }
        else {
            view = inflater.inflate(R.layout.maxupmenu_settings, container, false);
        }
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, Str.attr.getstr(3));
        MyService.dayli = 10000;

        stname = (TextView) view.findViewById(R.id.st_name);
        stphone = (TextView) view.findViewById(R.id.st_phone);
        stname.setText(myDbinfo.getnameandphon()[0]);
        //Toast.makeText(getActivity(),  ddd, Toast.LENGTH_LONG).show();
        String phn = myDbinfo.getnameandphon()[1];
        if(phn.length()<2){
            phn = "لا يوجد";
            if(musicenb > 1){ phn = "not found"; }
        }
        stphone.setText(phn);

        LinearLayout about = (LinearLayout) view.findViewById(R.id.st_about);
        Switch sounden = (Switch) view.findViewById(R.id.st_sounden);
        Switch musicen = (Switch) view.findViewById(R.id.st_musicen);
        if(soundenb == 0){
            sounden.setChecked(true);
        }
        if(musicenb == 0 || musicenb == 2){
            musicen.setChecked(true);
        }
        Button button = (Button) view.findViewById(R.id.st_editmyinfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int num = NetWorkDbinfo.getidbynum(0);
                //WebBrowser.id = num;
                //startActivity(new Intent(getActivity(), WebBrowser.class));
                msgbox();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here about
            }
        });

        sounden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    myDbinfo.updatesound(0);
                } else{
                    myDbinfo.updatesound(1);
                }
                MainActivity.myinfodata = myDbinfo.getData();
            }
        });
        musicen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    if(musicenb < 2){
                        musicenb = 0;
                        Str.attr.lang = 0;
                        myDbinfo.updatemusic(0);
                    }
                    else{
                        musicenb = 2;
                        Str.attr.lang = 2;
                        myDbinfo.updatemusic(2);
                    }
                } else{
                    if(musicenb < 2){
                        musicenb = 1;
                        Str.attr.lang = 1;
                        myDbinfo.updatemusic(1);
                    }
                    else{
                        musicenb = 3;
                        Str.attr.lang = 3;
                        myDbinfo.updatemusic(3);
                    }
                }
                MainActivity.myinfodata = myDbinfo.getData();

            }
        });
        TextView wifacot = (TextView) view.findViewById(R.id.st_wifaco);
        ImageView wifacoimg = (ImageView)view.findViewById(R.id.st_wifacoimg);
        //DrawableCompat.setTint(wifacoimg.getDrawable(), ContextCompat.getColor(getContext(), R.color.wifacolor2));
        wifacot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devloper();
            }
        });
        wifacoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devloper();
            }
        });
        LinearLayout aboutlayout = (LinearLayout)view.findViewById(R.id.st_about);
        aboutlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    WebBrowser.url = "http://www." +  Geturldata.wifilink + "/wifiservice/aboutwifag.php";
                    WebBrowser.Nname = "About WIFA";
                    WebBrowser.Nimage = "wifalogo";
                    startActivity(new Intent(getActivity(), WebBrowser.class));
                } catch (Exception e){}
            }
        });
        initCustomSpinner(view);
        return view;
    }

    private void initCustomSpinner(View view) {

        Spinner spinnerCustom= (Spinner) view.findViewById(R.id.spinner);
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<>();
        languages.add("العربية");
        languages.add("English");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustom.setAdapter(adapter);
        if(musicenb > 1) {
            spinnerCustom.setSelection(1);
        }
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(),  item, Toast.LENGTH_LONG).show();
                if(ddd > 0) {
                    if (item == "العربية") {
                        if (musicenb == 2) {
                            musicenb = 0;
                            Str.attr.lang = 0;
                            myDbinfo.updatemusic(0);
                        } else if (musicenb == 3) {
                            musicenb = 1;
                            Str.attr.lang = 1;
                            myDbinfo.updatemusic(1);
                        }
                    } else {
                        if (musicenb == 0) {
                            musicenb = 2;
                            Str.attr.lang = 2;
                            myDbinfo.updatemusic(2);
                        } else if (musicenb == 1){
                            musicenb = 3;
                            Str.attr.lang = 3;
                            myDbinfo.updatemusic(3);
                        }
                    }
                    MainActivity.myinfodata = myDbinfo.getData();
                }
                else{ ddd = 1; }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void msgbox(){
        try{
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.edit_myinfo);
            //dialog.setTitle("message box");

            final EditText name = (EditText) dialog.findViewById(R.id.nameed);
            final EditText phone = (EditText) dialog.findViewById(R.id.phoneed);
            Button butnsave = (Button) dialog.findViewById(R.id.saveinfo);
            Button butncancel = (Button) dialog.findViewById(R.id.cancelinfo);
            if(musicenb > 1) {
                TextView textmsg = (TextView) dialog.findViewById(R.id.vt_headname);
                textmsg.setText("Edit personal data");
                name.setHint("Name OR Nicknamed");
                butnsave.setText("Save");
                butncancel.setText("Cancel");
            }
            String phn = myDbinfo.getnameandphon()[1];
            if(phn.length()<2){
                phn = "";
            }
            name.setText(myDbinfo.getnameandphon()[0]);
            phone.setText(phn);
            dialog.show();

            butnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Geturldata.urlw = "http://" + MainActivity.mainlink + "/updateuserinfo.php";
                    Geturldata.data = "none";
                    Geturldata.withpost = 5;
                    Geturldata.post = new String[]{"code","name","phone","mac","hostname"};
                    Geturldata.postdata = new String[]{Func.Getval("code",MainActivity.myinfodata),name.getText().toString(),phone.getText().toString(),Func.Getval("mac",MainActivity.myinfodata),Func.Getval("hostname",MainActivity.myinfodata)};
                    String dataa = "none";
                    try {
                        dataa = new Geturldata.SendRequest().execute().get();
                    }
                    catch(Exception e){}
                    //Toast.makeText(getActivity(), dataa, Toast.LENGTH_LONG).show();
                    if(dataa != "none"){
                        if(dataa.contains("updated")){
                            String phn2 = phone.getText().toString();
                            if(phn2 == "لا يوجد" || phn2.length()<2 || phn2 == "not found"){
                                phn2 = "0";
                            }
                            int n = myDbinfo.updateNameandphon(name.getText().toString(),phn2);
                            if(n<=0){

                            }
                            else{
                                stname.setText(myDbinfo.getnameandphon()[0]);
                                stphone.setText(myDbinfo.getnameandphon()[1]);

                                dialog.dismiss();
                            }
                        }
                    }


                }
            });

            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }catch(Exception e){}

    }

    private void devloper(){
        try{
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if(musicenb > 1) {
                dialog.setContentView(R.layout.devloper_en);
            }
            else{
                dialog.setContentView(R.layout.devloper);
            }
            dialog.show();

            Button butncancel = (Button) dialog.findViewById(R.id.dv_ok);
            butncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }catch(Exception e){}

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

}
