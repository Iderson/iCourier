package com.iv.icourier.helpers;

import android.view.View;

/**
 * Created by iv on 16.03.16.
 */
public class Do {
    public interface onCLick {
        void onClick(View view);
    }
    public static void click(View view, final onCLick cl) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.onClick(v);
            }
        });
    }
    public static void click(View root, int id, final onCLick cl) {
        root.findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.onClick(v);
            }
        });
    }
    public static void vis(View... views) {
        for (View v: views)
            v.setVisibility(v.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
