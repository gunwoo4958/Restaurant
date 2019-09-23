package com.hansung.android.restaurant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


//메뉴상세정보

public class hw1_foodmenu extends AppCompatActivity {
    private static final String TAG = "ActivityLifeCycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw1_foodmenu);

        TextView tvTitle = (TextView)findViewById(R.id.textView1);
        TextView tvArtist = (TextView)findViewById(R.id.textView2);
        ImageView iv = (ImageView)findViewById(R.id.imageView1);
        TextView score = (TextView)findViewById(R.id.textView3);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvTitle.setText(intent.getStringExtra("title"));
        tvArtist.setText(intent.getStringExtra("artist"));
        iv.setImageResource(intent.getIntExtra("img", 0));
        score.setText(intent.getStringExtra("score"));



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
            if (drawable != null) {
                drawable.setTint(Color.WHITE);
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }
    }


}