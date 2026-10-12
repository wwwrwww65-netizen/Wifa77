package com.wi_fa.wifag;

import java.util.List;

/**
 * Created by ahmedalalimi on 23/10/2017.
 */

public class Func {
    public static String wifidatainofupdate = "none";
    public static int uponce = 0;
    public static int doneupdtinfo = 0;
    public static String loginpass = "";
    public static String loginuser = "";
    public static List<V09Array> v09ArrayList;
    public static String ads_bottom = "none";
    public static int reconnected = 0;
    public static int adfsedse = 0;
    public static String Getval(String attr, String str){
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
        if(val.contains("=")){
            val = "";
        }
        return val;
    }
    public static String Getvalca(String attr, String str){
        if (!str.contains(attr))
        {
            return "";
        }
        String val = str.substring(str.indexOf(attr));
        val = val.substring(val.indexOf("=") + 1);
        int e = val.indexOf("-----END CERTIFICATE-----");
        if (e > 0)
        {
            val = val.substring(0, e);
        }
        if (val.contains("-----BEGIN CERTIFICATE-----"))
        {
            val = val.replace("-----BEGIN CERTIFICATE-----", "");
        }
        return val;
    }
    public static String remvoespace(String str){
        String val = str;
        if (str.contains(" "))
        {
            val = val.replace(" ", "");
        }
        return val;
    }

    public static String domainrad(String user, String pass){
        String val = user;
        if (user.contains("@wifa")) {
            if (user.contains("@wifa-man")) {
                val = user.replace("@wifa-man", "");
            } else {
                val = user.replace("@wifa", "") + pass;
            }
        }
        return val;
    }

    public static int Getintval(String attr, String str){
        if (!str.contains(attr))
        {
            return -1;
        }
        String val = str.substring(str.indexOf(attr));
        val = val.substring(val.indexOf("=") + 1);
        int e = val.indexOf("=");
        if (e > 0)
        {
            val = val.substring(0, e);
        }
        return Integer.parseInt(val);
    }

    public static double Getdoubleval(String attr, String str){
        if (!str.contains(attr))
        {
            return -1;
        }
        String val = str.substring(str.indexOf(attr));
        val = val.substring(val.indexOf("=") + 1);
        int e = val.indexOf("=");
        if (e > 0)
        {
            val = val.substring(0, e);
        }
        return Double.parseDouble(val);
    }

    public static String gettimeFromsec(int sec) {
        int uren, minuten, seconden, ingave;
        ingave = sec;
        uren = ingave / 3600;
        minuten = (ingave - uren * 3600) / 60;
        seconden = ingave - uren * 3600 - minuten * 60;
        String nU1 = (uren < 10) ? "0" : "";
        String nM1 = (minuten < 10) ? "0" : "";
        String nS1 = (seconden < 10) ? "0" : "";
        String lll = nU1 + uren + ":" + nM1 + minuten + ":" + nS1 + seconden;
        if(lll.contains("00:00:00")){
            lll = "unlimited";
        }
        return lll;
    }

    public static int[] getcontestinfo(){
        int[] strings = null;
        String giftcontest = Getval("contest", wifidatainofupdate);
        if(giftcontest.contains("enable")){
            int quiznum = Getintval("qnum", wifidatainofupdate);
            int quiznow = 1;
            int quiztime = Getintval("answertime", wifidatainofupdate);
            int quizstationnum = Getintval("stationnum", wifidatainofupdate);
            int quizhelpchange = Getintval("qchange", wifidatainofupdate);
            int quizhelpremoveop = Getintval("remove2o", Func.wifidatainofupdate);
            int quizwinnum = Getintval("winnum", wifidatainofupdate);
            int quiztrynum = Getintval("trynum", wifidatainofupdate);
            int quizwinnumday = Getintval("winday", wifidatainofupdate);
            int quiztrynumday = Getintval("tryday", wifidatainofupdate);
            int numonestation = (quiznum / quizstationnum);
            strings = new int[]{quiztime,quiznum,quizstationnum,numonestation,quiznow,quizhelpchange,quizhelpremoveop,quizwinnum,quizwinnumday,quiztrynum,quiztrynumday};
        }

        return strings;
    }

    public static int getlevelnow(int qnum, int qnow){
        int level = 0;
        if(qnum < 6){
            level = qnow;
            if(qnow==qnum){
                level = 4;
            }
        }
        else if(qnum > 5 && qnum < 9){
            level = qnow-1;
            if(qnow>3 && qnow < 6){ level = 2; }
            else if(qnow>5){
                level = 3;
                if(qnow==qnum){ level = 4; }
            }
        }
        else if(qnum > 9 && qnum < 15){
            if(qnow<3){ level = 0; }
            else if(qnow>2 && qnow < 5){ level = 1; }
            else if(qnow>4 && qnow < 8){ level = 2; }
            else if(qnow>7 && qnow < 11){ level = 3; }
            else{ level = 4; }
            if(qnow==qnum){ level = 4; }
        }
        else if(qnum > 14 && qnum < 17){
            if(qnow<3){ level = 0; }
            else if(qnow>2 && qnow < 6){ level = 1; }
            else if(qnow>5 && qnow < 10){ level = 2; }
            else if(qnow>9 && qnow < 13){ level = 3; }
            else{ level = 4; }
            if(qnow==qnum){ level = 4; }
        }
        else{
            if(qnow<3){ level = 0; }
            else if(qnow>2 && qnow < 7){ level = 1; }
            else if(qnow>6 && qnow < 13){ level = 2; }
            else if(qnow>12 && qnow < 16){ level = 3; }
            else{ level = 4; }
            if(qnow==qnum){ level = 4; }
        }
        return level;
    }

    public static int getstationnow(int qnum, int stnum, int qnow){
        int st = 1;
        int numqst = (qnum/stnum);
        for (int i=0; i<stnum; i++){
            int ll = i * numqst;
            if(qnow > ll){
                st = i + 1;
            }
        }
        return st;
    }

    public static void setV09ArrayListlistdata(List<V09Array> list){
        v09ArrayList = list;
    }

    public static String errorarabic(String error){
        String str = error;
        if(Str.attr.lang < 2){
            if (error == "simultaneous session limit reached") {
                str = "عذراً .. هذا الحساب مستخدم حالياً على جهاز آخر";
            }
            else if (error.contains("invalid password")) {
                str = "خطأ! حاول مرة أخرى";
            }
            else if (error.contains("uptime limit reached")) {
                str = "عذراً لقد انتهى الوقت المتاح لك..";
            }
            else if (error.contains("Expired prepaid card")) {
                str = "لقد انتهت صلاحية هذا الكرت";
            }
            else if (error.contains("No more online time left")) {
                str = "عذراً لقد انتهى الوقت المتاح لك..";
            }
            else if (error.contains("You are already logged in - access denied")) {
                str = "هذا الحساب مستخدم على جهاز اخر حالياً";
            }
            else if (error.contains("traffic limit reached")) {
                str = "لقد انتهى رصيد تحميل هذا الحساب..";
            }
            else if (error.contains("invalid username or password")) {
                str = "خطأ في رمز الدخول";
            }
            else if (error.contains("Invalid MAC address!")) {
                str = "هذا الكرت خاص بجهاز آخر";
            }
            else if (error.contains("traffic limit")) {
                str = "لقد انتهى رصيد تحميل هذا الحساب";
            }
            else if (error.contains("limit reached")) {
                str = "لقد انتهى رصيد تحميل هذا الحساب";
            }
            else if (error == "no valid profile found") {
                str = "لقد انتهت صلاحية هذا الحساب .";
            }
            else if(error.contains("uptime limit")) {
                str = "لقد انتهى رصيد هذا الحساب";
            }
            else if(error.contains("not found")) {
                str = "رمز الدخول غير صحيح ، الرجاء المحاولة مرة اخرى";
            }
            else if(error.contains("not allowed to log in from this MAC address")) {
                str = "هذا الحساب خاص بجهاز آخر .. لا تستطيع الدخول به من جهازك";
            }
            else if(error.contains("no more sessions are allowe")) {
                str ="هذا الحساب موجود حالياً .. ولا يسمح بالدخول بنفس الحساب من جهازين مختلفين";
            }
            else if(error.contains("responding")) {
                str = "قد يكون هناك مشكلة في مزود الخدمة الإنترنت ، يرجى المحاولة لاحقاً";
            }
            else if (error == "invalid Calling-Station-Id") {
                str = "هذا الحساب مقترن بجهاز آخر، لمزيد من المعلومات يرجى الاتصال بخدمة العملاء";
            }
        }
        return  str;
    }
}
