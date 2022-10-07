package com.example.firebase_realtimedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();
    Button add_btn;
    Camp camp = new Camp();

    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatActivity appCompatActivity = this;
        ArrayList<String> camps = new ArrayList<>();

        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkRequest.RequestGoCampingNetwork.requestCampInfo(appCompatActivity,
                        getApplicationContext(),
                        35.6633644,
                        129.06417);



            }
        });
    }

    public void writeCampInfo(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray campsInfo = jsonObject.getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        for(int i=0;i<campsInfo.length();i++){
            writeDBCamp("Camp"+i, campsInfo.getJSONObject(i).getString("facltNm"),
                    campsInfo.getJSONObject(i).getString("addr1")+campsInfo.getJSONObject(i).getString("addr2"));

        }
    }
    public void writeDBCamp(String CampID, String campname, String camplocation){
        Camp camp = new Camp(campname, camplocation);
        myRef.child("Camps").child(CampID).setValue(camp);
    }
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            try {
                processIntent(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) throws JSONException {
        String jsonData = intent.getStringExtra("jsonData");
        Log.d("jsonData",jsonData);
        writeCampInfo(jsonData);
    }
}

