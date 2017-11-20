package com.example.plawanth.juicefrappeshop.fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plawanth.juicefrappeshop.dao.DrinkDao;
import com.example.plawanth.juicefrappeshop.dao.SalesRecordDao;
import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.fragment.adapter.DrinkSellRecyclerAdapter;
import com.example.plawanth.juicefrappeshop.fragment.dialog.DrinkSellAmountDialogFragment;
import com.example.plawanth.juicefrappeshop.model.Drink;
import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkSellFragment extends Fragment {

    // View
    @BindView(R.id.sell_drink_list_recycler)
    RecyclerView mDrinkSellRecyclerView;

    private DrinkSellRecyclerAdapter mDrinkSellRecyclerAdapter;
    private RecyclerView.LayoutManager mDrinkSellRecyclerLayoutManager;

    // Database
    private SalesRecordDao salesRecordDao;
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Data
    private ArrayList<Drink> drinkList;

    public static DrinkSellFragment newInstance() {
        DrinkSellFragment fragment = new DrinkSellFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Database Connection
        mDbHelper = new DbHelper(getActivity());
        mDb = mDbHelper.getWritableDatabase();

        // Initialize Data Access
        DrinkDao drinkDao = new DrinkDao(getActivity(), mDb);
        drinkList = drinkDao.getAllDrinks();
        salesRecordDao = new SalesRecordDao(getActivity(), mDb);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_drink_sell, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrinkSellRecyclerAdapter = new DrinkSellRecyclerAdapter(getActivity(), drinkList);
        mDrinkSellRecyclerLayoutManager = new LinearLayoutManager(getActivity());
        mDrinkSellRecyclerView.setAdapter(mDrinkSellRecyclerAdapter);
        mDrinkSellRecyclerView.setHasFixedSize(true);
        mDrinkSellRecyclerView.setLayoutManager(mDrinkSellRecyclerLayoutManager);
        mDrinkSellRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mDrinkSellRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mDrinkSellRecyclerAdapter.setOnItemClickListener(new DrinkSellRecyclerAdapter.ItemClickListener() {
            @Override
            public void setItemClickListener(Drink drink) {
                Toast.makeText(getActivity(), drink.getName(), Toast.LENGTH_SHORT).show();
                showSellAmountDialog(drink);
            }

            @Override
            public void setItemLongClickListener(Drink drink) {
                // no action
            }
        });
    }

    @Override
    public void onDestroy() {
        mDb.close();
        mDbHelper.close();
        super.onDestroy();
    }

    // Custom Method

    public void showSellAmountDialog(final Drink drink) {
        DrinkSellAmountDialogFragment mSellAbmountDialog = new DrinkSellAmountDialogFragment();
        mSellAbmountDialog.show(getActivity().getSupportFragmentManager(), "DrinkSellAmountDialogFragment");
        mSellAbmountDialog.setOnDialogConfirmClick(new DrinkSellAmountDialogFragment.SellDialogListener() {
            @Override
            public void onDialogPositiveClick(int amount) {
                Toast.makeText(getActivity(), "Yes: "+amount, Toast.LENGTH_SHORT).show();

                String date = "";
                String time = "";

                if (Build.VERSION.SDK_INT >= 24) {
                    android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat(SalesRecord.DATE_FORMAT + " " + SalesRecord.TIME_FORMAT);
                    String dateTime = sdf.format(new Date());
                    date = dateTime.substring(0, dateTime.indexOf(" "));
                    time = dateTime.substring(dateTime.indexOf(" ")+1);
                } else {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(SalesRecord.DATE_FORMAT + " " + SalesRecord.TIME_FORMAT);
                    String dateTime = sdf.format(new Date());
                    date = dateTime.substring(0, dateTime.indexOf(" "));
                    time = dateTime.substring(dateTime.indexOf(" ")+1);
                }

                SalesRecord salesRecord = new SalesRecord();
                salesRecord.setDrinkId(drink.getId());
                salesRecord.setAmount(amount);
                salesRecord.setDate(date);
                salesRecord.setTime(time);

                salesRecordDao.insertNewSalesRecord(salesRecord);

            }

            @Override
            public void onDialogNegativeClick() {
                Toast.makeText(getActivity(), "No: ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
