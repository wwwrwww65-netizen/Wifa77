package com.wi_fa.wifag.recylcerchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wi_fa.wifag.R;

/**
 * Created by ahmedalimi.
 */

public class HolderDate extends RecyclerView.ViewHolder {

    private TextView date;

    public HolderDate(View v) {
        super(v);
        date = (TextView) v.findViewById(R.id.tv_date);
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
