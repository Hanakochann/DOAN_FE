package com.hanakochan.doan.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.hanakochan.doan.models.Config.ip_config;


public class AdminProfileFragment extends Fragment {


    private AdminProfileFragment.OnFragmentInteractionListener mListener;
    private EditText edtPhone;
    private Spinner spinner_city, spinner_gender;
    private TextView tvName, tvEmail, tvBirthday, tv_city;
    private Button btnSelectPhoto, btnLogout, btnUpdate, btnPickDate;
    private CircleImageView profilePhoto;
    Bitmap bitmap;
    SessionManager sessionManager;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    private static final int IMAGE_REQUEST = 1;
    private static final String TAG = ProfileFragment.class.getSimpleName();

    StorageReference storageReference;
    private static String URL_UPDATE = ip_config + "/update_profile.php";
    private static String URL_READ = ip_config + "/load_profile.php";
    private static String URL_UPLOAD = ip_config + "/upload_image.php";

    ArrayAdapter<String> arrayAdapter_City;
    ArrayAdapter<String> arrayAdapter_Gender;
    ArrayList<String> listCity = new ArrayList<>();
    ProgressDialog progressDialog;
    String getId;
    String[] SpinnerListGender = {"Nam", "Nữ", "Khác"};

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();


        profilePhoto = (CircleImageView) view.findViewById(R.id.profile_id);


        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);
        tvBirthday = (TextView) view.findViewById(R.id.tv_birthday);

        tv_city = view.findViewById(R.id.tv_spinner_city);

        spinner_gender = (Spinner) view.findViewById(R.id.gender_id);

        spinner_city =  view.findViewById(R.id.hometown_id);

        arrayAdapter_City = new ArrayAdapter<String>(getActivity(), R.layout.spinner_city_layout, R.id.tv_spinner_city, listCity);
        spinner_city.setAdapter(arrayAdapter_City);

        arrayAdapter_Gender = new ArrayAdapter<String>(getActivity(), R.layout.spinner_gender_layout, R.id.tv_spinner_gender, SpinnerListGender);
        spinner_gender.setAdapter(arrayAdapter_Gender);

        edtPhone = (EditText) view.findViewById(R.id.edt_phone);

        btnSelectPhoto = (Button) view.findViewById(R.id.btn_Select_Photo);
        btnPickDate = (Button) view.findViewById(R.id.btnpPickDate);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);


        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                tvBirthday.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutAdmin();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        btnSelectPhoto = (Button) view.findViewById(R.id.btn_Select_Photo);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        return view;
    }
    public void onStart() {
        super.onStart();
        AdminProfileFragment.BackTask backTask = new AdminProfileFragment.BackTask();
        backTask.execute();
    }

    public class BackTask extends AsyncTask<Void, Void, Void> {

        ArrayList<String> id_city_list;
        ArrayList<String> city_list;

        protected void onPreExecute() {
            super.onPreExecute();
            id_city_list = new ArrayList<>();
            city_list = new ArrayList<>();

        }

        protected Void doInBackground(Void... params) {
            InputStream inputStream_City = null;
            String result = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(ip_config + "/load_data_city.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                inputStream_City = entity.getContent();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_City, "utf-8"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream_City.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    city_list.add(jsonObject.getString("province_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            listCity.addAll(city_list);
            arrayAdapter_City.notifyDataSetChanged();

        }
    }

    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("username").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strBirthday = object.getString("birthday").trim();
                                    String strGender = object.getString("gender").trim();
                                    String strHometown = object.getString("hometown").trim();
                                    String strPhone = object.getString("phone").trim();
                                    String strPhoto = object.getString("photo").trim();

                                    sessionManager.createSessionImageUser(strPhoto);

                                    tvName.setText(strName);
                                    tvEmail.setText(strEmail);
                                    if (strBirthday == "null") {
                                        tvBirthday.setText("");
                                    } else {
                                        tvBirthday.setText(strBirthday);
                                    }
                                    if (strPhone == "null") {
                                        edtPhone.setText("");
                                    } else {
                                        edtPhone.setText(strPhone);
                                    }
                                    if (strGender != null) {
                                        int spinnerPositionGender = arrayAdapter_Gender.getPosition(strGender);
                                        spinner_gender.setSelection(spinnerPositionGender);
                                    }
                                    if (strHometown != null) {
                                        int spinnerPositionHometown = arrayAdapter_City.getPosition(strHometown);
                                        spinner_city.setSelection(spinnerPositionHometown);
                                    }

                                    if (strPhoto != null) {
                                        Picasso.get().load(strPhoto).into(profilePhoto);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Không thể hiển thị chi tiết!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDetail();
    }

    private void Update() {
        final String birthday = tvBirthday.getText().toString().trim();
        final String hometown = spinner_city.getSelectedItem().toString().trim();
        final String gender = spinner_gender.getSelectedItem().toString().trim();
        final String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(getActivity(), "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(hometown)) {
            Toast.makeText(getActivity(), "Vui lòng nhập quê quán", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(getActivity(), "Vui lòng nhập giới tính", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success_text = jsonObject.getString("success");
                            if (success_text.equals("1")) {
                                Toast.makeText(getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                params.put("birthday", birthday);
                params.put("phone", phone);
                params.put("gender", gender);
                params.put("hometown", hometown);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    ;

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select photo"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profilePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadImage(getId, getStringImage(bitmap));
        }
    }

    private void UploadImage(final String id, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Không thành công!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Thử lại!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
