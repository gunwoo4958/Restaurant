package com.hansung.android.restaurant;


import android.provider.BaseColumns;
//================================맛집 등록 정보가 저장되어지는 데이터베이스====================================
public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_NAME = "Name";
        public static final String KEY_ADDRESS="Address"; //위도
        public static final String KEY_PHONE = "Phone";  //경도
        public static final String KEY_PHOTO = "Photo";
        public static final String KEY_LATITUDE = "LATITUDE";
        public static final String KEY_LONGITUDE = "LONGITUDE";





        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_PHONE + TEXT_TYPE + COMMA_SEP +
                KEY_PHOTO + TEXT_TYPE + COMMA_SEP +
                KEY_LATITUDE + TEXT_TYPE + COMMA_SEP +
                KEY_LONGITUDE + TEXT_TYPE +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
