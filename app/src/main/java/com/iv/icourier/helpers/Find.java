package com.iv.icourier.helpers;

import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by iv on 16.03.16.
 */
public class Find {
    public static SwitchCompat s(View root, int id) {
        return (SwitchCompat) root.findViewById(id);
    }
    public static FrameLayout f(View root, int id) {
        return (FrameLayout) root.findViewById(id);
    }
    public static View v(View root, int id) {
        return root.findViewById(id);
    }
    public static TextView t(View root, int id) {
        return (TextView) root.findViewById(id);
    }

    public static ScrollView scroll(View root, int id) {
        return (ScrollView) root.findViewById(id);
    }
    public static MaterialRippleLayout m(View root, int id) {
        return (MaterialRippleLayout) root.findViewById(id);
    }
    public static LinearLayout l(View root, int id) {
        return (LinearLayout) root.findViewById(id);
    }
    public static RelativeLayout r(View root, int id) {
        return (RelativeLayout) root.findViewById(id);
    }
    public static ImageView i(View root, int id) {
        return (ImageView) root.findViewById(id);
    }
    public static void price(View root, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            ((TextView) root.findViewById(id)).append(" \u20bd");
        else
            ((TextView) root.findViewById(id)).append(" руб.");
    //            ((TextView) root.findViewById(id)).setText(ICCar
//                .format("%s %s", ((TextView) root.findViewById(id)).getText().toString(), Html.fromHtml("&#x584;")));
    }
    public static EditText e(View root, int id) {
        return (EditText) root.findViewById(id);
    }
    public static CircleImageView c(View root, int id) {
        return (CircleImageView) root.findViewById(id);
    }
    public static RecyclerView recycler(View root, int id) {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(id);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(false);
        return recyclerView;
    }
}
