package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by Andread on 31.05.2016.
 */
public class NewPriceActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_new);

        ICHelper.showKeyboard(this, ((EditText)findViewById(R.id.price_number)));
        findViewById(R.id.price_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = ((EditText) findViewById(R.id.price_number)).getText().toString();
                if (string.length() > 0){
                    Intent intent = new Intent();
                    intent.putExtra("PRICE", string);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    ICHelper.showDialog(NewPriceActivity.this, "Введите пожалуйста цену", "Внимание", null);
                }
            }
        });
        findViewById(R.id.price_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
