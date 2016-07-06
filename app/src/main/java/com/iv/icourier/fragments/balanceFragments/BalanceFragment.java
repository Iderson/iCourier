package com.iv.icourier.fragments.balanceFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iv.icourier.R;
import com.iv.icourier.activities.BalanceActivity;

/**
 * Created by iv on 21.03.16.
 */
public class BalanceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_balance, container, false);
        BalanceActivity.title.setText(getString(R.string.balance));

        root.findViewById(R.id.balance_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.balance_container, new BalanceSumFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }
        });
        root.findViewById(R.id.balance_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }
}
