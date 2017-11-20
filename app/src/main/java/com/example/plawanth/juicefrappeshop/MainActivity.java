package com.example.plawanth.juicefrappeshop;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.fragment.MainMenuFragment;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setLocale("th");

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_frame, MainMenuFragment.newInstance())
                    .commit();
        }

    }

    // Custom Method

    // Set locale for the app
    /*
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 ) {
            config.locale = locale;
            resources.updateConfiguration(config, displayMetrics);
        } else {
            config.setLocale(locale);
            getApplicationContext().createConfigurationContext(config);
        }
    }
    */
}
