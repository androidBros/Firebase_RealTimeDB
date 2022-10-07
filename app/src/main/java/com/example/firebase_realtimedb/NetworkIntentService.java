package com.example.firebase_realtimedb;

import android.app.IntentService;
import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkIntentService extends IntentService {

    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            String jsonData = null;
            try {
                jsonData = request(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            sendJsonData(jsonData);

        }
        else{
            Log.d("NetworkIntentService","intent is null");
        }
    }
    public String request(URL url) {
        StringBuilder output = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(1000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                int resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while (true) {
                    line = reader.readLine();
                    if (line == null){
                        break;
                    }
                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        } catch (NetworkOnMainThreadException ex){
        } catch (Exception ex){
        }
        return output.toString();
    }

    private void sendJsonData(String data){
        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_SINGLE_TOP|
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("jsonData",data);
        startActivity(showIntent);
    }
}