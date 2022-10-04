package com.example.firebase_realtimedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();
    Button add_btn;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeCampInfo("Camp"+i,i+"번 째 캠핑장",i+"번 째 도시");
                i++;
            }
        });
    }

    public void writeCampInfo(String campID, String campname, String location){
        Camp camp = new Camp(campname, location);
        myRef.child("Camps").child(campID).setValue(camp);

    }
}
