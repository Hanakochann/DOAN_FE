package com.hanakochan.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static com.hanakochan.doan.Config.ip_config;

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinner_city, spinner_district, spinner_street, spinner_type_room;
    private EditText edt_number, edt_slot_available, edt_price, edt_other;
    private Button btn_post;
    TextView tv_city;
    Toolbar toolbar;

    ArrayAdapter<String> arrayAdapter_City;
    ArrayAdapter<String> arrayAdapter_District;
    ArrayAdapter<String> arrayAdapter_Street;

    ArrayList<String> listCity = new ArrayList<>();
    ArrayList<String> listDistrict = new ArrayList<>();
    ArrayList<String> listStreet = new ArrayList<>();

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

        tv_city = findViewById(R.id.tv_spinner_city);

        arrayAdapter_City = new ArrayAdapter<String>(this, R.layout.spinner_city_layout, R.id.tv_spinner_city, listCity);
        spinner_city.setAdapter(arrayAdapter_City);

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayList<String> id_district_list;
            ArrayList<String> id_district_city_list;
            ArrayList<String> district_list = new ArrayList<>();
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district_list.clear();
                listDistrict.clear();
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(AddRoomActivity.this, selectedItemText, Toast.LENGTH_SHORT).show();
                this.doDistrictInBackground(selectedItemText);
            }

            public void doDistrictInBackground(String text){

                InputStream inputStream_District = null;

                String resultDistrict = "";


                try {
                    HttpClient httpClient_District = new DefaultHttpClient();
                    HttpPost httpPost_District = new HttpPost(ip_config+"/load_data_district.php");
                    HttpResponse httpResponse_District = httpClient_District.execute(httpPost_District);
                    HttpEntity entity_District = httpResponse_District.getEntity();
                    inputStream_District = entity_District.getContent();

                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_District, "utf-8"));


                    String line ="";
                    while ((line = bufferedReader.readLine()) != null){
                        resultDistrict += line;
                    }
                    inputStream_District.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray(resultDistrict);
                    for (int i = 0; i <= jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("city_name").equals(text)) {
                            district_list.add(jsonObject.getString("district_name"));
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                listDistrict.addAll(district_list);
                arrayAdapter_District.notifyDataSetChanged();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        arrayAdapter_District = new ArrayAdapter<String>(this, R.layout.spinner_district_layout, R.id.tv_spinner_district, listDistrict);
        spinner_district.setAdapter(arrayAdapter_District);

        arrayAdapter_Street = new ArrayAdapter<String>(this, R.layout.spinner_street_layout, R.id.tv_spinner_street, listStreet);
        spinner_street.setAdapter(arrayAdapter_Street);



    }
    protected void onStart(){
        super.onStart();
        BackTask backTask = new BackTask();
        backTask.execute();
    }
    public class BackTask extends AsyncTask<Void, Void, Void>{
        ArrayList<String> id_city_list;
        ArrayList<String> city_list;


        ArrayList<String> id_street_list;
        ArrayList<String> id_street_distric_list;
        ArrayList<String> street_list;

        protected void onPreExecute(){
            super.onPreExecute();
            id_city_list = new ArrayList<>();
            city_list = new ArrayList<>();

//            listDistrict = new ArrayList<>();
//            listStreet = new ArrayList<>();
        }
        protected Void doInBackground(Void...params){
            InputStream inputStream_City = null;
//            InputStream inputStream_District = null;
//            InputStream inputStream_Street = null;
            String result = "";
//            String resultDistrict = "";
//            String resultStreet = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(ip_config+"/load_data_city.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                inputStream_City = entity.getContent();

//                HttpClient httpClient_District = new DefaultHttpClient();
//                HttpPost httpPost_District = new HttpPost("http://192.168.0.105/android_register_login/load_data_city.php");
//                HttpResponse httpResponse_District = httpClient_District.execute(httpPost_District);
//                HttpEntity entity_District = httpResponse_District.getEntity();
//                inputStream_District = entity_District.getContent();
//
//                HttpClient httpClient_Street = new DefaultHttpClient();
//                HttpPost httpPost_Street = new HttpPost("http://192.168.0.105/android_register_login/load_data_city.php");
//                HttpResponse httpResponse_Street = httpClient_Street.execute(httpPost_Street);
//                HttpEntity entity_Street = httpResponse_Street.getEntity();
//                inputStream_Street = entity_Street.getContent();
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_City, "utf-8"));
//                BufferedReader bufferedReader_District = new BufferedReader(new InputStreamReader(inputStream_District, "utf-8"));
//                BufferedReader bufferedReader_Street = new BufferedReader(new InputStreamReader(inputStream_Street, "utf-8"));

                String line ="";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                inputStream_City.close();

            }catch (IOException e){
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i <= jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id_city_list.add(jsonObject.getString("id"));
                    city_list.add(jsonObject.getString("city_name"));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Void result){
            listCity.addAll(city_list);
            arrayAdapter_City.notifyDataSetChanged();

        }
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
