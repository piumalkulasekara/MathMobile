package com.example.piumal.mathmobile;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
//import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileOutputStream;

/**
 * Created by piumal on 3/12/17.
 */
public class Prefs extends PreferenceActivity{

    public static CheckBoxPreference hints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        hints = (CheckBoxPreference)findPreference("hints");
        hints.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (hints.isChecked()){
                    savePrefs("off");
                }else
                    savePrefs("on");
                return true;
            }
        });

    }

    public void savePrefs(String hints){
        String data = "hints=" + hints;
        try {
            Log.d("myResults", "Save hints=" + hints);
            FileOutputStream outputStream = openFileOutput("Game.prefs", Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
