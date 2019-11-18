package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LoginActivity extends AppCompatActivity {
    public EditText edt_email;
    public EditText edt_password;
    public Button buttonLog, buttonCreate;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    String URL_LOGIN = ip_config+"/user_login.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        edt_email = findViewById(R.id.edt_username);
        edt_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_password = findViewById(R.id.edt_password);
        buttonLog = findViewById(R.id.btnLogin);
        buttonCreate = findViewById(R.id.btnCreate);
        progressDialog = new ProgressDialog(this);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edt_email.getText().toString().trim();
                final String password = edt_password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login(email, password);
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void Login(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("username").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();

                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("username",name);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                }
                                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
