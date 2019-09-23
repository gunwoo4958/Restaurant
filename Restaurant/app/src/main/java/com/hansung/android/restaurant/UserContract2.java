package com.hansung.android.restaurant;


import android.provider.BaseColumns;
//==============================메뉴 정보가 저장되어지는 데이터베이스=======================================
public final class UserContract2 {
    public static final String DB_NAME2="user.db2";
    public static final int DATABASE_VERSION2 = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private UserContract2() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_NAME = "Name";
        public static final String KEY_ADDRESS="Address";
        public static final String KEY_PHONE = "Phone";
        public static final String KEY_IMAGE = "Image";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_PHONE + TEXT_TYPE +COMMA_SEP +
                KEY_IMAGE + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
