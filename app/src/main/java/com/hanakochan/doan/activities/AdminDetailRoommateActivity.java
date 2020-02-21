package com.hanakochan.doan.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hanakochan.doan.R;
import com.hanakochan.doan.models.SessionManager;

import java.util.HashMap;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminDetailRoommateActivity extends AppCompatActivity {
    private static final String TAG = DetailRoommateActivity.class.getSimpleName();
    private static String URL_PHONE = ip_config + "/get_phone.php";
    Toolbar toolbar;
    String username, hometown, birthday, gender, phone;
    SessionManager sessionManager;
    String getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_roommate);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getUsername = user.get(sessionManager.NAME);

        toolbar = findViewById(R.id.toolbar_detail_roommate);
        setSupportActionBar(toolbar);
        String id = getIntent().getExtras().getString("roommate_detail_id");
        String id_user = getIntent().getExtras().getString("roommate_detail_id_user");
        String title = getIntent().getExtras().getString("roommate_detail_name");
        String hometown_view = getIntent().getExtras().getString("roommate_detail_hometown");
        String gender_view = getIntent().getExtras().getString("roommate_detail_gender");
        String birthday_view = getIntent().getExtras().getString("roommate_detail_birthday");
        String price = getIntent().getExtras().getString("roommate_detail_price");
        String city = getIntent().getExtras().getString("roommate_detail_city_name");
        String district = getIntent().getExtras().getString("roommate_detail_district_name");
        String ward = getIntent().getExtras().getString("roommate_detail_ward_name");
        String street = getIntent().getExtras().getString("roommate_detail_street_name");
        String phone_view = getIntent().getExtras().getString("roommate_detail_phone");
        String gender_roommate = getIntent().getExtras().getString("roommate_detail_gender_roommate");
        String note = getIntent().getExtras().getString("roommate_detail_note");
        String time = getIntent().getExtras().getString("roommate_detail_time");

        username = title;
        hometown = hometown_view;
        birthday = birthday_view;
        gender = gender_view;
        phone = phone_view;

        Toolbar roommate_tv_title = findViewById(R.id.tv_title);
        TextView roommate_detail_tv_id = findViewById(R.id.detail_roommate_tvId);
        TextView roommate_detail_tv_price = findViewById(R.id.detail_roommate_tvPrice);
        TextView roommate_detail_tv_city = findViewById(R.id.detail_roommate_tvcity);
        TextView roommate_detail_tv_district = findViewById(R.id.detail_roommate_tvdistrict);
        TextView roommate_detail_tv_ward = findViewById(R.id.detail_roommate_tvward);
        TextView roommate_detail_tv_street = findViewById(R.id.detail_roommate_tvstreet);
        TextView roommate_detail_tv_gender = findViewById(R.id.detail_roommate_tvgender);
        TextView roommate_detail_tv_note = findViewById(R.id.detail_roommate_tvnote);
        TextView roommate_detail_tv_time = findViewById(R.id.detail_roommate_tvTime);


        roommate_tv_title.setTitle(title);
        roommate_detail_tv_id.setText(id);
        roommate_detail_tv_price.setText(price);
        roommate_detail_tv_city.setText(city);
        roommate_detail_tv_district.setText(district);
        roommate_detail_tv_ward.setText(ward);
        roommate_detail_tv_street.setText(street);
        roommate_detail_tv_gender.setText(gender_roommate);
        roommate_detail_tv_note.setText(note);
        roommate_detail_tv_time.setText(time);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_roommate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_detail_roommate_back) {
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
