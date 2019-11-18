package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hanakochan.doan.R;

public class DetailRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

//        getSupportActionBar().hide();

        String title = getIntent().getExtras().getString("detail_type_room");
        String price = getIntent().getExtras().getString("detail_price");
        String city = getIntent().getExtras().getString("detail_city_name");
        String district = getIntent().getExtras().getString("detail_district_name");
        String street = getIntent().getExtras().getString("detail_street_name");
        String number = getIntent().getExtras().getString("detail_number");
        String image = getIntent().getExtras().getString("detail_img_room");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.id_collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_title = findViewById(R.id.aa_tvTitle);
        TextView tv_price = findViewById(R.id.aa_tvPrice);
        TextView tv_city = findViewById(R.id.aa_tvcity);
        TextView tv_district = findViewById(R.id.aa_tvdistrict);
        TextView tv_street = findViewById(R.id.aa_tvstreet);
        TextView tv_number = findViewById(R.id.aa_tvnumber);
        ImageView image_view = findViewById(R.id.aa_imgView);

        TextView detail_tv_title = findViewById(R.id.detail_tvTitle);
        TextView detail_tv_price = findViewById(R.id.detail_tvPrice);
        TextView detail_tv_city = findViewById(R.id.detail_tvcity);
        TextView detail_tv_district = findViewById(R.id.detail_tvdistrict);
        TextView detail_tv_street = findViewById(R.id.detail_tvstreet);
        TextView detail_tv_number = findViewById(R.id.detail_tvnumber);


        tv_title.setText(title);
        tv_price.setText(price);
        tv_city.setText(city);
        tv_district.setText(district);
        tv_street.setText(street);
        tv_number.setText(number);

        detail_tv_title.setText(title);
        detail_tv_price.setText(price);
        detail_tv_city.setText(city);
        detail_tv_district.setText(district);
        detail_tv_street.setText(street);
        detail_tv_number.setText(number);


        collapsingToolbarLayout.setTitle(title);

        Glide.with(this).load(image).into(image_view);

    }
}
