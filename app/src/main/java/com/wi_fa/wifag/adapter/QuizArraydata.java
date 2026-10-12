package com.wi_fa.wifag.adapter;

/**
 * Created by ahmedalalimi on 15/10/2017.
 */

public class QuizArraydata {
    private String quistion,option1,option2,option3,option4,specialty;
    private int num,rightop,done,level;

    public String getQuistion(){
        return quistion;
    }
    public void setQuistion(String quis){
        quistion = quis;
    }

    public String getOption1(){
        return option1;
    }
    public void setOption1(String op1){
        option1 = op1;
    }

    public String getOption2(){
        return option2;
    }
    public void setOption2(String op2){
        option2 = op2;
    }

    public String getOption3(){
        return option3;
    }
    public void setOption3(String op3){
        option3 = op3;
    }

    public String getOption4(){
        return option4;
    }
    public void setOption4(String op4){
        option4 = op4;
    }

    public String getSpecialty(){
        return specialty;
    }
    public void setSpecialty(String spec){
        specialty = spec;
    }

    public int getNum(){
        return num;
    }
    public void setNum(int nm){
        num = nm;
    }

    public int getRightop(){
        return rightop;
    }
    public void setRightop(int ritop){
        rightop = ritop;
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int level1){
        level = level1;
    }

    public int getDone(){
        return done;
    }
    public void setDone(int donee){
        done = donee;
    }
}
