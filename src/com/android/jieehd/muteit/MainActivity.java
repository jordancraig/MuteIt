package com.android.jieehd.muteit;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        Preference checkMedia = (Preference) findPreference("checkMedia");
        checkMedia.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = customSharedPreference.edit();
                                        editor.putString("checkMedia","Select this to mute media volume");
                                        editor.commit();
                                        return true;
                                }

                        });
        
        
        Preference checkRing = (Preference) findPreference("checkRing");
        checkRing.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = customSharedPreference.edit();
                                        editor.putString("checkRing","Select this to mute ringer volume");
                                        editor.commit();
                                        return true;
                                }

                        });
        
        
        Preference checkAlarm = (Preference) findPreference("checkAlarm");
        checkAlarm.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = customSharedPreference.edit();
                                        editor.putString("checkAlarm","Select this to mute alarm volume");
                                        editor.commit();
                                        return true;
                                }

                        });
        
        Preference checkVoice = (Preference) findPreference("checkVoice");
        checkVoice.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                        SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = customSharedPreference.edit();
                                        editor.putString("checkVoice","Select this to mute voice volume");
                                        editor.commit();
                                        return true;
                                }

                        });
        
    	
        Preference mute = (Preference) findPreference("muteButton");
        mute.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = customSharedPreference.edit();
                                    editor.putString("muteButton","Mutes selected audio streams");
                                    editor.commit();
                                    triggerNotification("Currently running in mute mode");
                                    muteStreams();
                                    return true;
                                }
        				});
        
        Preference unmute = (Preference) findPreference("unmuteButton");
        unmute.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = customSharedPreference.edit();
                                    editor.putString("unmuteButton","Unmutes previously selected audio streams");
                                    editor.commit();
                                    triggerNotification("Currently running in unmute mode");
                                    unmute();
                                    return true;
                                }
        				});
        
        
        Preference check_notify = (Preference) findPreference("checkNotify");
        check_notify.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = customSharedPreference.edit();
                                    editor.putString("checkNotify","Select this to mute notification volume");
                                    editor.commit();
                                    return true;
                                }
        				});
        
        Preference check_system = (Preference) findPreference("checkSystem");
        check_system.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = customSharedPreference.edit();
                                    editor.putString("checkSystem","Select this to mute system volume");
                                    editor.commit();
                                    return true;
                                }
        				});
        
        
        Preference list_profiles = (Preference) findPreference("choose_pro");
        list_profiles.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    SharedPreferences customSharedPreference = getSharedPreferences("preferences_Shared", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = customSharedPreference.edit();
                                    editor.putString("choose_pro","Select this to select MuteIt profile");
                                    editor.commit();
                                    setProfileCurrent();
                                    onSharedPreferenceChanged(customSharedPreference, "choose_pro");
                                    return true;
                                }
        				});
    
        
        
        
    }
    
    private void setProfileCurrent()  {
        final Preference key_current = (Preference) findPreference("key_current");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        key_current.setSummary(sp.getString("choose_pro", ""));
    }
    
    private void setData()  {
        final Preference key_current = (Preference) findPreference("key_current");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        key_current.setSummary(sp.getString("choose_pro", ""));
    }
    
    public void triggerNotification(String msg) {
    	
    	final NotificationManager notificationManager;
    	final int APP_ID = 4567;
    	final Notification notification =  new Notification(R.drawable.ic_stat_mute_notification, msg, System.currentTimeMillis());
    	
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent contentIntent = new Intent(this, MainActivity.class);
        notification.setLatestEventInfo(this, "MuteIt", msg, PendingIntent.getActivity(this.getBaseContext(), 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(null, APP_ID, notification);
}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case R.string.about:	Intent settings = new Intent();
									settings.setClass(getApplicationContext(), about.class);
									Intent settingsActivity = new Intent(getBaseContext(), about.class);
									startActivity(settings);
						break;
        }
        return true;
    }
    
    private void muteStreams() {
    	
        final boolean check_media;
        final boolean check_ring;
        final boolean check_alarm;
        final boolean check_voice;
        final boolean check_system;
        final boolean check_notify;
        
        final AudioManager mgr = (AudioManager) getSystemService(AUDIO_SERVICE);

        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        check_media = prefs.getBoolean("checkMedia", true);
        check_ring = prefs.getBoolean("checkRing", true);
        check_alarm = prefs.getBoolean("checkAlarm", true);
    	check_voice = prefs.getBoolean("checkVoice", true);
    	check_notify = prefs.getBoolean("checkNotify", true);
    	check_system = prefs.getBoolean("checkSystem", true);
    		
    	
                                	if (check_media == true) {
                                        mgr.setStreamMute(AudioManager.STREAM_MUSIC, true);
                                	}else{
                                		
                                	}
                                	
                                	if (check_ring == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_RING, true);
                                	}else{
                                		
                                	}
                                	
                                	if (check_alarm == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_ALARM, true);
                                	}else{
                                		
                                	}
                                	
                                	if (check_voice == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                                	}else{
                                		
                                	}
                                	
                                	if (check_system == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                                	}else{
                                		
                                	}
                                	
                                	if (check_notify == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                                	}else{
                                		
                                	}
                                	Toast.makeText(getBaseContext(), "Selected streams have been muted", Toast.LENGTH_SHORT);
                                }
    
    private void unmute() {
    	
        final boolean check_media;
        final boolean check_ring;
        final boolean check_alarm;
        final boolean check_voice;
        final boolean check_system;
        final boolean check_notify;
        
        final AudioManager mgr = (AudioManager) getSystemService(AUDIO_SERVICE);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        check_media = prefs.getBoolean("checkMedia", true);
        check_ring = prefs.getBoolean("checkRing", true);
        check_alarm = prefs.getBoolean("checkAlarm", true);
    	check_voice = prefs.getBoolean("checkVoice", true);
    	check_notify = prefs.getBoolean("checkNotify", true);
    	check_system = prefs.getBoolean("checkSystem", true);
    	
                                	if (check_media == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_MUSIC, false);
                                	}else{
                                		
                                	}
                                	
                                	if (check_ring == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_RING, false);
                                	}else{
                                		
                                	}
                                	
                                	if (check_alarm == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_ALARM, false);
                                	}else{
                                		
                                	}
                                	
                                	if (check_voice == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                                	}else{
                                		
                                	}
                                	
                                	if (check_system == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                                	}else{
                                		
                                	}
                                	
                                	if (check_notify == true) {
                                		mgr.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                                	}else{
                                		
                                	}
                                	Toast.makeText(getApplicationContext(), "Selected streams have been unmuted", Toast.LENGTH_SHORT);
    	
    }

    public void onSharedPreferenceChanged(SharedPreferences sp, String choose_pro) {
        Preference pref = findPreference("key_current");

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }
    }

    	
    }
        
    
