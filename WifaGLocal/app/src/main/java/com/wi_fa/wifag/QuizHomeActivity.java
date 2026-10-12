package com.wi_fa.wifag;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wi_fa.wifag.adapter.Quiz_Main_Data;

import java.util.Random;

/**
 * Created by ahmedalalimi on 14/10/2017.
 */

public class QuizHomeActivity extends AppCompatActivity {
    private int time,rightop;
    private int setchangq = 0;
    private static final String TAG = QuizHomeActivity.class.getSimpleName();
    private TextView timeview, quisnum, station,quizcategory,quizquestiony;
    private ImageView questionimage;
    public CountDownTimer waitTimer;
    private Button answerButton1,answerButton2,answerButton3,answerButton4,remove2op,changeq,rightbutton;
    private MediaPlayer mptimer,mpright,mpfail,music,timeover;
    private Quiz_Main_Data qmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);
        MainActivity.myinfodata = MainActivity.helperdb.getData();
        definedviewslayout();
        playSound(4);
        MyService.dayli = 300000;
        // q new
        //downtimr(time);
        setnewq();
    }
    @Override
    public void onBackPressed() {
        msgbox(Str.attr.getstr(32));
    }
    private void definedviewslayout(){
        answerButton1 = (Button)findViewById(R.id.answer_one);
        answerButton2 = (Button)findViewById(R.id.answer_two);
        answerButton3 = (Button)findViewById(R.id.answer_three);
        answerButton4 = (Button)findViewById(R.id.answer_four);
        remove2op = (Button)findViewById(R.id.help_remove2op);
        changeq = (Button)findViewById(R.id.help_changequiz);
        timeview = (TextView)findViewById(R.id.timer);
        quisnum = (TextView)findViewById(R.id.quiz_question_number);
        station = (TextView)findViewById(R.id.quiz_station);
        quizcategory = (TextView)findViewById(R.id.quiz_category);
        quizquestiony = (TextView)findViewById(R.id.quiz_questiony);
        time = Quiz_Main_Data.quiztime;
        if(Str.attr.lang > 1){
            remove2op.setText("Delete Two");
            changeq.setText("Change question");
        }
        questionimage = (ImageView)findViewById(R.id.question_with_image);
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikanserbutton(1);
            }
        });
        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikanserbutton(2);
            }
        });
        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikanserbutton(3);
            }
        });
        answerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikanserbutton(4);
            }
        });
        remove2op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sethelp(2);
            }
        });
        changeq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sethelp(1);
            }
        });
        if(Quiz_Main_Data.quizhelpchange == 1){
            changeq.setVisibility(View.GONE);
        }
        if(Quiz_Main_Data.quizhelpremoveop == 1){
            remove2op.setVisibility(View.GONE);
        }
        mptimer = MediaPlayer.create(getBaseContext(), (R.raw.timersound));
        mpright = MediaPlayer.create(getBaseContext(), (R.raw.correctanswert));
        mpfail = MediaPlayer.create(getBaseContext(), (R.raw.fail));
        music = MediaPlayer.create(getBaseContext(), (R.raw.backgroundmusic));
        timeover = MediaPlayer.create(getBaseContext(), (R.raw.timeover));
    }
    private void set_headerinfo(int st){
        quisnum.setText(Quiz_Main_Data.quiznow + "/" + Quiz_Main_Data.quiznum);
        if(st == 1){
            station.setText(Str.attr.getstr(33) + ": " + Str.attr.getstr(34));
        }
        else if(st == 2){
            station.setText(Str.attr.getstr(33) + ": " + Str.attr.getstr(35));
        }
        else if(st == 3){
            station.setText(Str.attr.getstr(33) + ": " + Str.attr.getstr(36));
        }
        else if(st == 4){
            station.setText(Str.attr.getstr(33) + ": " + Str.attr.getstr(37));
        }
    }
    private void msgbox(String msg){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.msgboxyesno);
        //dialog.setTitle("message box");
        TextView textmsg =(TextView) dialog.findViewById(R.id.textmsg);
        textmsg.setText(msg);
        dialog.show();
        Button butnsave = (Button) dialog.findViewById(R.id.yesbtn);
        Button butncancel = (Button) dialog.findViewById(R.id.nobtn);
        if(Str.attr.lang > 1){
            butnsave.setText("Yes");
            butncancel.setText("No");
        }
        butnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoptimer();
                dialog.dismiss();
                over(1);
                //QuizHomeActivity.this.finish();

            }
        });

        butncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void over(int loss){
        if(loss == 0){
            // win
            QuizResultActivity.result1 = Str.attr.getstr(38);
            QuizResultActivity.result2 = 0;
            QuizResultActivity.station = Quiz_Main_Data.quizstationnum;
            QuizResultActivity.level = 4;
            playSound(5);
            playSound(1);
            Intent startButtonIntent = new Intent(QuizHomeActivity.this, QuizResultActivity.class);
            startActivity(startButtonIntent);
            QuizHomeActivity.this.finish();
        }
        else if(loss == 1){
            // loss
            QuizResultActivity.result1 = Str.attr.getstr(39);
            int stnow = Func.getstationnow(Quiz_Main_Data.quiznum,Quiz_Main_Data.quizstationnum,Quiz_Main_Data.quiznow);
            int levelnow = Func.getlevelnow(Quiz_Main_Data.quiznum,Quiz_Main_Data.quiznow);
            if(stnow>1){
                QuizResultActivity.result2 = 0;
            }
            else {
                QuizResultActivity.result2 = 1;
            }
            QuizResultActivity.station = stnow -1;
            QuizResultActivity.level = levelnow;
            playSound(5);
            playSound(1);
            Intent startButtonIntent = new Intent(QuizHomeActivity.this, QuizResultActivity.class);
            startActivity(startButtonIntent);
            QuizHomeActivity.this.finish();
        }
        else if(loss == 2){
            // timeout
            QuizResultActivity.result1 = Str.attr.getstr(40);
            int stnow = Func.getstationnow(Quiz_Main_Data.quiznum,Quiz_Main_Data.quizstationnum,Quiz_Main_Data.quiznow);
            int levelnow = Func.getlevelnow(Quiz_Main_Data.quiznum,Quiz_Main_Data.quiznow);
            if(stnow>1){
                QuizResultActivity.result2 = 0;
            }
            else {
                QuizResultActivity.result2 = 1;
            }
            QuizResultActivity.station = stnow -1;
            QuizResultActivity.level = levelnow;
            playSound(5);
            playSound(1);
            Intent startButtonIntent = new Intent(QuizHomeActivity.this, QuizResultActivity.class);
            startActivity(startButtonIntent);
            QuizHomeActivity.this.finish();
        }
        //Intent result = new Intent(QuizHomeActivity.this, QuizResultActivity.class);
        //startActivity(result);
    }

    private void sethelp(int h){
        try{
            if(h == 2 && setchangq == 1){
                remove2op.setEnabled(false);
                remove2op.setBackgroundColor(Color.parseColor("#cccccc"));
                int[] remop = null;
                Random r = new Random();
                if(rightop == 1){
                    remop = new int[]{2,3,4};
                }
                else if(rightop == 2){
                    remop = new int[]{3,1,4};
                }
                else if(rightop == 3){
                    remop = new int[]{4,2,1};
                }
                else if(rightop == 4){
                    remop = new int[]{1,3,2};
                }
                int idx = r.nextInt(4 - 1) + 1;
                if(idx == 1){
                    removeopbutton(remop[1]);
                    removeopbutton(remop[2]);
                }
                else if(idx == 2){
                    removeopbutton(remop[0]);
                    removeopbutton(remop[2]);
                }
                else if(idx == 3){
                    removeopbutton(remop[0]);
                    removeopbutton(remop[1]);
                }
            }
            else if(h == 1 && setchangq == 1){
                //changeq.setVisibility(View.GONE);
                changeq.setEnabled(false);
                changeq.setBackgroundColor(Color.parseColor("#cccccc"));
                setnewq();
            }
        } catch (Exception e) {}

    }
    int qnowfortimer = 1;
    private void downtimr(){
        time = Quiz_Main_Data.quiztime;
        int tsec = (time + 1) * 1000;
        timeview.setText(String.valueOf(time));
        qnowfortimer = Quiz_Main_Data.quiznow;
        try {
            waitTimer = new CountDownTimer(tsec, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(time == 0){
                        playSound(1);
                        playSound(6);
                        over(2);
                        timeview.setText("00");
                    }
                    else{
                        time = time -1;
                        if(time == 6){
                            playSound(0);
                        }
                        if(time < 10){
                            timeview.setText("0" + String.valueOf(time));
                        }
                        else{
                            timeview.setText(String.valueOf(time));
                        }
                    }
                    setchangq = 1;
                }
                public void onFinish() {
                    //After 60000 milliseconds (60 sec) finish current
                    //if you would like to execute something when time finishes
                    timeview.setText("00");
                    playSound(1);
                    playSound(6);
                    if(qnowfortimer == Quiz_Main_Data.quiznow){
                        over(2);
                    }


                }
            }.start();
        }
        catch (Exception e){}
    }

    public void playSound(int n) {
        try{
            if(n == 0) {
                if(Func.Getintval("sound",MainActivity.myinfodata) == 0){
                    try {
                        mptimer = MediaPlayer.create(getBaseContext(), (R.raw.timersound));
                        mptimer.setLooping(true);
                        mptimer.start();

                    } catch (Exception e) {}
                    //mptimer = MediaPlayer.create(getBaseContext(), (R.raw.timer));
                    //mptimer.start();
                }
            }
            else if(n == 1) {
                mptimer.stop();
            }
            else if(n == 2) {
                if(Func.Getintval("sound",MainActivity.myinfodata) == 0){
                    try {
                        mpright = MediaPlayer.create(getBaseContext(), (R.raw.correctanswert));
                        mpright.start();

                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                }
            }
            else if(n == 3) {
                if(Func.Getintval("sound",MainActivity.myinfodata) == 0){
                    try {
                        mpfail = MediaPlayer.create(getBaseContext(), (R.raw.fail));
                        mpfail.start();
                    } catch (Exception e) {}
                }
            }
            else if(n ==4) {
                if(Func.Getintval("music",MainActivity.myinfodata) == 0 || Func.Getintval("music",MainActivity.myinfodata) == 2){
                    try {
                        music = MediaPlayer.create(getBaseContext(), (R.raw.backgroundmusic));
                        music.setLooping(true);
                        music.start();

                    } catch (Exception e) {}
                }
            }
            else if(n ==5) {
                music.stop();
            }
            else if(n ==6) {
                if(Func.Getintval("sound",MainActivity.myinfodata) == 0){
                    try {
                        timeover = MediaPlayer.create(getBaseContext(), (R.raw.timeover));
                        timeover.start();
                    } catch (Exception e) {}
                }
            }
        } catch (Exception e) {}
    }

    public void stoptimer(){
        //waitTimer.cancel();
        try{
            if(waitTimer != null) {
                playSound(1);
                waitTimer.cancel();
                waitTimer = null;
            }
        }catch (Exception e) {}

    }
    private int ss = 0;
    private void clikanserbutton(final int ansr){
        stoptimer();
        setchangq = 0;
        visbleopbutton(1);
        if(ansr == rightop){
            playSound(2);
        }
        else{
            playSound(3);
            setrongbuton(ansr);
        }
        try {
            CountDownTimer waitTimer;
            waitTimer = new CountDownTimer(2200, 200) {
                public void onTick(long millisUntilFinished) {
                    if(ss == 0){
                        ss = 1;
                        rightbutton.setBackgroundColor(Color.parseColor("#01b822"));
                    }
                    else {
                        ss = 0;
                        rightbutton.setBackgroundColor(Color.parseColor("#ffffbb33"));
                    }
                    setchangq = 0;
                }
                public void onFinish() {
                    rightbutton.setBackgroundColor(Color.parseColor("#01b822"));
                    //After 60000 milliseconds (60 sec) finish current
                    //if you would like to execute something when time finishes
                    checkanswer(ansr);
                }
            }.start();
        }
        catch (Exception e){}
    }

    private void checkanswer(int ansr){
        stoptimer();
        try{
            if(ansr == rightop){
                Quiz_Main_Data.quiznow = Quiz_Main_Data.quiznow + 1;
                if(Quiz_Main_Data.quiznow < Quiz_Main_Data.quiznum){
                    setnewq();
                }
                else{
                    over(0);
                }

            }
            else{
                over(1);
            }
        } catch (Exception e){}



    }

    private void setnewq(){
        try{
            int q_num = Quiz_Main_Data.quiznum;
            int st_num = Quiz_Main_Data.quizstationnum;
            int q_now = Quiz_Main_Data.quiznow;
            int foundq = 0;
            int st_numnow = Func.getstationnow(q_num,st_num,q_now);
            int levelnow = Func.getlevelnow(q_num,q_now);
            int numb = 0;
            int b_numb = 0;
            int b_rop = 1;
            int rop = 1;
            String[] q = new String[] {"","","","","",""};
            String[] b_q = new String[] {"","","","","",""};
            for (int i=0; i<Quiz_Main_Data.availbalequiznum -1 ; i++){
                int done = Quiz_Main_Data.quizArraydataList.get(i).getDone();
                if(done == 0){
                    int num = Quiz_Main_Data.quizArraydataList.get(i).getNum();
                    b_rop = Quiz_Main_Data.quizArraydataList.get(i).getRightop();
                    int level = Quiz_Main_Data.quizArraydataList.get(i).getLevel();
                    b_q[0] = Quiz_Main_Data.quizArraydataList.get(i).getQuistion();
                    b_q[1] = Quiz_Main_Data.quizArraydataList.get(i).getOption1();
                    b_q[2] = Quiz_Main_Data.quizArraydataList.get(i).getOption2();
                    b_q[3] = Quiz_Main_Data.quizArraydataList.get(i).getOption3();
                    b_q[4] = Quiz_Main_Data.quizArraydataList.get(i).getOption4();
                    Random rn = new Random();
                    int rand = rn.nextInt(4 - 1 + 1) + 1;
                    if(rand == 1){
                        // random options
                    }
                    b_q[5] = Quiz_Main_Data.quizArraydataList.get(i).getSpecialty();
                    b_numb = i;
                    if(level == levelnow){
                        q[0] = b_q[0];
                        q[1] = b_q[1];
                        q[2] = b_q[2];
                        q[3] = b_q[3];
                        q[4] = b_q[4];
                        q[5] = b_q[5];
                        rop = b_rop;
                        numb = i;
                        foundq = 1;
                    }
                }
            }
            if(foundq == 0){
                Quiz_Main_Data.quizArraydataList.get(b_numb).setDone(1);
                quizquestiony.setText(b_q[0]);
                answerButton1.setText(b_q[1]);
                answerButton2.setText(b_q[2]);
                answerButton3.setText(b_q[3]);
                answerButton4.setText(b_q[4]);
                quizcategory.setText(b_q[5]);
                rightop = b_rop;
                setrighbuton();
                set_headerinfo(st_numnow);
                visbleopbutton(0);
                stoptimer();
                downtimr();
            }
            else if(foundq == 1){
                Quiz_Main_Data.quizArraydataList.get(numb).setDone(1);
                quizquestiony.setText(q[0]);
                answerButton1.setText(q[1]);
                answerButton2.setText(q[2]);
                answerButton3.setText(q[3]);
                answerButton4.setText(q[4]);
                quizcategory.setText(q[5]);
                rightop = rop;
                setrighbuton();
                set_headerinfo(st_numnow);
                visbleopbutton(0);
                stoptimer();
                downtimr();
            }
        } catch (Exception e) {}

    }

    private void visbleopbutton(int n){
        try{
            if(n == 0){
                answerButton1.setBackgroundColor(Color.parseColor("#ffffbb33"));
                answerButton2.setBackgroundColor(Color.parseColor("#ffffbb33"));
                answerButton3.setBackgroundColor(Color.parseColor("#ffffbb33"));
                answerButton4.setBackgroundColor(Color.parseColor("#ffffbb33"));
                answerButton1.setEnabled(true);
                answerButton2.setEnabled(true);
                answerButton3.setEnabled(true);
                answerButton4.setEnabled(true);
            }
            else {
                answerButton1.setEnabled(false);
                answerButton2.setEnabled(false);
                answerButton3.setEnabled(false);
                answerButton4.setEnabled(false);
            }
        } catch (Exception e) {}



    }
    private void setrighbuton(){
        if(rightop == 1){
            rightbutton = (Button)findViewById(R.id.answer_one);
        }
        else if(rightop == 2){
            rightbutton = (Button)findViewById(R.id.answer_two);
        }
        else if(rightop == 3){
            rightbutton = (Button)findViewById(R.id.answer_three);
        }
        else if(rightop == 4){
            rightbutton = (Button)findViewById(R.id.answer_four);
        }
    }
    private void setrongbuton(int b){
        if(b == 1){
            answerButton1.setBackgroundColor(Color.parseColor("#ffff4444"));
        }
        else if(b == 2){
            answerButton2.setBackgroundColor(Color.parseColor("#ffff4444"));
        }
        else if(b == 3){
            answerButton3.setBackgroundColor(Color.parseColor("#ffff4444"));
        }
        else if(b == 4){
            answerButton4.setBackgroundColor(Color.parseColor("#ffff4444"));
        }
    }
    private void removeopbutton(int n){
        if(n == 1){
            answerButton1.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        else if(n == 2){
            answerButton2.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        else if(n == 3){
            answerButton3.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        else if(n == 4){
            answerButton4.setBackgroundColor(Color.parseColor("#cccccc"));
        }
    }

}
