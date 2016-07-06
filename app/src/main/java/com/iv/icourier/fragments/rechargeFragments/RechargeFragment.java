package com.iv.icourier.fragments.rechargeFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iv.icourier.R;
import com.iv.icourier.activities.RechargeActivity;
import com.iv.icourier.helpers.Find;

/**
 * Created by iv on 10.03.16.
 */
public class RechargeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_main, container, false);
        RechargeActivity.title.setText(getString(R.string.recharge));

        Find.price(root, R.id.balance_price_text);
        setListeners(root);

        return root;
    }
    private void setListeners(View root) {
        root.findViewById(R.id.recharge_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recharge_container, new CardFragment()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        root.findViewById(R.id.recharge_entity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recharge_container, new EntityFragment()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        root.findViewById(R.id.recharge_promo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recharge_container, new PromoFragment()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        root.findViewById(R.id.recharge_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
    }
}
