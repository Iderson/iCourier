package com.iv.icourier.adapters;

/**
 * Created by Andread on 02.06.2016.
 */
public class RecyclerItem {
    int id;
    String text;

    public RecyclerItem(int _id, String _text) {
        id = _id;
        text = _text;
    }

    public String getText() {
        return text;
    }
}
