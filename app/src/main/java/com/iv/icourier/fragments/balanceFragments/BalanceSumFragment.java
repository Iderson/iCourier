package com.iv.icourier.fragments.balanceFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.activities.BalanceActivity;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 21.03.16.
 */
public class BalanceSumFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_balance_sum, container, false);
        BalanceActivity.title.setText(getString(R.string.sum_push));

        EditText sum = (EditText) root.findViewById(R.id.balance_sum);
        ICHelper.showKeyboard(getActivity(), sum);

        root.findViewById(R.id.balance_sum_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.balance_container, new BalanceTransactionFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }
        });

        return root;
    }
}
