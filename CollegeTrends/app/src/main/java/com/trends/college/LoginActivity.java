package com.trends.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private String ph;
    private String pass;
    private int acc;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        final EditText phone = findViewById(R.id.input_phone);
        final EditText password = findViewById(R.id.input_password);
        final Intent i = new Intent(this, SignupActivity.class);
        final Spinner accType = findViewById(R.id.account_type);
        Button login = findViewById(R.id.btn_login);
        TextView signUp = findViewById(R.id.link_signup);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setError(null);
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setError(null);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph = phone.getText().toString();
                pass = password.getText().toString();
                acc = accType.getSelectedItemPosition();
                loginUser();
            }
        });
    }

    private void loginUser() {
        if ((!ConnectionManager.isNetworkAvailable(this)) || (!ConnectionManager.hasInternetAccess(this)))
            return;
        final CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        String url = (acc == 0) ? ServerURL.LOGIN_URL_STUDENT : ServerURL.LOGIN_URL_FACULTY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject userObj = new JSONObject(response);
                            List<HttpCookie> cookieList = manager.getCookieStore().getCookies();
                            if (userObj.getInt("success") == 1 && acc==0) {
                                Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT).show();
                                User user = new User(
                                        userObj.getString("user_fname"),
                                        userObj.getString("user_lname"),
                                        userObj.getString("user_email"),
                                        userObj.getString("user_mobile"),
                                        userObj.getString("student_id"),
                                        userObj.getString("student_year"),
                                        userObj.getString("student_stream"),
                                        0
                                );
                                LoginCookies cookies = new LoginCookies(cookieList);
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user, cookies);
                                finish();
                                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                            }else if (userObj.getInt("success") == 1 && acc==1) {
                                Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT).show();
                                User user = new User(
                                        userObj.getString("user_fname"),
                                        userObj.getString("user_lname"),
                                        userObj.getString("user_email"),
                                        userObj.getString("user_mobile"),
                                        userObj.getString("faculty_id"),
                                        userObj.getString("faculty_stream"),
                                        1
                                );
                                LoginCookies cookies = new LoginCookies(cookieList);
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user, cookies);
                                finish();
                                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                            }else
                                Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
                        } catch (JSONException | NullPointerException e) {
                            Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null)
                            return;
                        String statusCode = String.valueOf(error.networkResponse.statusCode), body;
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            JSONObject jsonObject = new JSONObject(body);
                            if (jsonObject.getInt("success") == 0 && (statusCode.equals("400") || statusCode.equals("401")))
                                Toast.makeText(getApplicationContext(), "Wrong phone no. or password\nError code" +
                                        statusCode, Toast.LENGTH_SHORT).show();

                        } catch (UnsupportedEncodingException | JSONException e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", ph);
                params.put("pwd", pass);
                params.put("remember", "true");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}