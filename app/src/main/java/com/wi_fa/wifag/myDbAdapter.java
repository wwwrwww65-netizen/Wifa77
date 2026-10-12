package com.wi_fa.wifag;

/**
 * Created by ahmedalalimi on 18/09/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }


    public long insertmyinfo(String name, String phone, int code, String hostn, String mac, int sound, int music, String link, String lastupdat) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.Phone, phone);
        contentValues.put(myDbHelper.CodeU, code);
        contentValues.put(myDbHelper.HostNAME, hostn);
        contentValues.put(myDbHelper.MacAddr, mac);
        contentValues.put(myDbHelper.Sound, sound);
        contentValues.put(myDbHelper.Music, music);
        contentValues.put(myDbHelper.Link, link);
        contentValues.put(myDbHelper.lastupdate, lastupdat);
        long id = dbb.insert(myDbHelper.TABLE_myinfo, null , contentValues);
        return id;
    }

    public long insertnewnetwork(String name, String phone, String code, String img, String imgurl, int rad, int servtype, int logntyp, String user, String pass, int vipuser, String offer, int offertype, int cookie, String lastupdate, int statapp, String ipserver, String serverlink) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.Phone, phone);
        contentValues.put(myDbHelper.CodeN, code);
        contentValues.put(myDbHelper.IPServer, ipserver);
        contentValues.put(myDbHelper.ImageN, img);
        contentValues.put(myDbHelper.Imageurl, imgurl);
        contentValues.put(myDbHelper.Rad, rad);
        contentValues.put(myDbHelper.ServType, servtype);
        contentValues.put(myDbHelper.LoginType, logntyp);
        contentValues.put(myDbHelper.UserN, user);
        contentValues.put(myDbHelper.PassW, pass);
        contentValues.put(myDbHelper.VipUser, vipuser);
        contentValues.put(myDbHelper.Offer, offer);
        contentValues.put(myDbHelper.Offertype, offertype);
        contentValues.put(myDbHelper.Cookielogin, cookie);
        contentValues.put(myDbHelper.LastUpdate,lastupdate);
        contentValues.put(myDbHelper.StatApp, statapp);
        contentValues.put(myDbHelper.ServerLink, serverlink);
        long id = db.insert(myDbHelper.TABLE_networks, null , contentValues);
        return id;
    }

    public long insertmsgdata(String code, String msgg, int msgtype, String date, int sendr, int done, int watched) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.wificode, code);
        contentValues.put(myDbHelper.msg, msgg);
        contentValues.put(myDbHelper.msgtype, msgtype);
        contentValues.put(myDbHelper.datem, date);
        contentValues.put(myDbHelper.sender, sendr);
        contentValues.put(myDbHelper.done, done);
        contentValues.put(myDbHelper.watch, watched);
        long id = dbb.insert(myDbHelper.TABLE_msg, null , contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.Phone,myDbHelper.CodeU,myDbHelper.HostNAME,myDbHelper.MacAddr,myDbHelper.Sound,myDbHelper.Music,myDbHelper.Link,myDbHelper.lastupdate};
        Cursor cursor =db.query(myDbHelper.TABLE_myinfo,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            buffer.append("=id=" + cursor.getInt(0)+ "=name=" + cursor.getString(1) + "=phone=" + cursor.getString(2) +
                    "=code=" + cursor.getInt(3) + "=hostname=" + cursor.getString(4) +"=mac=" + cursor.getString(5) +
                    "=sound=" + cursor.getInt(6) + "=music=" + cursor.getInt(7) +"=link=" + cursor.getString(8) +
                    "=lastupdate=" + cursor.getString(9));
        }
        return buffer.toString();
    }

    public String[] getnetworkData(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME, myDbHelper.Phone,myDbHelper.ImageN,myDbHelper.Imageurl, myDbHelper.Rad, myDbHelper.ServType, myDbHelper.LoginType, myDbHelper.UserN, myDbHelper.PassW, myDbHelper.VipUser, myDbHelper.Offer, myDbHelper.Offertype, myDbHelper.Cookielogin, myDbHelper.LastUpdate, myDbHelper.StatApp,myDbHelper.IPServer,myDbHelper.ServerLink};
        Cursor cursor =db.query(myDbHelper.TABLE_networks,columns,myDbHelper.CodeN + "=?",new String[] { code },null,null,null,null);
        String[] buffer = {"","","","","","","","","","","","","","","","","",""};
        if (cursor != null) {
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                buffer[0] = cursor.getString(0);
                buffer[1] = cursor.getString(1);
                buffer[2] = cursor.getString(2);
                buffer[3] = cursor.getString(3);
                buffer[4] = cursor.getString(4);
                buffer[5] = cursor.getString(5);
                buffer[6] = cursor.getString(6);
                buffer[7] = cursor.getString(7);
                buffer[8] = cursor.getString(8);
                buffer[9] = cursor.getString(9);
                buffer[10] = cursor.getString(10);
                buffer[11] = cursor.getString(11);
                buffer[12] = cursor.getString(12);
                buffer[13] = cursor.getString(13);
                buffer[14] = cursor.getString(14);
                buffer[15] = cursor.getString(15);
                buffer[16] = cursor.getString(16);
                buffer[17] = cursor.getString(17);
            }
        }
        return buffer;
    }

    public String getDatamsg(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.msg,myDbHelper.msgtype,myDbHelper.datem,myDbHelper.sender,myDbHelper.done,myDbHelper.watch};
        Cursor cursor =db.query(myDbHelper.TABLE_msg,columns,myDbHelper.wificode + "=?",new String[] { code },null,null,myDbHelper.datem+" ASC",null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.getCount()>0){
            while (cursor.moveToNext())
            {
                buffer.append("=id=" + cursor.getInt(0)+ "=msg=" + cursor.getString(1) + "=msgtype=" + cursor.getInt(2) +
                        "=datem=" + cursor.getString(3) + "=sender=" + cursor.getString(4) +"=done=" + cursor.getString(5) +
                        "=watch=" + cursor.getInt(6) + "=end=endline");
            }
        }
        return buffer.toString();
    }
    public String getvpnstate() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.Link};
        Cursor cursor =db.query(myDbHelper.TABLE_myinfo,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            buffer.append(cursor.getString(0));
        }
        return buffer.toString();
    }
    public String getDatamsgby_s(String code, String sendr) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.msg,myDbHelper.msgtype,myDbHelper.datem,myDbHelper.sender,myDbHelper.done,myDbHelper.watch};
        Cursor cursor =db.query(myDbHelper.TABLE_msg,columns,myDbHelper.wificode + "=? AND "+myDbHelper.sender + "=? AND "+myDbHelper.watch + "=?",new String[] { code,sendr,"1" },null,null,myDbHelper.datem+" ASC",null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.getCount()>0){
            while (cursor.moveToNext())
            {
                buffer.append("=id=" + cursor.getInt(0)+ "=msg=" + cursor.getString(1) + "=msgtype=" + cursor.getInt(2) +
                        "=datem=" + cursor.getString(3) + "=sender=" + cursor.getString(4) +"=done=" + cursor.getString(5) +
                        "=watch=" + cursor.getInt(6) + "=end=endline");
            }
        }
        return buffer.toString();
    }

    public String getlastmsg(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.msg,myDbHelper.msgtype,myDbHelper.datem,myDbHelper.sender,myDbHelper.done,myDbHelper.watch};
        Cursor cursor =db.query(myDbHelper.TABLE_msg,columns,myDbHelper.wificode + "=?",new String[] { code },null,null,myDbHelper.UID+" DESC","1");
        String buffer = "none";
        if(cursor.getCount()>0){
            while (cursor.moveToNext())
            {
                buffer = "=id=" + cursor.getInt(0)+ "=msg=" + cursor.getString(1) + "=msgtype=" + cursor.getInt(2) +
                        "=datem=" + cursor.getString(3) + "=sender=" + cursor.getString(4) +"=done=" + cursor.getString(5) +
                        "=watch=" + cursor.getInt(6) + "=end=endline";
            }
        }
        return buffer;
    }
    public String getmsgnotsent() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.wificode,myDbHelper.msg,myDbHelper.msgtype,myDbHelper.datem,myDbHelper.done};
        Cursor cursor =db.query(myDbHelper.TABLE_msg,columns,myDbHelper.done + "=?",new String[] { "1" },null,null,null,null);
        String buffer = "none";
        if(cursor.getCount()>0){
            buffer = "";
            while (cursor.moveToNext())
            {
                buffer = buffer + "=id=" + cursor.getString(0)+ "=code=" + cursor.getString(1)+ "=mssg=" + cursor.getString(2) + "=msgtype=" + cursor.getInt(3) +
                        "=datem=" + cursor.getString(4) + "=line=endline";
            }
        }
        return buffer;
    }

    public int getmsgnotwatchnum(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID};
        String sendr = "1";
        Cursor cursor =db.query(myDbHelper.TABLE_msg,columns,myDbHelper.wificode + "=? AND "+myDbHelper.sender + "=? AND "+myDbHelper.watch + "=? ",new String[] { code,sendr,"1" },null,null,null,null);
        return cursor.getCount();
    }

    public int iscodefound(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME};
        Cursor cursor =db.query(myDbHelper.TABLE_networks,columns,myDbHelper.CodeN + "=?",new String[] { code },null,null,null,null);
        int n = cursor.getCount();
        return n;
    }

    public int getidbynum(int num) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID};
        Cursor cursor = db.query(myDbHelper.TABLE_networks, columns, null, null, null, null, null);
        int n = 0;
        int a = 0;
        if (cursor != null) {
            if(cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    if(a == num){
                        n = cursor.getInt(0);
                        return n;
                    }
                    else{
                        a = a + 1;
                    }
                }
            }
        }
        return n;
    }

    public String getlastmsgid() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID};
        String sendr = "2";
        Cursor cursor = db.query(myDbHelper.TABLE_msg, columns, myDbHelper.sender + "=?",new String[] { sendr }, null, null, myDbHelper.UID+" DESC", "1");
        String n = "0";
        if (cursor != null) {
            if(cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    n = cursor.getString(0);
                }
            }
        }
        return n;
    }

    public String[] getnameandphon() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME,myDbHelper.Phone};
        Cursor cursor =db.query(myDbHelper.TABLE_myinfo,columns,null,null,null,null,null);
        String[] buffer = {"name", "phone"};
        while (cursor.moveToNext())
        {
            buffer[0] =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            buffer[1] =cursor.getString(cursor.getColumnIndex(myDbHelper.Phone));
        }
        return buffer;
    }

    public long getrowCount(int table) {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        long cnt = 0;
        if(table == 1) {
            cnt = DatabaseUtils.queryNumEntries(db, myDbHelper.TABLE_myinfo);
        } else if(table == 2) {
            cnt = DatabaseUtils.queryNumEntries(db, myDbHelper.TABLE_networks);
        } else if(table == 3) {
            cnt = DatabaseUtils.queryNumEntries(db, myDbHelper.TABLE_msg);
        }
        return cnt;
    }
    public int getlang() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        int lang = 0;
        String[] columns = {myDbHelper.Music};
        Cursor cursor =db.query(myDbHelper.TABLE_myinfo,columns,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            lang = cursor.getInt(0);
        }
        return lang;
    }
    public String[] getnamephonimg(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME, myDbHelper.Phone,myDbHelper.ImageN,myDbHelper.Imageurl};
        Cursor cursor =db.query(myDbHelper.TABLE_networks,columns,myDbHelper.CodeN + "=?",new String[] { code },null,null,null,null);
        String[] buffer = {"none","none","none","none"};
        if (cursor != null) {
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                buffer[0] = cursor.getString(0);
                buffer[1] = cursor.getString(1);
                buffer[2] = cursor.getString(2);
                buffer[3] = cursor.getString(3);
            }
        }
        return buffer;
    }
    public String[] getnameimg(int id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME, myDbHelper.ImageN,myDbHelper.CodeN};
        Cursor cursor =db.query(myDbHelper.TABLE_networks,columns,myDbHelper.UID + "=?",new String[] { String.valueOf(id) },null,null,null,null);
        String[] buffer = {"none","none","none"};
        if (cursor != null) {
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                buffer[0] = cursor.getString(0);
                buffer[1] = cursor.getString(1);
                buffer[2] = cursor.getString(2);
            }
        }
        return buffer;
    }
    public String[] getoffer(String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.Offer, myDbHelper.Offertype};
        Cursor cursor =db.query(myDbHelper.TABLE_networks,columns,myDbHelper.CodeN + "=?",new String[] { code },null,null,null,null);
        String[] buffer = {"none","none"};
        if (cursor != null) {
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                buffer[0] = cursor.getString(0);
                buffer[1] = cursor.getString(1);
            }
        }
        return buffer;
    }
    public int updateoffer(String code , int station) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(station > 0){
            contentValues.put(myDbHelper.Offertype,station);
            contentValues.put(myDbHelper.Offer,"contestwifa");
        }
        else{
            contentValues.put(myDbHelper.Offertype,0);
            contentValues.put(myDbHelper.Offer,"none");
        }
        String[] whereArgs= {code};
        int count = db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }

    public int delete(String uname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_networks ,myDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateNameandphon(String name , String phone) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,name);
        contentValues.put(myDbHelper.Phone,phone);
        //contentValues.put(myDbHelper.HostNAME,hostname);
        //contentValues.put(myDbHelper.MacAddr,mac);
        String[] whereArgs= {"1"};
        int count =db.update(myDbHelper.TABLE_myinfo,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }
    public int updatevpnstatus(String info) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.Link,info);
        String[] whereArgs= {"1"};
        int count =db.update(myDbHelper.TABLE_myinfo,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }
    public int updateinfowifi(String code, String name, String phone, int rad, String serverip, int servtype, int logntyp, String offer, int offertype, String lastupdate, int statapp ) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.Phone, phone);
        contentValues.put(myDbHelper.Rad, rad);
        contentValues.put(myDbHelper.IPServer, serverip);
        contentValues.put(myDbHelper.ServType, servtype);
        contentValues.put(myDbHelper.LoginType, logntyp);
        contentValues.put(myDbHelper.Offer, offer);
        contentValues.put(myDbHelper.Offertype, offertype);
        contentValues.put(myDbHelper.LastUpdate,lastupdate);
        contentValues.put(myDbHelper.StatApp, statapp);
        String[] whereArgs= {code};
        int count =db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }
    public int updateimgwifi(String code, String img, String imgurl) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ImageN,img);
        contentValues.put(myDbHelper.Imageurl,imgurl);
        String[] whereArgs= {code};
        int count =db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }

    public int updatecookielogin(String code, int cookie) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.Cookielogin,cookie);
        String[] whereArgs= {code};
        int count =db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }
    public int updatevipuser(String code, int vip) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.VipUser,vip);
        String[] whereArgs= {code};
        int count =db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }

    public int updateuserandpass(String user , String pass, String code) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.UserN,user);
        contentValues.put(myDbHelper.PassW,pass);
        String[] whereArgs= {code};
        int count =db.update(myDbHelper.TABLE_networks,contentValues, myDbHelper.CodeN+" = ?",whereArgs );
        return count;
    }

    public int updatesound(int sound) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.Sound,sound);
        String[] whereArgs= {"1"};
        int count =db.update(myDbHelper.TABLE_myinfo,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public int updatewtchmsg(String code,int sender) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.watch,0);
        String[] whereArgs= {code,"1", String.valueOf(sender)};
        int count =db.update(myDbHelper.TABLE_msg,contentValues, myDbHelper.wificode+" = ? AND "+myDbHelper.watch+" = ? AND "+myDbHelper.sender+" = ?",whereArgs );
        return count;
    }
    public int updatewtchmsgall() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.watch,0);
        String[] whereArgs= {"1","2"};
        int count =db.update(myDbHelper.TABLE_msg,contentValues,myDbHelper.watch+" = ? AND "+myDbHelper.sender+" = ?",whereArgs );
        return count;
    }
    public int updatedonemsg(String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.done,0);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_msg,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public int updatemusic(int music) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.Music,music);
        String[] whereArgs= {"1"};
        int count =db.update(myDbHelper.TABLE_myinfo,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MaxUpgradeDb";    // Database Name
        private static final String TABLE_myinfo = "myinfoTable";
        private static final String TABLE_networks = "wifiTable"; // Table Name
        private static final String TABLE_msg = "msgTable";
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";
        private static final String Phone = "Phone";
        private static final String CodeU = "CodeU";
        private static final String HostNAME = "HostN";
        private static final String MacAddr = "MacAddr";
        private static final String Sound = "Sound";
        private static final String Music = "Music";
        private static final String Link = "mainlink";
        private static final String lastupdate = "lastupdate";
        // networks table
        private static final String CodeN = "CodeN";
        private static final String IPServer = "IPServer";
        private static final String ImageN = "ImageN";
        private static final String Imageurl = "Imageurl";
        private static final String Rad = "Rad";
        private static final String ServType = "ServType";
        private static final String LoginType = "LoginType";
        private static final String UserN = "UserN";
        private static final String PassW = "PassW";
        private static final String VipUser = "VipUser";
        private static final String Offer = "Offer";
        private static final String Offertype = "Offertype";
        private static final String Cookielogin = "Cookielogin";
        private static final String LastUpdate = "LastUpdate";
        private static final String StatApp = "StatApp";
        private static final String ServerLink = "serverLink";
        // msg table
        private static final String wificode = "Code";
        private static final String msg = "msg";
        private static final String msgtype = "msgtype";
        private static final String datem = "Datim";
        private static final String sender = "Sender";
        private static final String done= "Done";
        private static final String watch= "Watch";

        private static final String CREATE_TABLE1 = "CREATE TABLE "+TABLE_myinfo+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(100) ,"+Phone+" VARCHAR(100) ,"+CodeU+" INTEGER ,"+HostNAME+" VARCHAR(100) ,"+MacAddr+" VARCHAR(17) ,"+Sound+" INTEGER ,"+Music+" INTEGER ,"+Link+" VARCHAR(200) ,"+lastupdate+"  VARCHAR(10));";
        private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_networks+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CodeN+" VARCHAR(100) ,"+IPServer+" VARCHAR(15) ,"+NAME+" VARCHAR(100) ,"+Phone+" TEXT ,"+ImageN+" TEXT ,"+Imageurl+" TEXT ,"+Rad+" INTEGER ,"+ServType+" INTEGER ,"+LoginType+" INTEGER ,"+UserN+" VARCHAR(50) ,"+PassW+" VARCHAR(50) ,"+VipUser+" INTEGER ,"+Offer+" TEXT ,"+Offertype+" INTEGER ,"+Cookielogin+" INTEGER ,"+LastUpdate+" VARCHAR(20) ,"+ StatApp+" INTEGER ,"+ ServerLink+" TEXT);";
        private static final String CREATE_TABLE3 = "CREATE TABLE "+TABLE_msg+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+wificode+" VARCHAR(100) ,"+msg+" TEXT ,"+msgtype+" INTEGER ,"+datem+" VARCHAR(100) ,"+sender+" INTEGER ,"+done+" INTEGER ,"+ watch+" INTEGER);";
        private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS "+TABLE_myinfo;
        private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS "+TABLE_networks;
        private static final String DROP_TABLE3 ="DROP TABLE IF EXISTS "+TABLE_msg;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE1);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
                //db.execSQL(DROP_TABLE);
            } catch (Exception e) {
                //Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
}
