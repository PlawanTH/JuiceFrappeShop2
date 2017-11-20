package com.example.plawanth.juicefrappeshop.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.dao.SalesRecordDao;
import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.fragment.adapter.SalesRecyclerAdapter;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {

    // View
    @BindView(R.id.sales_list_recycler)
    RecyclerView mSalesRecyclerView;

    private SalesRecyclerAdapter mSalesRecyclerAdapter;
    private RecyclerView.LayoutManager mSalesRecyclerLayoutManager;

    // Database
    DbHelper mDbHelper;
    SQLiteDatabase mDb;
    SalesRecordDao salesRecordDao;

    // Data
    ArrayList<SalesRecord> salesList;

    public static SalesFragment newInstance() {
        SalesFragment fragment = new SalesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new DbHelper(getActivity());
        mDb = mDbHelper.getWritableDatabase();

        // Prepare Data
        salesRecordDao = new SalesRecordDao(getActivity(), mDb);
        salesList = salesRecordDao.getSalesRecords();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSalesRecyclerLayoutManager = new LinearLayoutManager(getActivity());
        mSalesRecyclerAdapter = new SalesRecyclerAdapter(getActivity(), mDb, salesList);

        mSalesRecyclerView.setAdapter(mSalesRecyclerAdapter);
        mSalesRecyclerView.setHasFixedSize(true);
        mSalesRecyclerView.setLayoutManager(mSalesRecyclerLayoutManager);
        mSalesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSalesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mDb.close();
        mDbHelper.close();
        super.onDestroy();
    }
}
