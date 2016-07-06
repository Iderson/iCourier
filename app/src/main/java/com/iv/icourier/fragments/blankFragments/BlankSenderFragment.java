package com.iv.icourier.fragments.blankFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

import java.util.ArrayList;

/**
 * Created by iv on 09.03.16.
 */
public class BlankSenderFragment extends Fragment {
    public static EditText    where;
    private static EditText    date;
    private static EditText    time;
    private static EditText    whom;
    private static EditText    phone;
    private        EditText    comment;

    public static boolean isFilled() {
        return where.getText().length() > 0
                && date.getText().length() > 0
                && whom.getText().length() > 0
                && phone.getText().length() > 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_blank_sender, container, false);

        initViews(root);
        setListeners(root);

        return root;
    }
    private void initViews(View root) {
        where   =   Find.e(root, R.id.blank_sender_where);
        date    =   Find.e(root, R.id.blank_sender_date);
        time    =   Find.e(root, R.id.blank_sender_time);
        whom    =   Find.e(root, R.id.blank_sender_whom);
        phone   =   Find.e(root, R.id.blank_sender_phone);
        comment =   Find.e(root, R.id.blank_sender_comment);
    }
    private void setListeners(View root) {
        Do.click(root, R.id.blank_sender_ripple_where, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithPlace(getContext(), where, PagerBlankFragment.map, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder .setAddressFrom(_args.getString(""));
                    }
                }, null);
            }
        });
        Do.click(root, R.id.blank_sender_ripple_comment, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), comment, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setOrderDescription(_args.getString(""));
                    }
                }, "Номер офиса, как подъехать и т.д");

            }
        });
        Do.click(root, R.id.blank_sender_ripple_date, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getActivity(), date, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setDateFrom(_args.getString(""));
                    }
                });
            }
        });
        Do.click(root, R.id.blank_sender_ripple_time, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithClock((AppCompatActivity) getActivity(), new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle args) {
                        ArrayList<String> strings = args.getStringArrayList("");
                        if (strings != null) {
                            time.setText(String.format("%s:00 - %s:00", strings.get(0), strings.get(1)));
                            ICApplication.currentOrder .setTimeFromStart(strings.get(0));
                            ICApplication.currentOrder .setTimeFromTill(strings.get(1));
                        }
                    }
                });
            }
        });
        Do.click(root, R.id.blank_sender_ripple_whom, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), whom, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setSenderName(_args.getString(""));
                    }
                }, null);
            }
        });
        Do.click(root, R.id.blank_sender_ripple_phone, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInputPhone(getContext(), phone, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setSenderPhone(_args.getString(""));
                    }
                }, null);
            }
        });
    }
}
