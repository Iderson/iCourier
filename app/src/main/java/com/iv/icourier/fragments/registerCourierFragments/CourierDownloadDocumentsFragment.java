package com.iv.icourier.fragments.registerCourierFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iv.icourier.R;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;

/**
 * Created by iv on 18.03.16.
 */
public class CourierDownloadDocumentsFragment extends Fragment {
    private LinearLayout mainPage;
    private LinearLayout addressPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courier_download_documents, container, false);

        initViews(root);
        setListeners(root);

        return root;
    }
    private void initViews(View root) {
        mainPage    =   Find.l(root, R.id.courier_download_main_page);
        addressPage =   Find.l(root, R.id.courier_download_address_page);
    }
    private void setListeners(View root) {
        Do.click(mainPage, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                mainPage.setActivated(true);
                addressPage.setActivated(false);
            }
        });
        Do.click(addressPage, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                mainPage.setActivated(false);
                addressPage.setActivated(true);
            }
        });
        Do.click(root, R.id.courier_document_action, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
    }
}
