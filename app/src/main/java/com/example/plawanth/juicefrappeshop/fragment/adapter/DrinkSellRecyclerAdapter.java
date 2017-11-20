package com.example.plawanth.juicefrappeshop.fragment.adapter;

import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.model.Drink;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by PlawanTH on 27/6/2560.
 */

public class DrinkSellRecyclerAdapter extends RecyclerView.Adapter<DrinkSellRecyclerAdapter.ViewHolder> {

    // Context
    private Context mContext;
    private ItemClickListener mItemClickListener;

    // Data Item
    private ArrayList<Drink> drinkList;

    private int lastPosition = -1;

    public DrinkSellRecyclerAdapter(Context context, ArrayList<Drink> drinkList) {
        mContext = context;
        this.drinkList = drinkList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // declare view holder
        @BindView(R.id.sell_drink_item_text) TextView mDrinkTextView;
        @BindView(R.id.drink_item_card_view) CardView mDrinkCard;

        private ViewHolder(View v) {
            super(v);
            // bind view in this scope
            ButterKnife.bind(this, v);
        }

        private void clearAnimation(View targetView) {
            targetView.clearAnimation();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // can set view type
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_drink_sell_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // bind for view type: holder.getItemViewType()
        holder.itemView.setAlpha(0f);

        // set property of view
        holder.mDrinkTextView.setText(drinkList.get(position).getName());

        holder.mDrinkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.setItemClickListener(drinkList.get(position));
                }
            }
        });

        holder.mDrinkCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.setItemLongClickListener(drinkList.get(position));
                }
                return false;
            }
        });

        setAnimation(holder.itemView, position);
    }



    @Override
    public int getItemCount() {
        return drinkList == null ? 0 : drinkList.size();
        // item count for recycler
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        // get type for multiple view type
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        ((ViewHolder) holder).clearAnimation(((ViewHolder) holder).itemView);
    }

    // Custom Method

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    // Animation
    private void setAnimation(View targetView, int position) {
        if (position > lastPosition) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(targetView, View.TRANSLATION_Y, 200f, 0f);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(targetView, View.ALPHA, 0f, 1f);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animation, alphaAnim);
            animSet.setDuration(1000);
            animSet.setInterpolator(new DecelerateInterpolator());
            animSet.start();
            lastPosition = position;
        }
    }

    // Interface class for click listener

    public interface ItemClickListener {
        void setItemClickListener(Drink drink);
        void setItemLongClickListener(Drink drink);
    }

}
