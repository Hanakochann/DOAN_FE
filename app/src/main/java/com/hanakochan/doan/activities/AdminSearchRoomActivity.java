package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hanakochan.doan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminSearchRoomActivity extends AppCompatActivity {
    private Spinner spinner_city, spinner_district, spinner_ward, spinner_street, spinner_price;
    Button btn_Search;
    Toolbar toolbar;
    EditText username;

//    String[] SpinnerListPrice = {"<500,000", "<1,000,000", "<1,500,000"};
//
//    TextView tv_city, tv_district, tv_ward, tv_street, tv_price;
//    ArrayAdapter<String> arrayAdapter_City;
//    ArrayAdapter<String> arrayAdapter_District;
//    ArrayAdapter<String> arrayAdapter_Ward;
//    ArrayAdapter<String> arrayAdapter_Price;
//
//    ArrayList<String> listCity = new ArrayList<>();
//    ArrayList<String> listDistrict = new ArrayList<>();
//    ArrayList<String> listWard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_room);

        toolbar = findViewById(R.id.toolbar_add_room);
        setSupportActionBar(toolbar);
        AdminSearchRoomActivity.this.setTitle("Tìm kiếm");

        username = findViewById(R.id.username);
//        spinner_city = findViewById(R.id.id_city);
//        spinner_district = findViewById(R.id.id_district);
//        spinner_ward = findViewById(R.id.id_ward);
//        spinner_price = findViewById(R.id.id_price);
//
//        tv_city = findViewById(R.id.tv_spinner_city);
//        tv_district = findViewById(R.id.tv_spinner_district);
//        tv_ward = findViewById(R.id.tv_spinner_ward);
//        tv_street = findViewById(R.id.tv_spinner_street);
//        tv_price = findViewById(R.id.tv_spinner_price);
//
//        arrayAdapter_City = new ArrayAdapter<String>(this, R.layout.spinner_city_layout, R.id.tv_spinner_city, listCity);
//        spinner_city.setAdapter(arrayAdapter_City);
//
//        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            ArrayList<String> district_list = new ArrayList<>();
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                district_list.clear();
//                listDistrict.clear();
//                String selectedItemText = (String) adapterView.getItemAtPosition(i);
//                this.doDistrictInBackground(selectedItemText);
//            }
//
//            public void doDistrictInBackground(String text) {
//
//                InputStream inputStream_District = null;
//
//                String resultDistrict = "";
//
//                try {
//                    HttpClient httpClient_District = new DefaultHttpClient();
//                    HttpPost httpPost_District = new HttpPost(ip_config + "/load_data_district.php");
//                    HttpResponse httpResponse_District = httpClient_District.execute(httpPost_District);
//                    HttpEntity entity_District = httpResponse_District.getEntity();
//                    inputStream_District = entity_District.getContent();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_District, "utf-8"));
//
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        resultDistrict += line;
//                    }
//                    inputStream_District.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    JSONArray jsonArray = new JSONArray(resultDistrict);
//                    for (int i = 0; i <= jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.getString("province_name").equals(text)) {
//                            district_list.add(jsonObject.getString("district_name"));
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                listDistrict.addAll(district_list);
//                arrayAdapter_District.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        arrayAdapter_District = new ArrayAdapter<String>(this, R.layout.spinner_district_layout, R.id.tv_spinner_district, listDistrict);
//        spinner_district.setAdapter(arrayAdapter_District);
//
//        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            ArrayList<String> street_list = new ArrayList<>();
//            ArrayList<String> ward_list = new ArrayList<>();
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                street_list.clear();
//                ward_list.clear();
//                listWard.clear();
//                String selectedItemText1 = (String) adapterView.getItemAtPosition(i);
//                this.doDistrictInBackground1(selectedItemText1);
//            }
//
//            public void doDistrictInBackground1(String text) {
//
//                InputStream inputStream_Ward = null;
//
//                String resultWard = "";
//
//                try {
//                    HttpClient httpClient_Ward = new DefaultHttpClient();
//                    HttpPost httpPost_Ward = new HttpPost(ip_config + "/load_data_ward.php");
//                    HttpResponse httpResponse_Ward = httpClient_Ward.execute(httpPost_Ward);
//                    HttpEntity entity_Ward = httpResponse_Ward.getEntity();
//                    inputStream_Ward = entity_Ward.getContent();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_Ward, "utf-8"));
//
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        resultWard += line;
//                    }
//                    inputStream_Ward.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    JSONArray jsonArray = new JSONArray(resultWard);
//                    for (int i = 0; i <= jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.getString("district_name").equals(text)) {
//                            ward_list.add(jsonObject.getString("ward_name"));
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                listWard.addAll(ward_list);
//                arrayAdapter_Ward.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        arrayAdapter_Ward = new ArrayAdapter<String>(this, R.layout.spinner_ward_layout, R.id.tv_spinner_ward, listWard);
//        spinner_ward.setAdapter(arrayAdapter_Ward);
//
//        arrayAdapter_Price = new ArrayAdapter<String>(this, R.layout.spinner_price_layout, R.id.tv_spinner_price, SpinnerListPrice);
//        spinner_price.setAdapter(arrayAdapter_Price);

        btn_Search = findViewById(R.id.id_button_Searchroom);
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResult();
            }
        });
    }
//    protected void onStart() {
//        super.onStart();
//        AdminSearchRoomActivity.BackTask backTask = new AdminSearchRoomActivity.BackTask();
//        backTask.execute();
//    }
//
//    public class BackTask extends AsyncTask<Void, Void, Void> {
//
//        ArrayList<String> id_city_list;
//        ArrayList<String> city_list;
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//            id_city_list = new ArrayList<>();
//            city_list = new ArrayList<>();
//
//        }
//
//        protected Void doInBackground(Void... params) {
//            InputStream inputStream_City = null;
//            String result = "";
//            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(ip_config + "/load_data_city.php");
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity entity = httpResponse.getEntity();
//                inputStream_City = entity.getContent();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_City, "utf-8"));
//
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                inputStream_City.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                JSONArray jsonArray = new JSONArray(result);
//                for (int i = 0; i <= jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    city_list.add(jsonObject.getString("province_name"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void result) {
//            listCity.addAll(city_list);
//            arrayAdapter_City.notifyDataSetChanged();
//
//        }
//    }

    public void SearchResult(){
        Intent intent = new Intent(AdminSearchRoomActivity.this, AdminSearchResultActivity.class);
        intent.putExtra("name", username.getText().toString());
        startActivity(intent);
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

        if (id == R.id.action_search_room_back) {
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
