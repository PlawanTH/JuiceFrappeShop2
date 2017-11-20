package com.example.plawanth.juicefrappeshop.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plawanth.juicefrappeshop.R;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkManagementFragment extends Fragment {

    public static DrinkManagementFragment newInstance() {
        DrinkManagementFragment fragment = new DrinkManagementFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_management, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drink_management_frame, DrinkManagementListFragment.newInstance(), DrinkManagementListFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
