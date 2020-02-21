package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.AdminRecyclerViewAdapterSearch_Roommate;
import com.hanakochan.doan.models.RecyclerViewAdapterSearch_Roommate;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminSearchResultRoommateActivity extends AppCompatActivity {
    private static String URL_SEARCH = ip_config + "/admin_search_roommate.php";
    Toolbar toolbar;
    RecyclerView recyclerView;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    SessionManager sessionManager;
    String getId, getUrlImage;
    TextView tvEmpty;
    private ArrayList<Room> lstRoom = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_result_roommate);sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getUrlImage = user.get(sessionManager.IMAGE);
        lstRoom = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar_home_id);
        setSupportActionBar(toolbar);
        AdminSearchResultRoommateActivity.this.setTitle("Kết quả");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        recyclerView = findViewById(R.id.recyclerViewResult);
        tvEmpty = findViewById(R.id.empty);
        collectDataRoommate();
    }
    private void collectDataRoommate() {
        final String name = getIntent().getExtras().getString("name");
        request = new JsonArrayRequest(Request.Method.GET, URL_SEARCH+ "?username=" + name, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    if (response.isNull(i)){
                        String a = "Dữ liệu bạn tìm không có";
                        tvEmpty.setText(a);
                    }
                    try {
                        jsonObject = response.getJSONObject(i);
                        Room room = new Room();
                        room.setId_post(jsonObject.getString("id"));
                        room.setId_user(jsonObject.getString("id_user"));
                        room.setUsername(jsonObject.getString("username"));
                        room.setHometown(jsonObject.getString("hometown"));
                        room.setGender(jsonObject.getString("gender"));
                        room.setBirthday(jsonObject.getString("birthday"));
                        room.setPrice(jsonObject.getString("price"));
                        room.setImage(jsonObject.getString("img_user"));
                        room.setCity(jsonObject.getString("city_name"));
                        room.setDistrict(jsonObject.getString("district_name"));
                        room.setWard(jsonObject.getString("ward_name"));
                        room.setStreet(jsonObject.getString("street_name"));
                        room.setPhone(jsonObject.getString("phone"));
                        room.setGender_roommate(jsonObject.getString("gender_roommate"));
                        room.setTime(jsonObject.getString("time_post"));
                        lstRoom.add(room);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerViewRoommate(lstRoom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setupRecyclerViewRoommate(ArrayList<Room> lstRoom) {
        AdminRecyclerViewAdapterSearch_Roommate myAdapter = new AdminRecyclerViewAdapterSearch_Roommate(this, lstRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
}
