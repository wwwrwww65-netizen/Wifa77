package com.wi_fa.wifag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ahmedalalimi on 10/03/2018.
 */
public class DbAccessp {
    myDbHelper myhelper;
    public DbAccessp(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    public long insert(String code, String link, String ssid, String mac) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CodeU, code);
        contentValues.put(myDbHelper.Link, link);
        contentValues.put(myDbHelper.SSID, ssid);
        contentValues.put(myDbHelper.MacAddr, mac);

        long id = dbb.insert(myDbHelper.TABLE_access, null , contentValues);
        return id;
    }
    public String[] getcodebyssid(String ssid, String mac) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.CodeU,myDbHelper.Link};
        Cursor cursor =db.query(myDbHelper.TABLE_access,columns,myDbHelper.SSID + "=? AND "+myDbHelper.MacAddr + "=?",new String[] { ssid,mac },null,null,null,null);
        String[] buffer = {"notfound","nolink"};
        if(cursor.getCount()>0){
            while (cursor.moveToNext())
            {
                buffer[0] = cursor.getString(0);
                buffer[1] = cursor.getString(1);
            }
        }
        return buffer;
    }
    public int delete() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={"0"};

        int count =db.delete(myDbHelper.TABLE_access ,myDbHelper.UID+" >= ?",whereArgs);
        return  count;
    }
    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MaxUpgradeAPDb";    // Database Name
        private static final String TABLE_access = "AccesspointTable";
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String CodeU = "CodeNN";
        private static final String Link = "LINK";
        private static final String SSID = "SSID";
        private static final String MacAddr = "MacAddr";



        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_access+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CodeU+" TEXT ,"+Link+" TEXT ,"+SSID+" TEXT ,"+MacAddr+" VARCHAR(17));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_access;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                //db.execSQL(DROP_TABLE);
            } catch (Exception e) {
                //Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
}
