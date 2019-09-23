package com.hansung.android.restaurant;


import android.provider.BaseColumns;

//==============================위도 경도가 저장되어지는 데이터베이스=======================================
public final class UserContract3 {
    public static final String DB_NAME3="user.db3";
    public static final int DATABASE_VERSION3 = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private UserContract3() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_IMAGE = "Image";
        public static final String KEY_IMAGE2 = "Image2";
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_IMAGE + TEXT_TYPE + COMMA_SEP +
                KEY_IMAGE2 + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
