package com.iv.icourier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.BlankActivity;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by Andread on 19.05.2016.
 */
public class SizeFragment extends Fragment
        implements BlankActivity.OnBackPressedListener{

    private EditText length;
    private EditText width;
    private EditText height;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_size, container, false);
//        RechargeActivity.title.setText(getString(R.string.recharge));

        initViews(root);
        setListeners(root);

        return root;
    }
    private void initViews(View root) {
        length  =   Find.e(root, R.id.size_length);
        width   =   Find.e(root, R.id.size_width);
        height  =   Find.e(root, R.id.size_height);
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void setListeners(View root) {
        Do.click(root, R.id.size_ripple_length, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInputNumber(getContext(), length, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setCargoLength(_args.getString(""));
                    }
                }, null);
            }
        });
        Do.click(root, R.id.size_ripple_width, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInputNumber(getContext(), width, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setCargoWidth(_args.getString(""));
                    }
                }, null);

            }
        });
        Do.click(root, R.id.size_ripple_height, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInputNumber(getContext(), height, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setCargoHeight(_args.getString(""));
                    }
                }, null);
            }
        });
        root.findViewById(R.id.size_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (length.length() == 0 || width.length() == 0 || height.length() == 0)
                    ICHelper.showDialog(getContext(), "Не все размеры заполнены", "Ошибка", null);
                else getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        root.findViewById(R.id.size_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (length.length() == 0 || width.length() == 0 || height.length() == 0)
                    ICHelper.showDialog(getContext(), "Не все размеры заполнены", "Ошибка", null);
                else getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
