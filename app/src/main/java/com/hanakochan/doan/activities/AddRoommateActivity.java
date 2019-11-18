package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static com.hanakochan.doan.models.Config.ip_config;

public class AddRoommateActivity extends AppCompatActivity {
    private Spinner spinner_city, spinner_district, spinner_street, spinner_gender;
    private EditText edt_price;
    private Button btn_post;
    TextView tv_city, tv_district, tv_street, tv_gender;
    Toolbar toolbar;
    ProgressBar progressBar;
    SessionManager sessionManager;
    String getId, getUsername;
    String [] SpinnerListGender = {"", "Male","Female"};
    private static String URL_ADD = ip_config+"/post_roommate.php";


    ArrayAdapter<String> arrayAdapter_City;
    ArrayAdapter<String> arrayAdapter_District;
    ArrayAdapter<String> arrayAdapter_Street;
    ArrayAdapter<String> arrayAdapter_Gender;

    ArrayList<String> listCity = new ArrayList<>();
    ArrayList<String> listDistrict = new ArrayList<>();
    ArrayList<String> listStreet = new ArrayList<>();

    private static final String TAG = AddRoommateActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roommate);

        toolbar = findViewById(R.id.toolbar_add_room);
        setSupportActionBar(toolbar);
        AddRoommateActivity.this.setTitle("Add Room");

        sessionManager = new SessionManager(AddRoommateActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserId();
        getId = user.get(sessionManager.ID);
        getUsername = user.get(sessionManager.NAME);

        spinner_city = findViewById(R.id.id_city);
        spinner_district = findViewById(R.id.id_district);
        spinner_street = findViewById(R.id.id_street);
        spinner_gender = findViewById(R.id.id_gender);

        edt_price = findViewById(R.id.id_price);

        btn_post = findViewById(R.id.id_button);

        tv_city = findViewById(R.id.tv_spinner_city);
        tv_district = findViewById(R.id.tv_spinner_district);
        tv_street = findViewById(R.id.tv_spinner_street);
        tv_gender = findViewById(R.id.tv_spinner_gender);

        arrayAdapter_City = new ArrayAdapter<String>(this, R.layout.spinner_city_layout, R.id.tv_spinner_city, listCity);
        spinner_city.setAdapter(arrayAdapter_City);

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayList<String> district_list = new ArrayList<>();
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district_list.clear();
                listDistrict.clear();
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(AddRoommateActivity.this, selectedItemText, Toast.LENGTH_SHORT).show();
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

        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayList<String> street_list = new ArrayList<>();
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                street_list.clear();
                listStreet.clear();
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
//                Toast.makeText(AddRoomActivity.this, selectedItemText, Toast.LENGTH_SHORT).show();
                this.doDistrictInBackground(selectedItemText);
            }
            public void doDistrictInBackground(String text){

                InputStream inputStream_Street = null;

                String resultStreet = "";

                try {
                    HttpClient httpClient_Street = new DefaultHttpClient();
                    HttpPost httpPost_Street = new HttpPost(ip_config+"/load_data_street.php");
                    HttpResponse httpResponse_Street = httpClient_Street.execute(httpPost_Street);
                    HttpEntity entity_Street = httpResponse_Street.getEntity();
                    inputStream_Street = entity_Street.getContent();

                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_Street, "utf-8"));


                    String line ="";
                    while ((line = bufferedReader.readLine()) != null){
                        resultStreet += line;
                    }
                    inputStream_Street.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray(resultStreet);
                    for (int i = 0; i <= jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("district_name").equals(text)) {
                            street_list.add(jsonObject.getString("street_name"));
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                listStreet.addAll(street_list);
                arrayAdapter_Street.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        arrayAdapter_Street = new ArrayAdapter<String>(this, R.layout.spinner_street_layout, R.id.tv_spinner_street, listStreet);
        spinner_street.setAdapter(arrayAdapter_Street);


        arrayAdapter_Gender = new ArrayAdapter<String>(this, R.layout.spinner_gender_layout, R.id.tv_spinner_gender, SpinnerListGender);
        spinner_gender.setAdapter(arrayAdapter_Gender);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                addRoommate();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        BackTask backTask = new BackTask();
        backTask.execute();
    }
    public class BackTask extends AsyncTask<Void, Void, Void> {

        ArrayList<String> id_city_list;
        ArrayList<String> city_list;

        protected void onPreExecute(){
            super.onPreExecute();
            id_city_list = new ArrayList<>();
            city_list = new ArrayList<>();

        }
        protected Void doInBackground(Void...params){
            InputStream inputStream_City = null;

            String result = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(ip_config+"/load_data_city.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                inputStream_City = entity.getContent();

            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_City, "utf-8"));

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
    private void addRoommate(){
        final String city = spinner_city.getSelectedItem().toString().trim();
        final String district = spinner_district.getSelectedItem().toString().trim();
        final String street = spinner_street.getSelectedItem().toString().trim();
        final String gender = spinner_gender.getSelectedItem().toString().trim();
        final String price = edt_price.getText().toString().trim();

        if (TextUtils.isEmpty(price)) {
            Toast.makeText(AddRoommateActivity.this, "Please enter price", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success_text = jsonObject.getString("success");
                            if (success_text.equals("1")) {
                                Toast.makeText(AddRoommateActivity.this, "Post Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddRoommateActivity.this, "Post Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddRoommateActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("id_user", getId);
                params.put("username", getUsername);
                params.put("gender", gender);
                params.put("price", price);
                params.put("city_name", city);
                params.put("district_name", district);
                params.put("street_name", street);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddRoommateActivity.this);
        requestQueue.add(stringRequest);
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