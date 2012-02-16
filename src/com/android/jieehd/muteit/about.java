package com.android.jieehd.muteit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class about extends PreferenceActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);
        
        Preference website = (Preference) findPreference("webBrowse");
        website.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        String url = "http://www.villainrom.co.uk";
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        return true;
                                }

                        });
        
        Preference openSource = (Preference) findPreference("openSource");
        openSource.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        String url = "https://JieeHD@github.com/JieeHD/MuteIt";
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        return true;
                                }

                        });
    }
}
