package com.wi_fa.wifag.adapter;

/**
 * Created by ahmedalalimi on 07/10/2017.
 */

public class Chat {

    private String mName;
    private String mLastChat;
    private String mTime;
    private String mImage;
    private String mMsgnum;
    private boolean online;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastChat() {
        return mLastChat;
    }

    public void setLastChat(String lastChat) {
        mLastChat = lastChat;
    }

    public String getTime() {
        return mTime;
    }

    public void setmTime(String time) {
        mTime = time;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
    public String getMsgnum() {
        return mMsgnum;
    }

    public void setMsgnum(String msgnum) {
        mMsgnum = msgnum;
    }


    public boolean getOnline(){
        return online;
    }

    public void setOnline(boolean on){
        online = on;
    }
}
