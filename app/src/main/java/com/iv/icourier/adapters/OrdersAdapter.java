package com.iv.icourier.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.iv.icourier.R;
import com.iv.icourier.activities.OrderDetailActivity;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.models.ICOrder;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class OrdersAdapter extends RecyclerSwipeAdapter<OrdersAdapter.ViewHolder> {
    public  static ICOrder      currentOrder;
    private ArrayList<ICOrder>  listData;
    private Context             mContext;

    public OrdersAdapter(ArrayList<ICOrder> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout  swipeLayout;
        TextView     name;
        TextView     addressStart;
        TextView     addressEnd;
        TextView     date;
        TextView     distance;
        TextView     price;
        LinearLayout leftView;
        LinearLayout rightView;

        public ViewHolder(View itemView) {
            super(itemView);
            addressStart    =   Find.t(itemView, R.id.item_order_address_from);
            addressEnd      =   Find.t(itemView, R.id.item_order_address_to);
            date            =   Find.t(itemView, R.id.item_order_date);
            distance        =   Find.t(itemView, R.id.item_order_distance);
            price           =   Find.t(itemView, R.id.item_order_price);
            name            =   Find.t(itemView, R.id.item_order_name);
            leftView        =   Find.l(itemView, R.id.item_order_left_view);
            rightView       =   Find.l(itemView, R.id.item_order_right_view);
            swipeLayout     =   (SwipeLayout) itemView.findViewById(R.id.item_order_swipe);
                                Find.price(itemView, R.id.item_order_price);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ICOrder order = listData.get(position);
        String date = order.getDateFrom() + " — " + order.getDateTo();

        viewHolder.name.setText(order.getName());
        viewHolder.addressStart.setText(order.getAddressFrom());
        viewHolder.addressEnd.setText(order.getAddressTo());
        viewHolder.date.setText(date);
        viewHolder.distance.setText(order.getDistance());
        viewHolder.price.setText(order.getPrice());
        Do.click(viewHolder.leftView, new Do.onCLick() {
            @Override
            public void onClick(View view) {

            }
        });
        Do.click(viewHolder.rightView, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                currentOrder = order;
                ((Activity)mContext).startActivityForResult(new Intent(mContext, OrderDetailActivity.class), ICConst.REQUEST_DETAIL_ORDER);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.item_order_swipe;
    }
}