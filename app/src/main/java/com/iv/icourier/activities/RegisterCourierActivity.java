package com.iv.icourier.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.fragments.registerCourierFragments.CourierTutorialFragment;

public class RegisterCourierActivity extends AppCompatActivity {
    public  static  TextView    title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_courier);
        initViews();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.courier_container, new CourierTutorialFragment())
                .commitAllowingStateLoss();
    }
    private void initViews() {
        title = (TextView) findViewById(R.id.courier_title);

        findViewById(R.id.register_courier_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
