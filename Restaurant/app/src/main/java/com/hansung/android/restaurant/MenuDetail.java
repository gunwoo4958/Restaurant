package com.hansung.android.restaurant;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


//메뉴상세정보
//강의자료 6주차 프래그먼트 참조
public class MenuDetail extends AppCompatActivity {
    private DBHelper2 mDbHelper2;

    //===========================리스트의 메뉴정보를 누르면 각각의 메뉴정보를 나타내 주는 코드===============================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }


        MenuFragment details = new MenuFragment();
        details.setSelection(getIntent().getIntExtra("index", -1));
        getSupportFragmentManager().beginTransaction().replace(R.id.details, details).commit();



//============================액션바 위에 뒤로가기를 누르면 자신의 전 액티비티로 돌아간다=======================================
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            Drawable drawable = getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
//            if (drawable != null) {
//                drawable.setTint(Color.WHITE);
//                actionBar.setHomeAsUpIndicator(drawable);
//            }
//        }
// }
//=============================메뉴정보  DBHelper2에서 저장되어지는 데이터베이스를 불러오는 코드==========================
//    private void viewAllToTextView1() {
//        TextView result = (TextView) findViewById(R.id.textView1);
//        Cursor cursor = mDbHelper2.getAllUsersByMethod();
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(1) + " \n");
//        }
//        result.setText(buffer);
//    }
//
//    private void viewAllToTextView2() {
//        TextView result = (TextView) findViewById(R.id.textView2);
//        Cursor cursor = mDbHelper2.getAllUsersByMethod();
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(2) + " \n");
//        }
//        result.setText(buffer);
//    }
//
//
//    private void viewAllToTextView3() {
//        TextView result = (TextView) findViewById(R.id.textView3);
//        Cursor cursor = mDbHelper2.getAllUsersByMethod();
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(3) + " \n");
//        }
//        result.setText(buffer);
//    }
//
//    private void viewCamera() {
//        ImageView imageView = (ImageView)findViewById(R.id.imageView1);
//
//        Cursor cursor = mDbHelper2.getAllUsersByMethod();
//
//        if(cursor.moveToLast()){
//            imageView.setImageURI(Uri.parse(cursor.getString(4)));
//        }
//
//    }


    }
}