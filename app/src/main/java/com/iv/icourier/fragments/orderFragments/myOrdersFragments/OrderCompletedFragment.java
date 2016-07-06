package com.iv.icourier.fragments.orderFragments.myOrdersFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iv.icourier.R;
import com.iv.icourier.adapters.MyOrdersAdapter;
import com.iv.icourier.models.ICOrder;

import java.util.ArrayList;

/**
 * Created by iv on 08.03.16.
 */
public class OrderCompletedFragment extends Fragment {
    private RecyclerView        ordersView;
    private MyOrdersAdapter     ordersAdapter;
    private ArrayList<ICOrder>  ordersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_completed, container, false);

        initAdapter();
        initRecycler(root);

        return root;
    }
    private void initAdapter() {
//        ordersList = ModelHelper.getOrdersList();
//        ordersAdapter = new MyOrdersAdapter(ordersList, getActivity());
    }
    private void initRecycler(View root) {
//        ordersView = Find.recycler(root, R.id.order_completed_recycler);
//        ordersView.setAdapter(ordersAdapter);
    }
}
