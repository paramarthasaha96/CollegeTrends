package com.trends.college;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

//here for this class we are using a singleton pattern

class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "user_details";
    private static final String KEY_ACCESS = "access";
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_ACCESS_CSRF = "access_csrf";
    private static final String KEY_REFRESH_CSRF = "refresh_csrf";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STUDID = "studid";
    private static final String KEY_STUDSTREAM = "studstream";
    private static final String KEY_STUDYEAR = "studyear";
    private static final String KEY_FACSTREAM = "facstream";
    private static final String KEY_FACID = "facid";
    private static final String KEY_ACCTYPE = "acctype";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    void userLogin(User user, LoginCookies cookies) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS, cookies.getAccessCookie());
        editor.putString(KEY_ACCESS_CSRF, cookies.getCsrfAccessCookie());
        editor.putString(KEY_REFRESH, cookies.getRefreshCookie());
        editor.putString(KEY_REFRESH_CSRF, cookies.getCsrfRefreshCookie());
        editor.putString(KEY_FNAME, user.getfName());
        editor.putString(KEY_LNAME, user.getlName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_EMAIL, user.getEmail().equals("null") ? "<unspecified>" : user.getEmail());
        if(user.getAccountType()==0){
            editor.putString(KEY_STUDID, user.getStudent_id());
            editor.putString(KEY_STUDSTREAM, user.getStudent_stream());
            editor.putString(KEY_STUDYEAR, user.getStudent_year());
            editor.putInt(KEY_ACCTYPE, user.getAccountType());
        }
        else {
            editor.putString(KEY_FACID, user.getFaculty_id());
            editor.putString(KEY_FACSTREAM, user.getFaculty_stream());
            editor.putInt(KEY_ACCTYPE, user.getAccountType());
        }
        editor.commit();
    }

    void setAccessCookie(Map<String, String> cookieList) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS, cookieList.get(LoginCookies.JWT_ACCESS_COOKIE_NAME));
        editor.putString(KEY_ACCESS_CSRF, cookieList.get(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME));
        editor.commit();
    }

    //this method will checker whether user is already logged in or not
    boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE, null) != null && sharedPreferences.getString(KEY_ACCESS, null) != null;
    }

    LoginCookies getLogin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Map<String, String> cookieList = new HashMap<>();
        cookieList.put(LoginCookies.JWT_ACCESS_COOKIE_NAME, sharedPreferences.getString(KEY_ACCESS, null));
        cookieList.put(LoginCookies.JWT_REFRESH_COOKIE_NAME, sharedPreferences.getString(KEY_REFRESH, null));
        cookieList.put(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME, sharedPreferences.getString(KEY_ACCESS_CSRF, null));
        cookieList.put(LoginCookies.JWT_REFRESH_CSRF_COOKIE_NAME, sharedPreferences.getString(KEY_REFRESH_CSRF, null));
        return new LoginCookies(cookieList);
    }

    //this method will give the logged in user
    User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null)
        );
    }

    //this method will logout the user
    void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}