package com.wi_fa.wifag.maxupmenu;

/**
 * Created by ahmedalalimi on 13/09/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wi_fa.wifag.Conversation;
import com.wi_fa.wifag.Func;
import com.wi_fa.wifag.MainActivity;
import com.wi_fa.wifag.MyService;
import com.wi_fa.wifag.R;
import com.wi_fa.wifag.Str;
import com.wi_fa.wifag.adapter.Chat;
import com.wi_fa.wifag.adapter.ChatAdapter;
import com.wi_fa.wifag.myDbAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class maxupmsg extends Fragment implements ChatAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    myDbAdapter helperdb;
    long networkdbcount = 0;
    View view;
    public maxupmsg() { setHasOptionsMenu(true); }
    public void onCreate(Bundle a){
        super.onCreate(a);
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maxupmenu_chat, null, false);
        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, Str.attr.getstr(2));
        MyService.dayli = 30000;
        helperdb = new myDbAdapter(getActivity());
        networkdbcount = helperdb.getrowCount(2);

        try{
            tv_selection = (TextView) view.findViewById(R.id.tv_selection);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new ChatAdapter(getContext(),setData(),this);
            mRecyclerView.setAdapter (mAdapter);
        }catch (Exception e) {}


        return view;
    }

    public List<Chat> setData(){
        List<Chat> data = new ArrayList<>();
        for (int i = 0; i<networkdbcount; i++){
            Chat chat = new Chat();
            int id = i;
            int num = helperdb.getidbynum(i);
            String codeN = helperdb.getnameimg(num)[2];
            int n = helperdb.getmsgnotwatchnum(codeN);
            String lastdatamsg = helperdb.getlastmsg(codeN);
            String time = "";
            String lastmsg = "";
            String datetime = "";
            if(lastdatamsg != "none"){
                String tim = Func.Getval("datem",lastdatamsg);
                datetime = tim;
                lastmsg = Func.Getval("msg",lastdatamsg);
                String[] mmssgg = lastmsg.split(" ");
                if(mmssgg.length > 6){
                    lastmsg = mmssgg[0] + " " + mmssgg[1] + " " + mmssgg[3] + " " + mmssgg[4] + " " + mmssgg[5] + " ...";
                }
                String[] dtt2 = tim.split(" ");
                String[] ttim = dtt2[1].split(":");
                int h = Integer.parseInt(ttim[0]);

                if(h>12){
                    h = h - 12;
                    String hh = "";
                    if(h < 10){
                        hh = "0" + String.valueOf(h);
                    }
                    time = "م" + hh + ":" + ttim[1] + ":" +ttim[2];
                    if(Str.attr.lang > 1){
                        time = hh + ":" + ttim[1] + ":" +ttim[2] + "pm";
                    }
                }
                else {
                    time =  "ص" + dtt2[1];
                    if(Str.attr.lang > 1){
                        time =  dtt2[1] + "am";
                    }
                }
            }
            String datenow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String sd = DateUtils.getRelativeTimeSpanString(timeStringtoMilis(datetime), timeStringtoMilis(datenow),DateUtils.MINUTE_IN_MILLIS).toString();
            chat.setmTime(sd);
            chat.setName(helperdb.getnameimg(num)[0]);
            String imgg = helperdb.getnameimg(num)[1];
            chat.setImage(imgg);
            chat.setMsgnum(String.valueOf(n));
            chat.setOnline(true);
            chat.setLastChat(lastmsg);
            data.add(chat);
        }
        return data;
    }

    @Override
    public void onItemClicked (int position) {
        int num = helperdb.getidbynum(position);
        Conversation.id = num;
        Conversation.finsh = 0;
        startActivity(new Intent(getActivity(), Conversation.class));
    }

    @Override
    public boolean onItemLongClicked (int position) {
        toggleSelection(position);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
        if (mAdapter.getSelectedItemCount()>0){
            tv_selection.setVisibility(View.VISIBLE);
        }else
            tv_selection.setVisibility(View.GONE);


        getActivity().runOnUiThread(new Runnable() {
            public void run()
            {
                tv_selection.setText("Delete ("+mAdapter.getSelectedItemCount()+")");
            }
        });

    }

    private long timeStringtoMilis(String time) {
        long milis = 0;

        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date 	= sd.parse(time);
            milis 		= date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return milis;
    }




}
