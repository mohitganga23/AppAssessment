package com.example.appassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Model> models;
    AppLabelAdapter appLabelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = findViewById(R.id.main_activity_relative_layout);
        MaterialToolbar materialToolbar = findViewById(R.id.toolbar);
        materialToolbar.inflateMenu(R.menu.menu);

        DatabaseHandler db = new DatabaseHandler(this);
        RecyclerView appLabelRecyclerView = findViewById(R.id.app_label_recycler_view);

        models = new ArrayList<>();
        models = db.getApplicationLabel();

        appLabelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appLabelRecyclerView.setHasFixedSize(true);

        appLabelAdapter = new AppLabelAdapter(models);
        appLabelRecyclerView.setAdapter(appLabelAdapter);

        if (models.size() < 1) {
            findViewById(R.id.nothing_to_show_text).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.nothing_to_show_text).setVisibility(View.GONE);
        }

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete_logs) {
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "All logs deleted", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    db.clearTable();
                    models = db.getApplicationLabel();
                    appLabelAdapter = new AppLabelAdapter(models);
                    appLabelRecyclerView.setAdapter(appLabelAdapter);
                    findViewById(R.id.nothing_to_show_text).setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        RelativeLayout relativeLayout = findViewById(R.id.main_activity_relative_layout);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;

            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Tap back again to exit", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            snackbar.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}