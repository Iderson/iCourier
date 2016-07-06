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

import java.util.ArrayList;

/**
 * Created by Andread on 31.05.2016.
 */
public class PriceAdapter  extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {
    private static int currentPos = -1;
    public ArrayList<String> mPriceList;
    public Context mContext;

    public PriceAdapter(ArrayList<String> listData, Context mContext) {
        this.mPriceList = listData;
        this.mContext = mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mIvCheck;
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = Find.t(itemView, R.id.item_price_model);
            mIvCheck = Find.i(itemView, R.id.item_price_check);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_price, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String price = mPriceList.get(position);
        /*if (currentPos != -1 && position != currentPos) {
            holder.mIvDrag.setImageDrawable(mContext.getDrawable(R.drawable.transparent));
            notifyDataSetChanged();
        }
        else if (currentPos != -1 && position == currentPos) {
            holder.mIvDrag.setImageDrawable(mContext.getDrawable(R.drawable.blue_check));
            notifyDataSetChanged();
        }*/
        holder.mTextView.setText(price);
    }
    public void setSelected(int position) {
        try {
            if (mPriceList.size() > 1) {
                currentPos = position;
            }
//            mPhoneList.get(position).setIsCheck(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPriceList != null ? mPriceList.size() : 0;
    }
}
