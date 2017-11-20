package com.example.plawanth.juicefrappeshop.fragment.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.example.plawanth.juicefrappeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkSellAmountDialogFragment extends DialogFragment {

    private SellDialogListener mSellDialogListener;

    @BindView(R.id.sell_number_picker)
    NumberPicker mNumberPicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.fragment_drink_sell_dialog, null);
        ButterKnife.bind(this, mView);

        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(999);
        mNumberPicker.setWrapSelectorWheel(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSellDialogListener.onDialogPositiveClick(mNumberPicker.getValue());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSellDialogListener.onDialogNegativeClick();
            }
        });

        return builder.create();
    }

    public void setOnDialogConfirmClick(SellDialogListener listener) {
        mSellDialogListener = listener;
    }

    public interface SellDialogListener {
        void onDialogPositiveClick(int amount);
        void onDialogNegativeClick();
    }
}
