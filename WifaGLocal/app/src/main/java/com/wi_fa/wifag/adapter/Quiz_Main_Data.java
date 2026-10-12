package com.wi_fa.wifag.adapter;

import java.util.List;

/**
 * Created by ahmedalalimi on 15/10/2017.
 */

public class Quiz_Main_Data {
    public static int quiztime;
    public static int quiznum;
    public static int quizstationnum;
    public static int quizhelpchange;
    public static int quizhelpremoveop;
    public static int quizwinnum;
    public static int quiztrynum;
    public static int quizwinnumday;
    public static int quiztrynumday;
    public static List<QuizArraydata> quizArraydataList;
    public static int quiznow;
    public static int availbalequiznum;

    public Quiz_Main_Data(int q_time,int q_num, int q_station, int q_now, int q_h1, int q_h2,
                          int q_wn, int q_wd, int q_tn, int q_td){
        quiztime = q_time;
        quiznum = q_num;
        quizstationnum = q_station;
        quiznow = q_now;
        quizhelpchange = q_h1;
        quizhelpremoveop = q_h2;
        quizwinnum = q_wn;
        quizwinnumday = q_wd;
        quiztrynum = q_tn;
        quiztrynumday = q_td;

    }
    public void setlistdata(List<QuizArraydata> list){
        quizArraydataList = list;
    }
}
