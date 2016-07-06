package com.iv.icourier.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.fragments.balanceFragments.BalanceFragment;

public class BalanceActivity extends AppCompatActivity {
    public static TextView  title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        title   =   (TextView)  findViewById(R.id.balance_title);
        findViewById(R.id.balance_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.balance_container, new BalanceFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
