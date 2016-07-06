package com.iv.icourier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.fragments.rechargeFragments.RechargeFragment;

public class RechargeActivity extends AppCompatActivity {
    public static TextView  title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        title  =   (TextView)  findViewById(R.id.recharge_title);

        getSupportFragmentManager().beginTransaction().add(R.id.recharge_container, new RechargeFragment()).commit();
        findViewById(R.id.recharge_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        else super.onBackPressed();
    }
}
