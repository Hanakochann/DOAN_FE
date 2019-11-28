package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanakochan.doan.R;

public class DetailHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        String id = getIntent().getExtras().getString("detail_history_id");
        String title = getIntent().getExtras().getString("detail_history_type_room");
        String price = getIntent().getExtras().getString("detail_history_history_price");
        String slot_available = getIntent().getExtras().getString("detail_history_slot_available");
        String other = getIntent().getExtras().getString("detail_history_other");
        String city = getIntent().getExtras().getString("detail_history_city_name");
        String district = getIntent().getExtras().getString("detail_history_district_name");
        String street = getIntent().getExtras().getString("detail_history_street_name");
        String number = getIntent().getExtras().getString("detail_history_number");
        String image = getIntent().getExtras().getString("detail_history_img_room");

        Toolbar detail_title = findViewById(R.id.tv_title);
        TextView detail_tv_id = findViewById(R.id.detail_history_tvid);
        TextView detail_tv_title = findViewById(R.id.detail_history_tvTitle);
        TextView detail_tv_price = findViewById(R.id.detail_history_tvPrice);
        TextView detail_tv_slot_available = findViewById(R.id.detail_history_tvSlot_available);
        TextView detail_tv_other = findViewById(R.id.detail_history_tvOther);
        TextView detail_tv_city = findViewById(R.id.detail_history_tvcity);
        TextView detail_tv_district = findViewById(R.id.detail_history_tvdistrict);
        TextView detail_tv_street = findViewById(R.id.detail_history_tvstreet);
        TextView detail_tv_number = findViewById(R.id.detail_history_tvnumber);
        ImageView detail_img_Image = findViewById(R.id.detail_history_imgView);

        detail_title.setTitle(title);
        detail_tv_id.setText(id);
        detail_tv_title.setText(title);
        detail_tv_price.setText(price);
        detail_tv_slot_available.setText(slot_available);
        detail_tv_other.setText(other);
        detail_tv_city.setText(city);
        detail_tv_district.setText(district);
        detail_tv_street.setText(street);
        detail_tv_number.setText(number);

        Glide.with(this).load(image).into(detail_img_Image);
    }
}
