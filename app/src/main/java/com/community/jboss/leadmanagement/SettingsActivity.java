package com.community.jboss.leadmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.community.jboss.leadmanagement.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends FragmentActivity {

    private static final String DarkUI_Pref = "prefs";
    private static final String DarkTheme_Pref = "dark_theme";

    @BindView(R.id.settings_bar)
    Toolbar toolbar;
    @BindView(R.id.nightSwitch)
    Switch themeToggle;
    @BindView(R.id.enableAutoMode)
    CheckBox enableAuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(DarkUI_Pref, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(DarkTheme_Pref, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState != null) {
            // During initial setup, plug in the details fragment.
            SettingsFragment details = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.settings_fragment_id, details).commit();
        }

        ButterKnife.bind(this);
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_AUTO) {
            enableAuto.setChecked(true);
        } else {
            enableAuto.setChecked(false);
        }

        Intent intent = new Intent(this, MainActivity.class);
        enableAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
                if (isEnabled) {
                    themeToggle.setEnabled(false);
                    themeToggle.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    Toast.makeText(getApplicationContext(), getString(R.string.autoEnabled), Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    themeToggle.setEnabled(true);

                }
            }
        });

        themeToggle.setChecked(useDarkTheme);
        themeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });


    }

    private void toggleTheme(boolean darkTheme) {
        Toast.makeText(getApplicationContext(), getString(R.string.modeChanged), Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences(DarkUI_Pref, MODE_PRIVATE).edit();
        editor.putBoolean(DarkTheme_Pref, darkTheme);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onPause();
    }

}
