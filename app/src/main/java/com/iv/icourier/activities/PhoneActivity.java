package com.iv.icourier.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.fragments.phoneFragments.PhoneFragment;
import com.iv.icourier.helpers.ICConst;

public class PhoneActivity extends AppCompatActivity {
    public static TextView  title;
    public static boolean   needAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        needAuth = getIntent().getBooleanExtra(ICConst.NEED_AUTH, false);
        if(needAuth)
            findViewById(R.id.phone_back).setVisibility(View.GONE);
        title   =   (TextView)  findViewById(R.id.phone_title);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.phone_container, new PhoneFragment())
                .commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
