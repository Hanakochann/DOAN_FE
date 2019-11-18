package com.hanakochan.doan.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hanakochan.doan.models.SessionManager;
import com.hanakochan.doan.R;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.hanakochan.doan.models.Config.ip_config;


public class ProfileFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mFirebaseAuth;
    private TextView mDisplayDate;
    private EditText edtPhone;
    private MaterialBetterSpinner betterSpinnerHometown, betterSpinnerGender;
    private TextView tvName, tvEmail, tvBirthday;
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
    private Uri imageUri;
    private StorageTask uploadTask;

    StorageReference storageReference;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private static String URL_UPDATE = ip_config+"/update_profile.php";
    private static String URL_READ = ip_config+"/load_profile.php";
    private static String URL_UPLOAD = ip_config+"/upload_image.php";
    ProgressDialog progressDialog;
    String getId;
    String [] SpinnerListCountry = {"", "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh"
            ,"Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng","Cần Thơ","Đà Nẵng"
            ,"Đắk Lắk","Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp","Gia Lai","Hà Giang", "Hà Nam","Hà Nội"
            ,"Hải Phòng", "Hà Tĩnh", "Hải Dương","Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang"
            , "Kon Tum", "Lai Châu", "Lâm Đồng","Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An","Ninh Bình"
            , "Ninh Thuận", "Phú Thọ","Phú Yên","Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị"
            , "Sóc Trăng", "Sơn La","Tây Ninh","Thái Bình", "Thái Nguyên", "Thanh Hóa", "TP Hồ Chí Minh"
            , "Thừa Thiên Huế","Tiền Giang", "Trà Vinh","Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", };
    String [] SpinnerListGender = {"", "Male","Female","Other"};

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();


        profilePhoto = (CircleImageView) view.findViewById(R.id.profile_id);


        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);
        tvBirthday = (TextView) view.findViewById(R.id.tv_birthday);

        betterSpinnerGender = (MaterialBetterSpinner) view.findViewById(R.id.gender_id);
        betterSpinnerHometown = (MaterialBetterSpinner) view.findViewById(R.id.hometown_id);


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

        ArrayAdapter<String> arrayAdapterCountry = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line,SpinnerListCountry);
        betterSpinnerHometown.setAdapter(arrayAdapterCountry);

        ArrayAdapter<String> arrayAdapterGender = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line,SpinnerListGender);
        betterSpinnerGender.setAdapter(arrayAdapterGender);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
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

    private void getUserDetail(){
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



                                    tvName.setText(strName);
                                    tvEmail.setText(strEmail);
                                    if (strBirthday== "null"){
                                        tvBirthday.setText("");
                                    }else {
                                        tvBirthday.setText(strBirthday);
                                    }
                                    if (strPhone == "null"){
                                        edtPhone.setText("");
                                    }else {
                                        edtPhone.setText(strPhone);
                                    }
                                    if (strGender == "null"){
                                        betterSpinnerGender.setText("");
                                    }else {
                                        betterSpinnerGender.setText(strGender);
                                    }
                                    if (strHometown == "null"){
                                        betterSpinnerHometown.setText("");
                                    }else {
                                        betterSpinnerHometown.setText(strHometown);
                                    }

                                    if(strPhoto != null){
                                        Picasso.get().load(strPhoto).into(profilePhoto);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error reading detail!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
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

    private void Update(){
        final String birthday = tvBirthday.getText().toString().trim();
        final String hometown = betterSpinnerHometown.getText().toString().trim();
        final String gender = betterSpinnerGender.getText().toString().trim();
        final String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(getActivity(), "Please select your birthday", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(hometown)) {
            Toast.makeText(getActivity(), "Please select your hometown", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(getActivity(), "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getActivity(), "Update Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Update Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
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
    };

    private void openGallery(){
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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profilePhoto.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
            UploadImage(getId, getStringImage(bitmap));
        }
    }

    private void UploadImage(final String id, final  String photo) {
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
                        if (success.equals("1")){
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!"+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
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

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }


    private String getFileExtension(Uri uri){
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
