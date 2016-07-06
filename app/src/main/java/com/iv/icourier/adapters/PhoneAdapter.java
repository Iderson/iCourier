package com.iv.icourier.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.iv.icourier.R;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andread on 01.06.2016.
 */
public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder>
        implements DraggableItemAdapter<PhoneAdapter.ViewHolder> {
    private static int currentPos = -1;
    public List<RecyclerItem> mPhoneList;
    public boolean mIsClickable = false;
    public Context mContext;

    public PhoneAdapter(ArrayList<String> _listData, Context _context, boolean _isClickable) {
        setHasStableIds(true);
        this.mIsClickable = _isClickable;
        mPhoneList = new ArrayList<>();
        mContext = _context;
        for (int i = 0; i < _listData.size(); i++)
            mPhoneList.add(new RecyclerItem(i, _listData.get(i)));
    }

    @Override
    public boolean onCheckCanStartDrag(ViewHolder holder, int position, int x, int y) {
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(ViewHolder holder, int position) {
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        if(fromPosition != 0 && fromPosition != getItemCount()-1
                && toPosition != 0 && toPosition != getItemCount()-1) {
            RecyclerItem movedItem = mPhoneList.remove(fromPosition);
            mPhoneList.add(toPosition, movedItem);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }

    class ViewHolder  extends AbstractDraggableItemViewHolder {
        final ImageView mIvDrag;
        EditText mEditText;
        public ViewHolder(final View itemView) {
            super(itemView);
            mEditText = Find.e(itemView, R.id.item_phone);
            mIvDrag = Find.i(itemView, R.id.item_drag);
            Do.click(itemView, R.id.item_ripple_phone, new Do.onCLick() {
                @Override
                public void onClick(View view) {
                    if(mIsClickable)
                        ICHelper.showDialogWithInputPhone(mContext, mEditText, new ICHelper.ExecutorValue() {
                        @Override
                        public void sucExec(Bundle _args) {
                            mPhoneList.get(getLayoutPosition()).text = _args.getString("");
                            notifyItemChanged(getAdapterPosition());
                            if(getLayoutPosition() == getItemCount()-1)
                                add(getItemCount());
                        }
                    }, null);
                }
            });
        }

        private void add(int _pos) {
            mPhoneList.add(new RecyclerItem(_pos,""));
            notifyItemInserted(_pos);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String phoneNumber = mPhoneList.get(position).text;
        if(position == getItemCount()-1)
            holder.mIvDrag.setImageDrawable(mContext.getDrawable(R.drawable.transparent));
        else holder.mIvDrag.setImageDrawable(mContext.getDrawable(R.drawable.grabber));
        holder.mEditText.setText(phoneNumber);
    }
    public void setSelected(int position) {
        try {
            if (mPhoneList.size() > 1) {
                currentPos = position;
            }
//            mPhoneList.get(position).setIsCheck(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return mPhoneList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mPhoneList != null ? mPhoneList.size() : 0;
    }
}
