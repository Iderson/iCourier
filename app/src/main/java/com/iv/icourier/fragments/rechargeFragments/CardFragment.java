package com.iv.icourier.fragments.rechargeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.activities.RechargeActivity;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 10.03.16.
 */
public class CardFragment extends Fragment {
    private EditText    card;
    private EditText    name;
    private EditText    date;
    private EditText    cvv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_card, container, false);
        RechargeActivity.title.setText(getString(R.string.card));

        initViews(root);
        setListeners(root);

        return root;
    }
    private void initViews(View root) {
        card    =   Find.e(root, R.id.recharge_card_number);
        name    =   Find.e(root, R.id.recharge_card_name);
        date    =   Find.e(root, R.id.recharge_card_date);
        cvv     =   Find.e(root, R.id.recharge_card_cvv);
                    Find.price(root, R.id.recharge_price_text);
    }
    private void setListeners(final View root) {
        Do.click(root, R.id.recharge_card_ripple_number, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), card, null, null);
            }
        });
        Do.click(root, R.id.recharge_card_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), name, null, null);
            }
        });
        Do.click(root, R.id.recharge_card_ripple_date, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getActivity(), date, null);
            }
        });
        Do.click(root, R.id.recharge_card_ripple_cvv, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), cvv, null, null);
            }
        });
    }
}
