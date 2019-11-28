package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanakochan.doan.R;

public class DetailRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

//        getSupportActionBar().hide();
        String username = getIntent().getExtras().getString("detail_username");
        String title = getIntent().getExtras().getString("detail_type_room");
        String price = getIntent().getExtras().getString("detail_price");
        String slot_available = getIntent().getExtras().getString("detail_slot_available");
        String other = getIntent().getExtras().getString("detail_other");
        String city = getIntent().getExtras().getString("detail_city_name");
        String district = getIntent().getExtras().getString("detail_district_name");
        String street = getIntent().getExtras().getString("detail_street_name");
        String number = getIntent().getExtras().getString("detail_number");
        String time = getIntent().getExtras().getString("detail_time");
        String image = getIntent().getExtras().getString("detail_img_room");

        Toolbar detail_title = findViewById(R.id.tv_title);
        TextView detail_tv_user = findViewById(R.id.detail_tvUser);
        TextView detail_tv_title = findViewById(R.id.detail_tvTitle);
        TextView detail_tv_price = findViewById(R.id.detail_tvPrice);
        TextView detail_tv_slot_available = findViewById(R.id.detail_tvSlot_available);
        TextView detail_tv_other = findViewById(R.id.detail_tvOther);
        TextView detail_tv_city = findViewById(R.id.detail_tvcity);
        TextView detail_tv_district = findViewById(R.id.detail_tvdistrict);
        TextView detail_tv_street = findViewById(R.id.detail_tvstreet);
        TextView detail_tv_number = findViewById(R.id.detail_tvnumber);
        TextView detail_tv_time = findViewById(R.id.detail_tvTime);
        ImageView detail_img_Image = findViewById(R.id.detail_imgView);

        detail_title.setTitle(title);
        detail_tv_user.setText(username);
        detail_tv_title.setText(title);
        detail_tv_price.setText(price);
        detail_tv_slot_available.setText(slot_available);
        detail_tv_other.setText(other);
        detail_tv_city.setText(city);
        detail_tv_district.setText(district);
        detail_tv_street.setText(street);
        detail_tv_time.setText(time);
        detail_tv_number.setText(number);

        Glide.with(this).load(image).into(detail_img_Image);

    }
}
