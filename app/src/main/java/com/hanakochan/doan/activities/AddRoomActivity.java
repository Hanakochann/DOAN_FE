package com.hanakochan.doan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hanakochan.doan.R;
import com.hanakochan.doan.fragments.HistoryFragment;
import com.hanakochan.doan.fragments.HomepageFragment;
import com.hanakochan.doan.fragments.MessageFragment;
import com.hanakochan.doan.fragments.ProfileFragment;
import com.hanakochan.doan.fragments.RoommateFragment;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinner_city, spinner_district, spinner_type_room;
    private EditText edt_street, edt_number, edt_slot_available, edt_price, edt_other;
    private Button btn_upphoto, btn_post;
    TextView tv_city, tv_district, tv_type;
    ImageView imageView;
    Bitmap bitmap;
    Toolbar toolbar;
    ProgressBar progressBar;
    SessionManager sessionManager;

    private static final int IMAGE_REQUEST = 1;
    String getId, getUsername;
    String [] SpinnerListType = {"", "Ký túc xá","Phòng trọ","Căn hộ"};

    private static String URL_ADD = ip_config+"/post_room.php";
    private static String URL_UPLOAD = ip_config+"/upload_room_photo.php";
    private static final String TAG = AddRoomActivity.class.getSimpleName();

    ArrayAdapter<String> arrayAdapter_City;
    ArrayAdapter<String> arrayAdapter_District;
    ArrayAdapter<String> arrayAdapter_TypeRoom;

    ArrayList<String> listCity = new ArrayList<>();
    ArrayList<String> listDistrict = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_homepage:
                    transaction.replace(R.id.container,new HomepageFragment()).commit();
                    return true;
                case R.id.navigation_roommate:
                    transaction.replace(R.id.container,new RoommateFragment()).commit();
                    return true;
                case R.id.navigation_history:
                    transaction.replace(R.id.container,new HistoryFragment()).commit();
                    return true;
                case R.id.navigation_message:
                    transaction.replace(R.id.container,new MessageFragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    transaction.replace(R.id.container,new ProfileFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        toolbar = findViewById(R.id.toolbar_add_room);
        setSupportActionBar(toolbar);
        AddRoomActivity.this.setTitle("Add Room");

        sessionManager = new SessionManager(AddRoomActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getUsername = user.get(sessionManager.NAME);

        spinner_city = findViewById(R.id.id_city);
        spinner_district = findViewById(R.id.id_district);
        spinner_type_room = findViewById(R.id.id_room_type);

        edt_street = findViewById(R.id.id_street);
        edt_number = findViewById(R.id.id_number);
        edt_price = findViewById(R.id.id_price);
        edt_slot_available = findViewById(R.id.id_slot);
        edt_other = findViewById(R.id.id_other);

        imageView = findViewById(R.id.img_upload);

        btn_upphoto = findViewById(R.id.btn_Select_Roomphoto);
        btn_post = findViewById(R.id.id_button);

        tv_city = findViewById(R.id.tv_spinner_city);
        tv_district = findViewById(R.id.tv_spinner_district);
        tv_type = findViewById(R.id.tv_spinner_typeroom);

        arrayAdapter_City = new ArrayAdapter<String>(this, R.layout.spinner_city_layout, R.id.tv_spinner_city, listCity);
        spinner_city.setAdapter(arrayAdapter_City);

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayList<String> district_list = new ArrayList<>();
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district_list.clear();
                listDistrict.clear();
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
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

        arrayAdapter_TypeRoom = new ArrayAdapter<String>(this, R.layout.spinner_typeroom_layout, R.id.tv_spinner_typeroom, SpinnerListType);
        spinner_type_room.setAdapter(arrayAdapter_TypeRoom);

        btn_upphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryPhoto();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                addRoom();
            }
        });
    }

    private void openGalleryPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select photo"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(AddRoomActivity.this.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }


    protected void onStart(){
        super.onStart();
        BackTask backTask = new BackTask();
        backTask.execute();
    }
    public class BackTask extends AsyncTask<Void, Void, Void>{

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
    private void addRoom(){
        final String city = spinner_city.getSelectedItem().toString().trim();
        final String district = spinner_district.getSelectedItem().toString().trim();
        final String street = edt_street.getText().toString().trim();
        final String number = edt_number.getText().toString().trim();
        final String type = spinner_type_room.getSelectedItem().toString().trim();
        final String price = edt_price.getText().toString().trim();
        final String slot = edt_slot_available.getText().toString().trim();
        final String other = edt_other.getText().toString().trim();
        final String photo = getStringImage(bitmap).trim();

        if (TextUtils.isEmpty(street)) {
            Toast.makeText(AddRoomActivity.this, "Please enter your street", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(AddRoomActivity.this, "Please enter your number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(AddRoomActivity.this, "Please enter price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(slot)) {
            Toast.makeText(AddRoomActivity.this, "Please enter slot vailable", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(other)) {
            Toast.makeText(AddRoomActivity.this, "Please enter something special", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AddRoomActivity.this, "Đăng phòng thành công!", Toast.LENGTH_SHORT).show();
                                Countinue();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddRoomActivity.this, "Đăng phòng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddRoomActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("id_user", getId);
                params.put("img_room", photo);
                params.put("username", getUsername);
                params.put("type_room", type);
                params.put("price", price);
                params.put("slot_available", slot);
                params.put("other", other);
                params.put("number", number);
                params.put("city_name", city);
                params.put("district_name", district);
                params.put("street_name", street);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddRoomActivity.this);
        requestQueue.add(stringRequest);
    }

    private void Countinue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomActivity.this);
        builder.setMessage("Bạn có muốn đăng phòng tiếp hay không?");
        DialogInterface.OnClickListener dOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        dialogInterface.cancel();
                        Intent intent = new Intent(AddRoomActivity.this, AddRoomActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };
        builder.setPositiveButton("Có", dOnClickListener);
        builder.setNegativeButton("Không",dOnClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
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
