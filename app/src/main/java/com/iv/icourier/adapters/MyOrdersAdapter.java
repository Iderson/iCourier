package com.iv.icourier.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.activities.CourierOrderDetailActivity;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.models.ICOrder;

import java.util.ArrayList;

/**
 * Created by iv on 20.03.16.
 */
public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private ArrayList<ICOrder>  listData;
    private Context             mContext;

    public MyOrdersAdapter(ArrayList<ICOrder> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View        ripple;
        TextView    name;
        TextView    date;
        TextView    addressStart;
        TextView    addressEnd;
        TextView    metroStart;
        TextView    metroEnd;
        TextView    status;

        public ViewHolder(View itemView) {
            super(itemView);

            addressStart = Find.t(itemView, R.id.item_my_order_address_from);
            addressEnd   = Find.t(itemView, R.id.item_my_order_address_to);
            name         = Find.t(itemView, R.id.item_my_order_name);
            date         = Find.t(itemView, R.id.item_my_order_date);
            metroStart   = Find.t(itemView, R.id.item_my_order_metro_start);
            metroEnd     = Find.t(itemView, R.id.item_my_order_metro_end);
            status       = Find.t(itemView, R.id.item_my_order_status);
            ripple       = Find.v(itemView, R.id.item_my_order_ripple);
        }
    }

    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_my_order, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyOrdersAdapter.ViewHolder holder, int position) {
        ICOrder order = listData.get(position);
        String date = order.getDateFrom() + " - " + order.getDateTo();

        holder.addressEnd.setText(order.getAddressTo());
        holder.addressStart.setText(order.getAddressFrom());
        holder.name.setText(order.getName());
        holder.date.setText(date);
        holder.metroStart.setText(order.getMetroStart());
        holder.metroEnd.setText(order.getMetroEnd());
        holder.status.setText(order.getStatus());

        holder.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CourierOrderDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
