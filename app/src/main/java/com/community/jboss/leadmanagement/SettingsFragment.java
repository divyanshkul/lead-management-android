package com.community.jboss.leadmanagement;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.community.jboss.leadmanagement.main.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);

        if(getActivity()!=null) {
            final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            final String currentServer = sharedPref.getString(getString(R.string.saved_server_ip), "https://github.com/jboss-outreach");

            final EditTextPreference mPreference = (EditTextPreference) findPreference("server_location");
            final SwitchPreference mToggleMode = (SwitchPreference) findPreference("nightSwitch");
            final CheckBoxPreference mAutoMode = (CheckBoxPreference) findPreference("enableAutoMode");
            Intent intent = new Intent(getActivity(), MainActivity.class);

            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_AUTO){
                mAutoMode.setChecked(true);
                mToggleMode.setChecked(false);
                mToggleMode.setEnabled(false);
            }

            else{
                mAutoMode.setChecked(false);

            }
            mAutoMode.setOnPreferenceChangeListener((preference, newValue) -> {
                if ((boolean) newValue) {
                    mToggleMode.setEnabled(false);
                    mToggleMode.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    Toast.makeText(getContext(), getString(R.string.autoEnabled), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mToggleMode.setEnabled(true);
                }
                return true;
            });
            mToggleMode.setOnPreferenceChangeListener((preference, newValue) -> {
                toggleTheme();
                return true;
            });


            mPreference.setSummary(currentServer);
            mPreference.setText(currentServer);

            mPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String newData = newValue.toString().trim();
                mPreference.setSummary(newData);
                mPreference.setText(newData);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.saved_server_ip),newData);
                editor.apply();
                return false;
            });
        }
    }

    private void toggleTheme() {
        Toast.makeText(getContext(), getString(R.string.modeChanged), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
