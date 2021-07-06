package com.example.appassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;

public class SetupActivity extends AppCompatActivity {

    private Timer timer;
    private final int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        RelativeLayout relativeLayout = findViewById(R.id.setup_activity_relative_layout);
        ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        MaterialCardView materialCardView = findViewById(R.id.allow_access_card);
        materialCardView.setVisibility(View.GONE);

        new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (isAccessServiceEnabled(getApplicationContext(), MyAccessibilityService.class)) {
                    startActivity(new Intent(SetupActivity.this, MainActivity.class));
                    finish();
                } else {
                    TransitionManager.beginDelayedTransition(transitionsContainer);
                    materialCardView.setVisibility(View.VISIBLE);
                }
            }
        }.start();

        Button goToSettingsBtn = findViewById(R.id.go_to_settings_btn);
        goToSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });

        Button continueBtn = findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAccessServiceEnabled(getApplicationContext(), MyAccessibilityService.class)) {
                    startActivity(new Intent(SetupActivity.this, MainActivity.class));
                    finish();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Please enable accessibility service from settings", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    public boolean isAccessServiceEnabled(Context context, Class accessibilityServiceClass) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return prefString != null && prefString.contains(context.getPackageName() + "/" + accessibilityServiceClass.getName());
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        RelativeLayout relativeLayout = findViewById(R.id.setup_activity_relative_layout);
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