package com.iv.icourier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iv.icourier.R;

/**
 * Created by Andread on 30.05.2016.
 */
public class EntranceActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.authorize_client).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntranceActivity.this, MainClientActivity.class));
            }
        });
        findViewById(R.id.authorize_courier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntranceActivity.this, RegisterCourierActivity.class));
            }
        });
    }

}
