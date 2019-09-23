package com.hansung.android.restaurant;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by hhan0 on 2017-11-21.
 */

//==================Fragment를 상속 받아서 Java 클래스 생성====================

public class RestaurantFragment extends Fragment{

    int mCurCheckPosition = -1; // 선택한 항목의 위치

//========================i는 리스트뷰의 선택된 항목===========================
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

    public RestaurantFragment(){
    }

    public DBHelper mDBHelper;
    public DBHelper2 mDBHelper2;

    EditText mName;
    EditText mAddress;
    EditText mPhone;
    ImageButton mImage;
//강의자료 6주차 프래그먼트 참조
//========================restaurant 레이아웃을 로드한다============================
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.restaurant, container, false);

        mName = (EditText) rootView.findViewById(R.id.edit1);
        mAddress = (EditText) rootView.findViewById(R.id.edit2);
        mPhone = (EditText) rootView.findViewById(R.id.edit3);
        mImage = (ImageButton) rootView.findViewById(R.id.imageButton2);

        //=================현재 프래그먼트와 연결된 액티비티를 반환===============================
        mDBHelper = new DBHelper(getActivity());

        TextView textview1 = (TextView) rootView.findViewById(R.id.maintitle);
        TextView textview2 = (TextView) rootView.findViewById(R.id.textItem2);
        TextView textview3 = (TextView) rootView.findViewById(R.id.textItem3);
        ImageView imageview = (ImageView) rootView.findViewById(R.id.iconItem);

        Cursor cursor = mDBHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) {
            textview1.setText(cursor.getString(1));
            textview2.setText(cursor.getString(2));
            textview3.setText(cursor.getString(3));
            imageview.setImageURI(Uri.parse(cursor.getString(4)));
        }

        //=================현재 프래그먼트와 연결된 액티비티를 반환===============================
        mDBHelper2 = new DBHelper2(getActivity());
        Cursor cursor2 = mDBHelper2.getAllUsersByMethod();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.item, cursor2, new String[]{
                UserContract2.Users.KEY_NAME,
                UserContract2.Users.KEY_ADDRESS,
                UserContract2.Users.KEY_PHONE,
                UserContract2.Users.KEY_IMAGE},
                new int[]{ R.id.address, R.id.name, R.id.phone, R.id.iconItem2}, 0);

        ListView lv = (ListView)rootView.findViewById(R.id.listView2);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent();

                intent.putExtra("1", i);

                Activity activity = getActivity();
                ((OnTitleSelectedListener)activity).onTitleSelected(i);

            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return rootView;
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) { //프래그먼트가 다시 시작될 때 저장된 선택 항목의 위치를 가져옴
            mCurCheckPosition = savedInstanceState.getInt("2", -1);
            if (mCurCheckPosition >= 0) {//저장된 위치가 0이상이면 onTitleSelected 메소드 호출
                Activity activity = getActivity();
                ((OnTitleSelectedListener)activity).onTitleSelected(mCurCheckPosition);

                ListView lv = (ListView) getView().findViewById(R.id.listView2);
                lv.setSelection(mCurCheckPosition);
                lv.smoothScrollToPosition(mCurCheckPosition);
                //출처:http://www.masterqna.com/android/31466/%EB%A6%AC%EC%8A%A4%ED%8A%B8%EB%B7%B0-%EC%9E%90%EB%8F%99-%EC%8A%A4%ED%81%AC%EB%A1%A4-%EB%AC%B8%EC%A0%9C
            }
        }
    }
    //출처 : https://developer.android.com/training/basics/activity-lifecycle/recreating.html?hl=ko
    //    @Override
    public void onSaveInstanceState(Bundle outState) { //프래그먼트가 사라질 때 선택 항목의 위치 저장
        super.onSaveInstanceState(outState);
        outState.putInt("2", mCurCheckPosition);
    }
}
