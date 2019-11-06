package com.hanakochan.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hanakochan.doan.fragments.HomepageFragment;

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinner_city, spinner_district, spinner_street, spinner_type_room;
    private EditText edt_number, edt_slot_available, edt_price, edt_other;
    private Button btn_post;
    Toolbar toolbar;
    String [] SpinnerCity = {"Huế", "Đà Nẵng"};
    String [] SpinnerDistrict_Hue = {"A Lưới","Nam Đông", "Phong Điền", "Phú Lộc", "Phú Vang", "Quảng Điền"};
    String [] SpinnerDistrict_DaNang = {"Cẩm Lệ", "Hải Châu","Hòa Vang","Hoàng Sa","Liên Chiểu","Ngũ Hành Sơn", "Sơn Trà", "Thanh Khê"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        toolbar = findViewById(R.id.toolbar_add_room);
        setSupportActionBar(toolbar);
        AddRoomActivity.this.setTitle("Add Room");

        spinner_city = findViewById(R.id.id_city);
        spinner_district = findViewById(R.id.id_district);
        spinner_street = findViewById(R.id.id_street);
        spinner_type_room = findViewById(R.id.id_room_type);

        edt_number = findViewById(R.id.id_number);
        edt_price = findViewById(R.id.id_price);
        edt_slot_available = findViewById(R.id.id_slot);
        edt_other = findViewById(R.id.id_other);

        btn_post = findViewById(R.id.id_button);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
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
