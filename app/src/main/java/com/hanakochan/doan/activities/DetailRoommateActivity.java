package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hanakochan.doan.R;

public class DetailRoommateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_roommate);
        String title = getIntent().getExtras().getString("roommate_detail_name");
        String price = getIntent().getExtras().getString("roommate_detail_price");
        String city = getIntent().getExtras().getString("roommate_detail_city_name");
        String district = getIntent().getExtras().getString("roommate_detail_district_name");
        String street = getIntent().getExtras().getString("roommate_detail_street_name");
        String gender = getIntent().getExtras().getString("roommate_detail_gender");
        String image = getIntent().getExtras().getString("roommate_detail_img_room");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.id_collapsingToolbar_roommate);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView roommate_tv_title = findViewById(R.id.aa_roommate_tvTitle);
        TextView roommate_tv_price = findViewById(R.id.aa_roommate_tvPrice);
        TextView roommate_tv_city = findViewById(R.id.aa_roommate_tvcity);
        TextView roommate_tv_district = findViewById(R.id.aa_roommate_tvdistrict);
        TextView roommate_tv_street = findViewById(R.id.aa_roommate_tvstreet);
        TextView roommate_tv_gender = findViewById(R.id.aa_roommate_tvgender);
        ImageView roommate_image_view = findViewById(R.id.aa_roommate_imgView);

        TextView roommate_detail_tv_title = findViewById(R.id.detail_roommate_tvTitle);
        TextView roommate_detail_tv_price = findViewById(R.id.detail_roommate_tvPrice);
        TextView roommate_detail_tv_city = findViewById(R.id.detail_roommate_tvcity);
        TextView roommate_detail_tv_district = findViewById(R.id.detail_roommate_tvdistrict);
        TextView roommate_detail_tv_street = findViewById(R.id.detail_roommate_tvstreet);
        TextView roommate_detail_tv_gender = findViewById(R.id.detail_roommate_tvgender);


        roommate_tv_title.setText(title);
        roommate_tv_price.setText(price);
        roommate_tv_city.setText(city);
        roommate_tv_district.setText(district);
        roommate_tv_street.setText(street);
        roommate_tv_gender.setText(gender);

        roommate_detail_tv_title.setText(title);
        roommate_detail_tv_price.setText(price);
        roommate_detail_tv_city.setText(city);
        roommate_detail_tv_district.setText(district);
        roommate_detail_tv_street.setText(street);
        roommate_detail_tv_gender.setText(gender);


        collapsingToolbarLayout.setTitle(title);

        Glide.with(this).load(image).into(roommate_image_view);
    }
}
