package com.hanakochan.doan;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class CustomListView extends ArrayAdapter<String> {

    private String[] profilename;
    private String[] title;
    private String[] price;
    private String[] address;
    private String[] imgPath;
    private Activity context;


    public CustomListView(Activity  context, String[] profilename,String[] title, String[] price, String[] address, String[] imgPath) {

        super(context, R.layout.listview_room_layout, profilename);
        this.context = context;
        this.title = title;
        this.price = price;
        this.address = address;
        this.imgPath = imgPath;

    }
}
