package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hanakochan.doan.R;

public class SearchRoomActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinnerCity;
    TextView tv_city, tv_message;
    int check = 0;
    String[] City = {"HUE", "DN"};

    ArrayAdapter<String> arrayAdapterCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_room);

        toolbar = findViewById(R.id.toolbar_search_room_id);
        setSupportActionBar(toolbar);
        SearchRoomActivity.this.setTitle("Tìm phòng");

        spinnerCity = findViewById(R.id.id_spinner_city);
        tv_city = findViewById(R.id.tv_spinner_text);
        tv_message = findViewById(R.id.tv_message);

        arrayAdapterCity = new ArrayAdapter<String>(this,R.layout.spinner_text_layout, R.id.tv_spinner_text,City);
        spinnerCity.setAdapter(arrayAdapterCity);
        tv_message.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back_search_room) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
