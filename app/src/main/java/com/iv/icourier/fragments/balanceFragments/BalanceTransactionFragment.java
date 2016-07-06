package com.iv.icourier.fragments.balanceFragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.activities.BalanceActivity;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 21.03.16.
 */
public class BalanceTransactionFragment extends Fragment {
    private EditText    name;
    private EditText    lastName;
    private EditText    fullName;
    private EditText    dob;
    private EditText    card;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_balance_transaction, container, false);
        BalanceActivity.title.setText(getString(R.string.transaction));
        ICHelper.hideKeyboard(getActivity());

        initViews(root);
        setListeners(root);
        root.findViewById(R.id.balance_transaction_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return root;
    }
    private void initViews(View root) {
        lastName    =   Find.e(root, R.id.balance_transaction_last_name);
        name        =   Find.e(root, R.id.balance_transaction_name);
        fullName    =   Find.e(root, R.id.balance_transaction_full_name);
        dob         =   Find.e(root, R.id.balance_transaction_dob);
        card        =   Find.e(root, R.id.balance_transaction_card);
    }
    private void setListeners(View root) {
        Do.click(root, R.id.balance_transaction_ripple_last_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), lastName, null, null);
            }
        });
        Do.click(root, R.id.balance_transaction_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), name, null, null);
            }
        });
        Do.click(root, R.id.balance_transaction_ripple_full_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), fullName, null, null);
            }
        });
        Do.click(root, R.id.balance_transaction_ripple_dob, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getActivity(), dob, null);
            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.dialog_theme);
        dialog.setTitle(getString(R.string.confirm));
        dialog.setMessage("Вы действительно хотите вывести 420\u20BD с баланса?")
                .setPositiveButton(getString(R.string.get_out), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
