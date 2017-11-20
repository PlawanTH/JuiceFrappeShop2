package com.example.plawanth.juicefrappeshop.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;

import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.dao.DrinkDao;
import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.fragment.adapter.DrinkSellRecyclerAdapter;
import com.example.plawanth.juicefrappeshop.model.Drink;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkManagementListFragment extends Fragment {

    // View
    @BindView(R.id.add_button) Button mAddButton;
    @BindView(R.id.drink_list_recycler) RecyclerView mDrinkSellRecyclerView;

    private DrinkSellRecyclerAdapter mDrinkSellRecyclerAdapter;
    private RecyclerView.LayoutManager mDrinkSellRecyclerLayoutManager;

    // database
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private DrinkDao drinkDao;

    // Data
    private ArrayList<Drink> drinkList;

    public static DrinkManagementListFragment newInstance() {
        DrinkManagementListFragment fragment = new DrinkManagementListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Database
        mDbHelper = new DbHelper(getActivity());
        mDb = mDbHelper.getWritableDatabase();

        // Initialize Data Access
        drinkDao = new DrinkDao(getActivity(), mDb);
        drinkList = drinkDao.getAllDrinks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_management_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrinkSellRecyclerAdapter = new DrinkSellRecyclerAdapter(getActivity(), drinkList);
        mDrinkSellRecyclerLayoutManager = new LinearLayoutManager(getActivity());

        mDrinkSellRecyclerView.setAdapter(mDrinkSellRecyclerAdapter);
        mDrinkSellRecyclerView.setLayoutManager(mDrinkSellRecyclerLayoutManager);
        mDrinkSellRecyclerView.setHasFixedSize(true);
        mDrinkSellRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mDrinkSellRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mDrinkSellRecyclerAdapter.setOnItemClickListener(new DrinkSellRecyclerAdapter.ItemClickListener() {
            @Override
            public void setItemClickListener(Drink drink) {
                // change fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drink_management_frame, DrinkEditFragment.newEditInstance(drink.getId()))
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void setItemLongClickListener(final Drink drink) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("ต้องการลบ \"" + drink.getName() + "\" ใช่หรือไม่");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drinkDao.markDeletedDrink(drink);
                        updateDrinkList();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No action
                    }
                });
                builder.create();
                builder.show();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.drink_management_frame, DrinkEditFragment.newAddInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        mDb.close();
        mDbHelper.close();
        super.onDestroy();
    }

    public void updateDrinkList() {
        drinkList.clear();
        drinkList.addAll(drinkDao.getAllDrinks());
        mDrinkSellRecyclerAdapter.notifyDataSetChanged();
    }
}
