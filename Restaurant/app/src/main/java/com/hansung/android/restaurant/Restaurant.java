package com.hansung.android.restaurant;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
/**
 * Created by hhan0 on 2017-11-14.
 */
//강의자료 6주차 프래그먼트 참조
//=============================맛집 정보와 메뉴 정보를 리스트로 보여주는 코드===================================
public class Restaurant extends AppCompatActivity implements RestaurantFragment.OnTitleSelectedListener {

    private DBHelper DbHelper;
    private DBHelper2 DbHelper2;

    ImageView iconItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_restaurant);
    }

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //===============================옵션바의 스타일 설정=============================
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
//=====================옵션마의 + 버튼을 누르면 메뉴를 등록시켜주는 클래스로 이동한다==============================
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quick_action1:
                startActivity(new Intent(this, MenuAdd.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //========================i는 선택된 항목===========================
    //===================RestuarantFragment의 리스트 항목을 클릭하면 Restaurant.java로 이동 후 MenuFragment로 해당 선택된 항목을 보여준다============================
    @Override
    public void onTitleSelected(int i) {
        //=============================가로 방향일때는 화면 두개로 나눠져서 보여진다==================================
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MenuFragment detailfragment = new MenuFragment();
            detailfragment.setSelection(i);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2, detailfragment).commit();
        } else {
            Intent intent = new Intent(this, MenuDetail.class);
            intent.putExtra("index", i);
            startActivity(intent);

        }
    }
}

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.restaurant);
//
//        DbHelper = new DBHelper(this);
//        DbHelper2 = new DBHelper2(this);
//
//        iconItem = (ImageView) findViewById(R.id.iconItem);
//
//        viewAllToTextView1();
//        viewAllToTextView2();
//        viewAllToTextView3();
//        viewCamera();
//        viewAllToListView();
//
//    }
//
//
//    //====================================액션바 표시 기능==================================
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.quick_action1:
//                startActivity(new Intent(this, MenuAdd.class));
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    //=======================맛집 등록 정보 데이터베이스 텍스트 출력=============================
//    private void viewAllToTextView1() {
//        TextView result = (TextView) findViewById(R.id.maintitle);
//
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(1) + " \n");
//        }
//        result.setText(buffer);
//
//    }
//
//    private void viewAllToTextView2() {
//        TextView result = (TextView) findViewById(R.id.textItem2);
//
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(2) + " \n");
//        }
//        result.setText(buffer);
//
//
//    }
//
//    private void viewAllToTextView3() {
//        TextView result = (TextView) findViewById(R.id.textItem3);
//
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//
//        StringBuffer buffer = new StringBuffer();
//        if(cursor.moveToLast()){
//            buffer.append(cursor.getString(3) + "\n");
//        }
//        result.setText(buffer);
//
//    }
//
//    private void viewCamera() {
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//
//        if(cursor.moveToLast()){
//            iconItem.setImageURI(Uri.parse(cursor.getString(4)));
//        }
//
//    }
//
//
//
//    //==========================메뉴 상세 정보 리스트 데이터베이스 출력================================
//    private void viewAllToListView() {
//        Cursor cursor = DbHelper2.getAllUsersByMethod();
//
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
//                R.layout.item, cursor, new String[]{
//                UserContract2.Users._ID,
//                UserContract2.Users.KEY_NAME,
//                UserContract2.Users.KEY_ADDRESS,
//                UserContract2.Users.KEY_PHONE,
//                UserContract2.Users.KEY_IMAGE},
//                new int[]{R.id._id, R.id.address, R.id.name, R.id.phone, R.id.iconItem2}, 0);
//
//        ListView lv = (ListView) findViewById(R.id.listView2);
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Intent intent = new Intent(getApplicationContext(), MenuDetail.class);
//                startActivity(intent);
//            }
//        });
//        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//    }
//
//    //================================프레그먼트============================
//    public void onTitleSelected(int i) {
//
//    }

