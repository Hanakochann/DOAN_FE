package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.Config.ip_config;


public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText edt_Name, edt_Email, edt_Pass, edt_ConfirmPass;
    private ProgressBar progressBar;
    private ProgressDialog loadingBar;
    String URL_REGIST = ip_config+"/user_register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_Name = (EditText) findViewById(R.id.edt_name);
        edt_Email = (EditText) findViewById(R.id.edt_email);
        edt_Pass = (EditText) findViewById(R.id.edt_pass);
        edt_ConfirmPass = (EditText) findViewById(R.id.edt_confirm_pass);
        btnRegister = (Button) findViewById(R.id.btn_register);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_id);
        loadingBar = new ProgressDialog(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

    }

    private void Register() {
        final String username = edt_Name.getText().toString().trim();
        final String email = edt_Email.getText().toString().trim();
        final String password = edt_Pass.getText().toString().trim();
        String confirmP = edt_ConfirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirmP)) {
            Toast.makeText(RegisterActivity.this, "Please enter your confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success_text = jsonObject.getString("success");
                            if(success_text.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                    }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
