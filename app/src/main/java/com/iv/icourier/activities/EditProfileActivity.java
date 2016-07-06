package com.iv.icourier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.ICHelper;

public class EditProfileActivity extends AppCompatActivity {
    private SwitchCompat    shippingSwitch;
    private EditText        lastName;
    private EditText        name;
    private EditText        fullName;
    private EditText        dob;
    private EditText        phone;
    private EditText        secondPhone;
    private EditText        thirdPhone;
    private EditText        email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        setListeners();
    }
    private void initViews() {
        shippingSwitch  =   (SwitchCompat)  findViewById(R.id.edit_profile_shipping_switch);
        lastName        =   (EditText)      findViewById(R.id.edit_profile_last_name);
        name            =   (EditText)      findViewById(R.id.edit_profile_name);
        fullName        =   (EditText)      findViewById(R.id.edit_profile_full_name);
        dob             =   (EditText)      findViewById(R.id.edit_profile_dob);
        phone           =   (EditText)      findViewById(R.id.edit_profile_phone);
        secondPhone     =   (EditText)      findViewById(R.id.edit_profile_second_phone);
        thirdPhone      =   (EditText)      findViewById(R.id.edit_profile_third_phone);
        email           =   (EditText)      findViewById(R.id.edit_profile_email);

    }
    private void setListeners() {
        findViewById(R.id.edit_profile_shipping_ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shippingSwitch.setChecked(!shippingSwitch.isChecked());
            }
        });

        shippingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Do.vis(findViewById(R.id.edit_profile_text), findViewById(R.id.edit_profile_cars_linear));
            }
        });
        findViewById(R.id.edit_profile_add_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, AddCarActivity.class));
            }
        });
        findViewById(R.id.edit_profile_ripple_last_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(EditProfileActivity.this, lastName, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(EditProfileActivity.this, name, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_full_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(EditProfileActivity.this, fullName, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithCalendar(EditProfileActivity.this, dob, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInputPhone(EditProfileActivity.this, phone, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_second_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInputPhone(EditProfileActivity.this, secondPhone, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_third_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInputPhone(EditProfileActivity.this, thirdPhone, null, null);
            }
        });
        findViewById(R.id.edit_profile_ripple_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(EditProfileActivity.this, email, null, null);
            }
        });
        findViewById(R.id.balance_back_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, MainCourierActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
