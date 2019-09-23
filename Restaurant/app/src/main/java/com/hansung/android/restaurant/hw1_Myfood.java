package com.hansung.android.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


public class hw1_Myfood extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<MyItem2> mItems = new ArrayList<MyItem2>();

    public hw1_Myfood(Context context, int resource, ArrayList<MyItem2> items) {
        mContext = context;
        mItems = items;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);
        }
        // Set Icon
        ImageView icon2 = (ImageView) convertView.findViewById(R.id.iconItem2);
        icon2.setImageResource(mItems.get(position).mIcon2);

        // Set Text 01
        TextView foodname = (TextView) convertView.findViewById(R.id.textItem4);
        foodname.setText(mItems.get(position).nfoodname);

        // Set Text 02
        TextView foodprice = (TextView) convertView.findViewById(R.id.textItem5);
        foodprice.setText(mItems.get(position).nfoodprice);

        TextView foodscore = (TextView) convertView.findViewById(R.id.textItem6);
        foodscore.setText(mItems.get(position).nfoodscore);

        foodscore.setVisibility(View.INVISIBLE);


        return convertView;
    }
}

class MyItem2 {
    int mIcon2; // image resource
    String nfoodname; // text
    String nfoodprice;  // text
    String nfoodscore;


    MyItem2(int aIcon, String afoodname, String afoodprice, String afoodscore) {
        mIcon2 = aIcon;
        nfoodname = afoodname;
        nfoodprice = afoodprice;
        nfoodscore = afoodscore;

    }
}

