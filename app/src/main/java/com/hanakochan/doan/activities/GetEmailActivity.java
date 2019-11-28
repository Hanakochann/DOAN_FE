package com.hanakochan.doan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class GetEmailActivity extends AppCompatActivity {
    EditText edt_email;
    Button btn_confirm;
    ProgressBar progressBar;
    String URL_CHECK = ip_config+"/checkEmail.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_email);

        sessionManager = new SessionManager(this);

        edt_email = findViewById(R.id.edt_email);
        btn_confirm = findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmailExits();
            }
        });
    }
    private void CheckEmailExits() {
        final String email = edt_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(GetEmailActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success_text = jsonObject.getString("success");
                            if (success_text.equals("1")) {
                                sessionManager.createSessionEmail(email);
                                Intent intent = new Intent(GetEmailActivity.this, ForgotPasswordActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(GetEmailActivity.this, "Email này chưa được đăng ký!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GetEmailActivity.this, "Thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GetEmailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
