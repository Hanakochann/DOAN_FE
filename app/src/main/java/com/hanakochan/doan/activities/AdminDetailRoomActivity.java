package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hanakochan.doan.R;
import com.hanakochan.doan.fragments.AdminRoomFragment;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminDetailRoomActivity extends AppCompatActivity {
    private static final String TAG = AdminDetailRoomActivity.class.getSimpleName();
    private static String URL_VERIFIED = ip_config + "/verified.php";
    Toolbar toolbar;
    String username, id;
    SessionManager sessionManager;
    String getUsername;
    private String verified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_room);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getUsername = user.get(sessionManager.NAME);

        toolbar = findViewById(R.id.toolbar_detail_room);
        setSupportActionBar(toolbar);

//        getSupportActionBar().hide();
        String id_code = getIntent().getExtras().getString("detail_id");
        final String userName = getIntent().getExtras().getString("detail_username");
        id = id_code;
        username = userName;
        String title = getIntent().getExtras().getString("detail_type_room");
        String price = getIntent().getExtras().getString("detail_price");
        String lenght = getIntent().getExtras().getString("detail_lenght");
        String width = getIntent().getExtras().getString("detail_width");
        String slot_available = getIntent().getExtras().getString("detail_slot_available");
        String other = getIntent().getExtras().getString("detail_other");
        String city = getIntent().getExtras().getString("detail_city_name");
        String district = getIntent().getExtras().getString("detail_district_name");
        String ward = getIntent().getExtras().getString("detail_ward_name");
        String street = getIntent().getExtras().getString("detail_street_name");
        String number = getIntent().getExtras().getString("detail_number");
        String time = getIntent().getExtras().getString("detail_time");
        String image = getIntent().getExtras().getString("detail_img_room");
        String verified_code = getIntent().getExtras().getString("detail_verified");

        verified = verified_code;
        Toolbar detail_title = findViewById(R.id.tv_title);
        TextView detail_tv_id = findViewById(R.id.detail_tvid);
        TextView detail_tv_user = findViewById(R.id.detail_tvUser);
        TextView detail_tv_title = findViewById(R.id.detail_tvTitle);
        TextView detail_tv_price = findViewById(R.id.detail_tvPrice);
        TextView detail_tv_lenght = findViewById(R.id.detail_tvLenght);
        TextView detail_tv_width = findViewById(R.id.detail_tvWidth);
        TextView detail_tv_slot_available = findViewById(R.id.detail_tvSlot_available);
        TextView detail_tv_other = findViewById(R.id.detail_tvOther);
        TextView detail_tv_city = findViewById(R.id.detail_tvcity);
        TextView detail_tv_district = findViewById(R.id.detail_tvdistrict);
        TextView detail_tv_ward = findViewById(R.id.detail_tvward);
        TextView detail_tv_street = findViewById(R.id.detail_tvstreet);
        TextView detail_tv_number = findViewById(R.id.detail_tvnumber);
        TextView detail_tv_time = findViewById(R.id.detail_tvTime);
        ImageView detail_img_Image = findViewById(R.id.detail_imgView);

        detail_title.setTitle(title);
        detail_tv_id.setText(id_code);
        detail_tv_user.setText(username);
        detail_tv_title.setText(title);
        detail_tv_price.setText(price);
        detail_tv_lenght.setText(lenght);
        detail_tv_width.setText(width);
        detail_tv_slot_available.setText(slot_available);
        detail_tv_other.setText(other);
        detail_tv_city.setText(city);
        detail_tv_district.setText(district);
        detail_tv_ward.setText(ward);
        detail_tv_street.setText(street);
        detail_tv_time.setText(time);
        detail_tv_number.setText(number);

        Glide.with(this).load(image).into(detail_img_Image);
        Button btn_verified = findViewById(R.id.btn_verified);
        if(verified.equals("1")) {
            Toast.makeText(AdminDetailRoomActivity.this, "Phòng này đã được xác minh!", Toast.LENGTH_SHORT).show();
        }else {
            btn_verified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onVerified();
                }
            });
        }
    }
    public void onVerified(){
        final String text = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VERIFIED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success_text = jsonObject.getString("success");
                            if (success_text.equals("1")) {
                                Toast.makeText(AdminDetailRoomActivity.this, "Xác minh thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdminDetailRoomActivity.this, "Xác minh không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminDetailRoomActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("verified", text);
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin_detail_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_detail_room_back) {
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
