package com.example.plawanth.juicefrappeshop.fragment;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plawanth.juicefrappeshop.R;
import com.example.plawanth.juicefrappeshop.dao.DrinkDao;
import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.model.Drink;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkEditFragment extends Fragment {

    public static final String KEY_DRINK_ID = "drinkId";

    // View
    @BindView(R.id.ok_button) Button mOkButton;
    @BindView(R.id.cancel_button) Button mCancelButton;
    @BindView(R.id.edit_drink_text) TextView mDrinkNameTextView;
    @BindView(R.id.edit_drink_name_edit_text) EditText mDrinkNameEditText;
    @BindView(R.id.edit_drink_type_edit_text) EditText mDrinkTypeEditText;

    // Database Handler
    DbHelper mDbHelper;
    SQLiteDatabase mDb;
    DrinkDao drinkDao;

    // Data
    Drink drink;

    public static DrinkEditFragment newAddInstance() {
        DrinkEditFragment fragment = new DrinkEditFragment();
        return fragment;
    }

    public static DrinkEditFragment newEditInstance(int drinkId) {
        DrinkEditFragment fragment = new DrinkEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DRINK_ID, drinkId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new DbHelper(getActivity());
        mDb = mDbHelper.getWritableDatabase();

        drinkDao = new DrinkDao(getActivity(), mDb);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int drinkId = bundle.getInt(KEY_DRINK_ID);
            drink = drinkDao.getDrink(drinkId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_management_edit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (drink != null) {
            mDrinkNameTextView.setText(drink.getName());
            mDrinkNameEditText.setText(drink.getName());
            mDrinkTypeEditText.setText(drink.getType());
        }

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _drinkName = mDrinkNameEditText.getText().toString();
                String _drinkType = mDrinkTypeEditText.getText().toString();

                if ("".equals(_drinkName) || "".equals(_drinkType)) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                } else {
                    if (drink != null) {
                        // update to database
                        drinkDao.updateDrink(new Drink(drink.getId(), _drinkName, _drinkType));
                    } else {
                        // add to database
                        drinkDao.insertDrink(new Drink(_drinkName, _drinkType));
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    ((DrinkManagementListFragment) fragmentManager.findFragmentByTag(DrinkManagementListFragment.class.getSimpleName())).updateDrinkList();
                    getActivity().getSupportFragmentManager().popBackStack();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroy() {
        mDb.close();
        mDbHelper.close();
        super.onDestroy();
    }
}
