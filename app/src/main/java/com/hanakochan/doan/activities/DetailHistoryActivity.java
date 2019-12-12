package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanakochan.doan.R;

public class DetailHistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        toolbar = findViewById(R.id.toolbar_detail_history_id);
        setSupportActionBar(toolbar);

        String id = getIntent().getExtras().getString("detail_history_id");
        String title = getIntent().getExtras().getString("detail_history_type_room");
        String price = getIntent().getExtras().getString("detail_history_price");
        String length = getIntent().getExtras().getString("detail_history_length");
        String width = getIntent().getExtras().getString("detail_history_width");
        String slot_available = getIntent().getExtras().getString("detail_history_slot_available");
        String other = getIntent().getExtras().getString("detail_history_other");
        String city = getIntent().getExtras().getString("detail_history_city_name");
        String district = getIntent().getExtras().getString("detail_history_district_name");
        String ward = getIntent().getExtras().getString("detail_history_ward_name");
        String street = getIntent().getExtras().getString("detail_history_street_name");
        String number = getIntent().getExtras().getString("detail_history_number");
        String time = getIntent().getExtras().getString("detail_history_time");
        String image = getIntent().getExtras().getString("detail_history_img_room");

        Toolbar detail_title = findViewById(R.id.tv_title);
        TextView detail_tv_id = findViewById(R.id.detail_history_tvid);
        TextView detail_tv_title = findViewById(R.id.detail_history_tvTitle);
        TextView detail_tv_price = findViewById(R.id.detail_history_tvPrice);
        TextView detail_tv_length = findViewById(R.id.detail_history_tvLenght);
        TextView detail_tv_width = findViewById(R.id.detail_history_tvWidth);
        TextView detail_tv_slot_available = findViewById(R.id.detail_history_tvSlot_available);
        TextView detail_tv_other = findViewById(R.id.detail_history_tvOther);
        TextView detail_tv_city = findViewById(R.id.detail_history_tvcity);
        TextView detail_tv_district = findViewById(R.id.detail_history_tvdistrict);
        TextView detail_tv_ward = findViewById(R.id.detail_history_tvward);
        TextView detail_tv_street = findViewById(R.id.detail_history_tvstreet);
        TextView detail_tv_number = findViewById(R.id.detail_history_tvnumber);
        TextView detail_tv_time = findViewById(R.id.detail_history_tvtime);
        ImageView detail_img_Image = findViewById(R.id.detail_history_imgView);

        detail_tv_id.setText(id);
        detail_tv_title.setText(title);
        detail_tv_price.setText(price);
        detail_tv_length.setText(length);
        detail_tv_width.setText(width);
        detail_tv_slot_available.setText(slot_available);
        detail_tv_other.setText(other);
        detail_tv_city.setText(city);
        detail_tv_district.setText(district);
        detail_tv_ward.setText(ward);
        detail_tv_street.setText(street);
        detail_tv_number.setText(number);
        detail_tv_time.setText(time);

        Glide.with(this).load(image).into(detail_img_Image);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_detail_history) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
