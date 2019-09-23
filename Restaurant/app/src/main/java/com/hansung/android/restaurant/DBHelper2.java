package com.hansung.android.restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//===========================메뉴정보를 저장하는 데이터베이스==================================
public class DBHelper2 extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper2(Context context) {
        super(context, UserContract2.DB_NAME2, null, UserContract2.DATABASE_VERSION2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract2.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract2.Users.DELETE_TABLE);
        onCreate(db);
    }

    public long insertUserByMethod(String name, String address, String phone,String image)  {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract2.Users.KEY_NAME, name);
        values.put(UserContract2.Users.KEY_ADDRESS, address);
        values.put(UserContract2.Users.KEY_PHONE,phone);
        values.put(UserContract2.Users.KEY_IMAGE,image);

        return db.insert(UserContract2.Users.TABLE_NAME,null,values);
    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(UserContract2.Users.TABLE_NAME,null,null,null,null,null,null);
    }

}
