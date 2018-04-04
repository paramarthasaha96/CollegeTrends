package com.trends.college;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

public class SignupActivity extends AppCompatActivity {
    private String name;
    private String email;
    private String year;
    private String ph;
    private String pass;
    private String repass;
    private int accType;
    private int stream;
    private EditText nameView;
    private EditText emailView;
    private EditText phoneView;
    private EditText yearView;
    private EditText passView;
    private EditText repassView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        requestQueue = Volley.newRequestQueue(this);
        final Spinner acc = findViewById(R.id.account_type);
        final Spinner str = findViewById(R.id.stream);
        nameView = findViewById(R.id.input_name);
        emailView = findViewById(R.id.input_email);
        phoneView = findViewById(R.id.input_phone);
        yearView = findViewById(R.id.input_year);
        passView = findViewById(R.id.input_password);
        repassView = findViewById(R.id.input_reEnterPassword);
        Button register = findViewById(R.id.btn_signup);
        TextView login = findViewById(R.id.link_login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameView.getText().toString();
                email = emailView.getText().toString();
                ph = phoneView.getText().toString();
                year = yearView.getText().toString();
                pass = passView.getText().toString();
                repass = repassView.getText().toString();
                accType = acc.getSelectedItemPosition();
                stream = str.getSelectedItemPosition();
                if (check()) registerUser();
            }
        });
        final Intent i = new Intent(this, LoginActivity.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(i);
            }
        });
    }

    private boolean check() {
        if (name.length() == 0) {
            Snackbar.make(getCurrentFocus(), "Enter name", Snackbar.LENGTH_LONG).setAction("Enter", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameView.requestFocus();
                }
            }).setActionTextColor(Color.WHITE).show();
            return false;
        }
        if (ph.length() != 10) {
            Snackbar.make(getCurrentFocus(), "Enter 10 digit phone no.", Snackbar.LENGTH_LONG).setAction("Enter", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneView.requestFocus();
                }
            }).setActionTextColor(Color.WHITE).show();
            return false;
        }
        if (accType == 0 && year.length() != 1 && (!Character.isDigit(year.charAt(0)))) {
            Snackbar.make(getCurrentFocus(), "Correct year required", Snackbar.LENGTH_LONG).setAction("Enter", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yearView.requestFocus();
                }
            }).setActionTextColor(Color.WHITE).show();
            return false;
        }
        if (pass.length() < 6) {
            Snackbar.make(getCurrentFocus(), "Password requires atleast 6 characters", Snackbar.LENGTH_LONG).setAction("Enter", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passView.requestFocus();
                }
            }).setActionTextColor(Color.WHITE).show();
            return false;
        }
        if (repass.length() == 0 || (!repass.equals(pass))) {
            Snackbar.make(getCurrentFocus(), "Passwords do not match", Snackbar.LENGTH_LONG).setAction("Correct", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repassView.requestFocus();
                }
            }).setActionTextColor(Color.WHITE).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        if ((!ConnectionManager.isNetworkAvailable(this)) || (!ConnectionManager.hasInternetAccess(this)))
            return;
        String url = accType == 0 ? ServerURL.REGISTER_URL_STUDENTS : ServerURL.REGISTER_URL_FACULTY;
        StringRequest stringRequestRegister = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt("response");
                            if (code == 0) {
                                Toast.makeText(getApplicationContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                                loginUser();
                            } else
                                Toast.makeText(getApplicationContext(), "Error registering", Toast.LENGTH_SHORT).show();
                        } catch (JSONException | NullPointerException e) {
                            Toast.makeText(getApplicationContext(), "Error registering", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error registering", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", name);
                params.put("lname", "");
                params.put("email", "");
                params.put("mobile", ph);
                params.put("pwd", pass);
                params.put("stream", String.valueOf(stream));
                if (accType == 0) params.put("year", year);
                return params;
            }
        };
        requestQueue.add(stringRequestRegister);
    }

    private void loginUser() {
        if ((!ConnectionManager.isNetworkAvailable(this)) || (!ConnectionManager.hasInternetAccess(this)))
            return;
        final CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        String url = (accType == 0) ? ServerURL.LOGIN_URL_STUDENT : ServerURL.LOGIN_URL_FACULTY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userObj = new JSONObject(response);
                            List<HttpCookie> cookieList = manager.getCookieStore().getCookies();
                            if (userObj.getInt("success") == 1 && accType == 0) {
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
                            } else if (userObj.getInt("success") == 1 && accType == 1) {
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
                            } else
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}