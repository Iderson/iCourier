package com.iv.icourier.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iv.icourier.R;
import com.iv.icourier.fragments.orderFragments.profileFragments.QuestionnaireFragment;

public class QuestionnaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.questionnaire_container, new QuestionnaireFragment())
                .commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
