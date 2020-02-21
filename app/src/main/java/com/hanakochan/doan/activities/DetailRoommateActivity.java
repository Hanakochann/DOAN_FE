package com.hanakochan.doan.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class DetailRoommateActivity extends AppCompatActivity {
    private static final String TAG = DetailRoommateActivity.class.getSimpleName();
    private static String URL_PHONE = ip_config + "/get_phone.php";
    Toolbar toolbar;
    String username, hometown, birthday, gender, phone;
    SessionManager sessionManager;
    String getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_roommate);

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


        getPhonenumber();
        Button btn_call = findViewById(R.id.btn_call_roomate);
        Button btn_view = findViewById(R.id.btn_view);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getUsername.equals(username)){
                    Toast.makeText(DetailRoommateActivity.this, "Ôi, bạn không thể gọi cho chính mình!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + 0 + phone));
                    startActivity(intent);
                }
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRoommateActivity.this);
                builder.setMessage("Tên: "+username+"\nQuê quán: "+hometown+"\nNăm sinh: "+birthday+"\nGiới tính: "+gender)
                        .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
                            Toast.makeText(DetailRoommateActivity.this, "Không thể hiển thị chi tiết!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailRoommateActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailRoommateActivity.this);
        requestQueue.add(stringRequest);
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
