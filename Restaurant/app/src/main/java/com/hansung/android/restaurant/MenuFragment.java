package com.hansung.android.restaurant;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hhan0 on 2017-11-21.
 */
//강의자료 6주차 프래그먼트 참조
public class MenuFragment extends android.support.v4.app.Fragment {

    public DBHelper2 mDBHelper2;
    static int index = -1;

    public void setSelection(int i){index = i;}

    public MenuFragment(){
    }
//==================Fragment를 상속 받아서 Java 클래스 생성====================
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.menudetail, container, false);
        mDBHelper2 = new DBHelper2(getActivity());
        //출처 : https://stackoverflow.com/questions/47827325/how-to-use-databse-with-fragment

        Cursor Cursor = mDBHelper2.getAllUsersByMethod();
        Intent intent = new Intent();

        int title = intent.getIntExtra("title",index);
        Cursor.moveToPosition(title);

        TextView textview1 = (TextView)view.findViewById(R.id.textView1);
        textview1.setText(Cursor.getString(1));

        TextView textview2 = (TextView)view.findViewById(R.id.textView2);
        textview2.setText(Cursor.getString(2));

        TextView textview3 = (TextView)view.findViewById(R.id.textView3);
        textview3.setText(Cursor.getString(3));

        ImageView img = (ImageView)view.findViewById(R.id.imageView1);
        img.setImageURI(Uri.parse(Cursor.getString(4)));

        return view;
    }
}
