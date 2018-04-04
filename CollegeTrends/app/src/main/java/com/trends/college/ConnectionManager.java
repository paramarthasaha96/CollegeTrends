package com.trends.college;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

class ConnectionManager {

    static boolean isNetworkAvailable(FragmentActivity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (activeNetworkInfo == null)
            Toast.makeText(activity.getApplicationContext(), "Check network connection", Toast.LENGTH_SHORT).show();
        return activeNetworkInfo != null;
    }

    static boolean hasInternetAccess(FragmentActivity activity) {
        boolean b = false;
        ConnectionTask connectionTask = new ConnectionTask();
        connectionTask.execute();
        try {
            b = connectionTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("Async", e.toString());
        }
        if (!b)
            Toast.makeText(activity.getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
        return b;
    }

    private static class ConnectionTask extends AsyncTask<Void, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean result = false;
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                result = (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("", "Error checking internet connection", e);
            }
            return result;
        }
    }
}