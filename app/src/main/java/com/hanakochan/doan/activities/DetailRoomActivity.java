package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.hanakochan.doan.models.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class DetailRoomActivity extends AppCompatActivity {
    private static final String TAG = DetailRoomActivity.class.getSimpleName();
    private static String URL_PHONE = ip_config + "/get_phone.php";
    Toolbar toolbar;
    String username, phone;
    SessionManager sessionManager;
    String getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getUsername = user.get(sessionManager.NAME);

        toolbar = findViewById(R.id.toolbar_detail_room);
        setSupportActionBar(toolbar);

//        getSupportActionBar().hide();
        final String userName = getIntent().getExtras().getString("detail_username");
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
        final String profile = getIntent().getExtras().getString("detail_img_user");

        Toolbar detail_title = findViewById(R.id.tv_title);
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

        getPhonenumber();
        Button btn_call = findViewById(R.id.btn_Call_room);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getUsername.equals(username)) {
                    Toast.makeText(DetailRoomActivity.this, "Ôi, bạn không thể gọi cho chính mình!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + 0 + phone));
                    startActivity(intent);
                }
            }
        });
    }

    private void getPhonenumber() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PHONE + "?username=" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String strPhone = object.getString("phone").trim();

                                    if (strPhone == "null") {
                                        phone = null;
                                    } else {
                                        phone = strPhone;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailRoomActivity.this, "Không thể hiển thị chi tiết!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailRoomActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailRoomActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_room, menu);
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
