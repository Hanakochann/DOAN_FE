package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import com.hanakochan.doan.R;

public class DetailRoommateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_roommate);
        String id = getIntent().getExtras().getString("roommate_detail_id");
        String id_user = getIntent().getExtras().getString("roommate_detail_id_user");
        String title = getIntent().getExtras().getString("roommate_detail_name");
        String price = getIntent().getExtras().getString("roommate_detail_price");
        String city = getIntent().getExtras().getString("roommate_detail_city_name");
        String district = getIntent().getExtras().getString("roommate_detail_district_name");
        String street = getIntent().getExtras().getString("roommate_detail_street_name");
        String gender = getIntent().getExtras().getString("roommate_detail_gender");
        String time = getIntent().getExtras().getString("roommate_detail_time");


        Toolbar roommate_tv_title = findViewById(R.id.tv_title);
        TextView roommate_detail_tv_id = findViewById(R.id.detail_roommate_tvId);
        TextView roommate_detail_tv_title = findViewById(R.id.detail_roommate_tvTitle);
        TextView roommate_detail_tv_price = findViewById(R.id.detail_roommate_tvPrice);
        TextView roommate_detail_tv_city = findViewById(R.id.detail_roommate_tvcity);
        TextView roommate_detail_tv_district = findViewById(R.id.detail_roommate_tvdistrict);
        TextView roommate_detail_tv_street = findViewById(R.id.detail_roommate_tvstreet);
        TextView roommate_detail_tv_gender = findViewById(R.id.detail_roommate_tvgender);
        TextView roommate_detail_tv_time = findViewById(R.id.detail_roommate_tvTime);


        roommate_tv_title.setTitle(title);
        roommate_detail_tv_title.setText(title);
        roommate_detail_tv_id.setText(id);
        roommate_detail_tv_price.setText(price);
        roommate_detail_tv_city.setText(city);
        roommate_detail_tv_district.setText(district);
        roommate_detail_tv_street.setText(street);
        roommate_detail_tv_gender.setText(gender);
        roommate_detail_tv_time.setText(time);

    }
}
