package com.hansung.android.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class hw1_RestaurantDetail extends AppCompatActivity {

    static hw1_Myfood adapter2;
    private static final String TAG = "ActivityLifeCycle";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw1_activity_restaurant_detail);
        Log.i(TAG, getLocalClassName() + ".onCreate");


        ImageButton btn = (ImageButton) findViewById(R.id.imageButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-543-6467"));
                startActivity(implicit_intent);
            }
        });

        Button btn2 = (Button) findViewById(R.id.button);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(getApplicationContext(), RestaurantRegister.class);
                startActivity(new_intent);
            }
        });





        // 데이터 원본 준비
        final ArrayList<MyItem2> data2 = new ArrayList<MyItem2>();
        data2.add(new MyItem2(R.drawable.americano, "아메리카노", "2000", "평점 : 4.0"));
        data2.add(new MyItem2(R.drawable.cafelatte, "카페라떼", "2500", "평점 : 3.5"));
        data2.add(new MyItem2(R.drawable.cafemoca, "카페모카", "2500", "평점 : 3.0"));
        data2.add(new MyItem2(R.drawable.caramel, "카라멜", "3000", "평점 : 4.5"));


        //어댑터 생성
        adapter2 = new hw1_Myfood(this, R.layout.hw1_item3, data2);

        //어댑터 연결
        ListView listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(adapter2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), hw1_foodmenu.class);

                intent.putExtra("title", data2.get(position).nfoodname);
                intent.putExtra("img", data2.get(position).mIcon2);
                intent.putExtra("artist", data2.get(position).nfoodprice);
                intent.putExtra("score", data2.get(position).nfoodscore);

                startActivity(intent);
            }
        });


    }

    }





