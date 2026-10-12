package com.wi_fa.wifag;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wi_fa.wifag.adapter.QuizArraydata;
import com.wi_fa.wifag.adapter.Quiz_Main_Data;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by ahmedalalimi on 14/10/2017.
 */

public class Quiz_explane extends AppCompatActivity {
    private TextView rule1,rule2,rule3,rule4,rule5;
    private LinearLayout LinearLayloading,allmain;
    private GifTextView loadigIcon = null;
    private Button startButton;
    public Quiz_Main_Data qmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_explane);
        definedviewslayout();
        loadingicon(0);
        setruledata();

    }
    private void definedviewslayout(){
        try{
            LinearLayloading = (LinearLayout)findViewById(R.id.Linearlay_loading);
            allmain = (LinearLayout)findViewById(R.id.all_main);
            loadigIcon =  (GifTextView)findViewById(R.id.image_loading);
            rule1 = (TextView)findViewById(R.id.rule_one);
            rule2 = (TextView)findViewById(R.id.rule_two);
            rule3 = (TextView)findViewById(R.id.rule_three);
            rule4 = (TextView)findViewById(R.id.rule_four);
            rule5 = (TextView)findViewById(R.id.rule_five);
            startButton = (Button)findViewById(R.id.start_button);
            if(Str.attr.lang > 1){
                startButton.setText("START");
            }
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingicon(0);
                    getquizdatafromweb();

                }
            });
        }catch(Exception e){}

    }
    private void loadingicon(int status){
        if(status == 0){
            LinearLayloading.setVisibility(View.VISIBLE);
            loadigIcon.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.GONE);
            allmain.setVisibility(View.GONE);
        }
        else if(status == 1){
            LinearLayloading.setVisibility(View.GONE);
            loadigIcon.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
            allmain.setVisibility(View.VISIBLE);
        }
    }
    public void setruledata(){
        try{
            if(Func.wifidatainofupdate != "none"){
                int[] ss = Func.getcontestinfo();
                if(ss != null){
                    int afterlogin = Func.Getintval("afterlogin", Func.wifidatainofupdate);
                    if(afterlogin > 0){
                        if(MyService.loginstate == 0){
                            String strconres = "";
                            strconres = Str.attr.getstr(26);
                            loadingicon(1);
                            rule1.setText(strconres);
                            rule2.setVisibility(View.GONE);
                            rule3.setVisibility(View.GONE);
                            rule4.setVisibility(View.GONE);
                            rule5.setVisibility(View.GONE);
                            startButton.setVisibility(View.GONE);
                        }
                        else{
                            setexplainquiz(ss);
                        }
                    }
                    else{
                        setexplainquiz(ss);
                    }
                }
                else{
                    int conres = Func.Getintval("contest", Func.wifidatainofupdate);
                    String strconres = "";
                    if(conres == 1 || conres == 4 || conres == 5 || conres == 6){
                        strconres = Str.attr.getstr(27);
                    }
                    else if(conres == 2){
                        strconres = Str.attr.getstr(28);
                    }
                    else if(conres == 3){
                        strconres = Str.attr.getstr(29);
                    }
                    else {
                        strconres = Str.attr.getstr(30);
                    }
                    loadingicon(1);
                    rule1.setText(strconres);
                    rule2.setVisibility(View.GONE);
                    rule3.setVisibility(View.GONE);
                    rule4.setVisibility(View.GONE);
                    rule5.setVisibility(View.GONE);
                    startButton.setVisibility(View.GONE);
                }
            }
        }catch(Exception e){}


    }
    private void setexplainquiz(int[] ss){
        String r1 = "1- تتكون المسابقة من " + ss[1] + " سؤال في ثمانية مجالات ثقافية مختلفة . لكل سؤال أربعة خيارات ، يتم اختيار الجواب الصحيح بالنقر عليه . ";
        if(Str.attr.lang > 1){
            r1 = "1. quiz game consists of " + ss[1] + " questions, Answer the question as fast as you can to save time";
        }
        String r2 = "2- وقت الإجابة لكل سؤال هو " + ss[0] + " ثانية ، إذا انتهت ولم تقم بإختيار الجواب الصحيح ستنتهي المسابقة . ";
        if(Str.attr.lang > 1){
            r2 = "2. You have a maximum of " + ss[0] + " seconds to answer each question";
        }
        String r3 = "3- عدد المحطات " + ss[2] + " محطات كل محطة " + ss[3] + " اسئلة ، عند الوصول إلى أول محطة يمكنك مواصلة المسابقة وقد ضمنت كرت لا يمكن فقدانه " +
                "ولكل محطة جائزة مختلفة فعند وصولك الى اخر محطة ستربح اكبر كرت.  ";
        if(Str.attr.lang > 1){
            r3 = "3. There is " + ss[2] + " stations, Each station has " + ss[3] + " questions";
        }
        String r4 = "4- لديك وسائل مساعدة وهي : تغيير سؤال و حذف اجابتين يمكنك استخدامها مرة واحدة إلى نهاية المسابقة .";
        if(Str.attr.lang > 1){
            r4 = "4. Means of assistance : Change a question and Delete two answers option";
        }
        if(ss[5] == 1 && ss[6] == 1){
            r4 = "4- ليس لديك أية وسائل مساعدة إلى نهاية المسابقة .";
            if(Str.attr.lang > 1 ){
                r4 = "4. You don't have any means of assistance";
            }
        }
        else if(ss[5] == 0 && ss[6] == 1){
            r4 = "4- لديك وسيلة مساعدة وهي تغيير سؤال تستخدمها مرة واحدة إلى نهاية المسابقة .";
            if(Str.attr.lang > 1){
                r4 = "4. Means of assistance : Change a question";
            }
        }
        else if(ss[5] == 1 && ss[6] == 0){
            r4 = "4- لديك وسيلة مساعدة وهي حذف اجابتين تستخدمها مرة واحدة إلى نهاية المسابقة .";
            if(Str.attr.lang > 1){
                r4 = "4. Means of assistance : Delete two answers option";
            }
        }
        String try_d = "";
        String win_n = "مرة واحدة";
        if(Str.attr.lang > 1){
            win_n = "once";
        }
        if(ss[10]>1){
            try_d = String.valueOf(ss[10]);
        }
        if(ss[7]>1){
            win_n = String.valueOf(ss[7]) + " مرات ";
        }
        String r5 = "5- لديك عدد " + ss[9] + " محاولات للمسابقة كل " + try_d + " يوم ، ويمكنك الفوز " +
                win_n + " كل يوم. ";
        if(Str.attr.lang > 1){
            r5 = "5. You have " + ss[9] + " lives evrey " + try_d + " days, and after that you loss the quiz game, And can win " + win_n + " every day";
        }
        rule1.setText(r1);
        rule2.setText(r2);
        rule3.setText(r3);
        rule4.setText(r4);
        rule5.setText(r5);
        loadingicon(1);
        startButton.setVisibility(View.VISIBLE);
        qmd = new Quiz_Main_Data(ss[0],ss[1],ss[2],ss[4],ss[5],ss[6],ss[7],ss[8],ss[9],ss[10]);
    }
    private int ii = 0;
    private void getquizdatafromweb(){
        startButton.setEnabled(false);
        Geturldata.urlw = "http://"+ MainActivity.mainlink +"/getcontestdata.php?lang=" + Str.attr.lang;
        Geturldata.data = "none";
        Geturldata.withpost = 3;
        Geturldata.post = new String[]{"wificode","mycode", "andrid"};
        Geturldata.postdata = new String[]{MainActivity.wifiCode,Func.Getval("code",MainActivity.myinfodata),MainActivity.android_id};
        try {
            new Geturldata.SendRequest().execute();
        }
        catch(Exception e){}
        ii = 0;
        try {
            CountDownTimer waitTimer;
            waitTimer = new CountDownTimer(60000, 3000) {
                public void onTick(long millisUntilFinished) {
                    if(ii == 0) {
                        if (Geturldata.data != "none") {
                            ii = 1;
                            if(Geturldata.data.contains("=re=re")){
                                if(Geturldata.data.contains("=contest=enable")){
                                    String data = Geturldata.data;
                                    int afterlogin = Func.Getintval("afterlogin", data);
                                    if(afterlogin > 0){
                                        if(MyService.loginstate == 0){
                                            String strconres = "";
                                            strconres = Str.attr.getstr(26);
                                            loadingicon(1);
                                            rule1.setText(strconres);
                                            rule2.setVisibility(View.GONE);
                                            rule3.setVisibility(View.GONE);
                                            rule4.setVisibility(View.GONE);
                                            rule5.setVisibility(View.GONE);
                                            startButton.setVisibility(View.GONE);
                                        }
                                        else if(MyService.loginstate == 1 && Func.loginuser.length() > 0){
                                            qmd.setlistdata(setquizdatalist(data));
                                            Intent startButtonIntent = new Intent(Quiz_explane.this, QuizHomeActivity.class);
                                            startActivity(startButtonIntent);
                                            Quiz_explane.this.finish();
                                        }
                                    }
                                    else{
                                        qmd.setlistdata(setquizdatalist(data));
                                        Intent startButtonIntent = new Intent(Quiz_explane.this, QuizHomeActivity.class);
                                        startActivity(startButtonIntent);
                                        Quiz_explane.this.finish();
                                    }
                                }
                                else{
                                    int conres = Func.Getintval("contest", Geturldata.data);
                                    String strconres = "";
                                    if(conres == 1 || conres == 4 || conres == 5 || conres == 6){
                                        strconres = Str.attr.getstr(27);
                                    }
                                    else if(conres == 2){
                                        strconres = Str.attr.getstr(28);
                                    }
                                    else if(conres == 3){
                                        strconres = Str.attr.getstr(29);
                                    }
                                    else {
                                        strconres = Str.attr.getstr(30);
                                    }
                                    loadingicon(1);
                                    rule1.setText(strconres);
                                    rule2.setVisibility(View.GONE);
                                    rule3.setVisibility(View.GONE);
                                    rule4.setVisibility(View.GONE);
                                    rule5.setVisibility(View.GONE);
                                    startButton.setVisibility(View.GONE);
                                }
                            }
                            else{
                                rule2.setVisibility(View.GONE);
                                rule3.setVisibility(View.GONE);
                                rule4.setVisibility(View.GONE);
                                rule5.setVisibility(View.GONE);
                                loadingicon(1);
                                rule1.setText(Str.attr.getstr(31));
                                startButton.setEnabled(true);
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
    private List<QuizArraydata> setquizdatalist(String maindata){
        List<QuizArraydata> data = new ArrayList<>();
        String[] n = maindata.split("line");
        qmd.availbalequiznum = n.length;
        for (int i=0; i<n.length-1; i++){
            QuizArraydata item = new QuizArraydata();
            item.setNum(i);
            item.setQuistion(Func.Getval("quiz", n[i]));
            item.setOption1(Func.Getval("op1", n[i]));
            item.setOption2(Func.Getval("op2", n[i]));
            item.setOption3(Func.Getval("op3", n[i]));
            item.setOption4(Func.Getval("op4", n[i]));
            item.setRightop(Func.Getintval("righto", n[i]));
            item.setLevel(Func.Getintval("level", n[i]));
            item.setSpecialty(Func.Getval("specialty", n[i]));
            item.setDone(0);
            data.add(item);
        }
        return data;
    }

}
