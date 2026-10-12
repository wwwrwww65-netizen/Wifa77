package com.wi_fa.wifag.recylcerchat;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wi_fa.wifag.R;

/**
 * Created by ahmedalimi.
 */

public class HolderYou extends RecyclerView.ViewHolder {

    private TextView time, chatText;
    private ImageView image;

    public HolderYou(View v) {
        super(v);
        time = (TextView) v.findViewById(R.id.tv_time);
        chatText = (TextView) v.findViewById(R.id.tv_chat_text);
        image = (ImageView) v.findViewById(R.id.tv_image);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView img) {
        this.image = img;
    }

    public TextView getChatText() {
        return chatText;
    }

    public void setChatText(TextView chatText) {
        this.chatText = chatText;
    }
}
