package com.iv.icourier.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.models.ICCar;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private static int currentPos = -1;
    public ArrayList<ICCar> listData;
    public Context          mContext;

    public CarsAdapter(ArrayList<ICCar> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private final LinearLayout list_row;
        TextView    model;
        ImageView   mark;
        public ViewHolder(View itemView) {
            super(itemView);
            model   = Find.t(itemView, R.id.item_car_model);
            mark = Find.i(itemView, R.id.item_car_check);
//            list_row = (LinearLayout) itemView.findViewById(R.id.list_row);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_car, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ICCar car = listData.get(position);
        if (currentPos != -1 && position != currentPos)
            car.setIsCheck(false);
        if (listData.get(position).isCheck()) {
            holder.mark.setImageDrawable(mContext.getDrawable(R.drawable.blue_check));
        } else {
            holder.mark.setImageDrawable(mContext.getDrawable(R.drawable.transparent));
        }

        holder.model.setText(car.getName());
    }
    public void setSelected(int position) {
        try {
            if (listData.size() > 1) {
                currentPos = position;
            }
            listData.get(position).setIsCheck(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listData != null ? listData.size() : 0;
    }
}
