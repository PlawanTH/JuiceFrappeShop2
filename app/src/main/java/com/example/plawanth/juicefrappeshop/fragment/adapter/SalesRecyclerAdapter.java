package com.example.plawanth.juicefrappeshop.fragment.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.dao.DrinkDao;
import com.example.plawanth.juicefrappeshop.model.Drink;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PlawanTH on 29/6/2560.
 */

public class SalesRecyclerAdapter extends RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder> {

    private Context mContext;

    private SQLiteDatabase mDb;
    private ArrayList<SalesRecord> salesList;
    private DrinkDao drinkDao;

    public SalesRecyclerAdapter(Context context, SQLiteDatabase db, ArrayList<SalesRecord> salesList) {
        mContext = context;
        mDb = db;
        this.salesList = salesList;
        drinkDao = new DrinkDao(mContext, mDb);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sales_label) TextView mSalesLabelTextView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_sales_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SalesRecord salesRecord = salesList.get(position);
        Drink drink = drinkDao.getDrink(salesRecord.getDrinkId());

        if (drink != null) {
            String drinkName = drink.getName();
            String drinkType = drink.getType();
            holder.mSalesLabelTextView.setText( drinkName + "\nประเภท: " + drinkType + "\nจำนวน: "  + salesRecord.getAmount());
        } else {
            holder.mSalesLabelTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
