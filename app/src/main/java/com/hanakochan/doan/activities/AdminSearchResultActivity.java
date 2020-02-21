package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.AdminRecyclerViewAdapterSearch;
import com.hanakochan.doan.models.RecyclerViewAdapterSearch;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminSearchResultActivity extends AppCompatActivity {
    private static String URL_SEARCH = ip_config + "/admin_search_room.php";
    Toolbar toolbar;
    RecyclerView recyclerView;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    SessionManager sessionManager;
    String getId, getUrlImage;
    private ArrayList<Room> lstRoom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_result);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getUrlImage = user.get(sessionManager.IMAGE);
        lstRoom = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar_home_id);
        setSupportActionBar(toolbar);
        AdminSearchResultActivity.this.setTitle("Kết quả");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        recyclerView = findViewById(R.id.recyclerViewResult);
        collectData();
    }
    private void collectData() {
        final String name = getIntent().getExtras().getString("name");
        request = new JsonArrayRequest(Request.Method.GET, URL_SEARCH + "?username=" + name, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Room room = new Room();
                        room.setId_post(jsonObject.getString("id"));
                        room.setUsername(jsonObject.getString("username"));
                        room.setType(jsonObject.getString("type_room"));
                        room.setPrice(jsonObject.getString("price"));
                        room.setLenght(jsonObject.getString("lenght"));
                        room.setWidth(jsonObject.getString("width"));
                        room.setSlot_available(jsonObject.getString("slot_available"));
                        room.setOther(jsonObject.getString("other"));
                        room.setImage(jsonObject.getString("image_name"));
                        room.setCity(jsonObject.getString("city_name"));
                        room.setDistrict(jsonObject.getString("district_name"));
                        room.setWard(jsonObject.getString("ward_name"));
                        room.setStreet(jsonObject.getString("street_name"));
                        room.setVerified(jsonObject.getString("verified"));
                        room.setNumber(jsonObject.getString("number"));
                        room.setTime(jsonObject.getString("time_post"));
                        lstRoom.add(room);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(lstRoom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setupRecyclerView(ArrayList<Room> lstRoom) {
        AdminRecyclerViewAdapterSearch myAdapter = new AdminRecyclerViewAdapterSearch(this, lstRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
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
        if (id == R.id.action_room_back) {
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
