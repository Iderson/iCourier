package com.iv.icourier.fragments.rechargeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.activities.RechargeActivity;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 10.03.16.
 */
public class EntityFragment extends Fragment {
    private EditText    name;
    private EditText    inn;
    private EditText    bik;
    private EditText    checkingAccount;
    private EditText    email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_entity, container, false);
        RechargeActivity.title.setText(getString(R.string.entity_title));

        initViews(root);
        setListeners(root);

        return root;
    }
    private void initViews(View root) {
        checkingAccount     =   Find.e(root, R.id.recharge_entity_checking_account);
        inn                 =   Find.e(root, R.id.recharge_entity_inn);
        bik                 =   Find.e(root, R.id.recharge_entity_bik);
        name                =   Find.e(root, R.id.recharge_entity_name);
        email               =   Find.e(root, R.id.recharge_entity_email);
                                Find.price(root, R.id.entity_price_text);
                                Find.price(root, R.id.entity_price_edit);
        ((TextView)root.findViewById(R.id.entity_price_text)).append(" ):");
    }
    private void setListeners(View root) {
        Do.click(root, R.id.recharge_entity_ripple_checking_account, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), checkingAccount, null, null);
            }
        });
        Do.click(root, R.id.recharge_entity_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), name, null, null);
            }
        });
        Do.click(root, R.id.recharge_entity_ripple_inn, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), inn, null, null);
            }
        });
        Do.click(root, R.id.recharge_entity_ripple_bik, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), bik, null, null);
            }
        });
        Do.click(root, R.id.recharge_entity_ripple_email, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), email, null, null);
            }
        });
    }
}
