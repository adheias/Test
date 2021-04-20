package com.dicoding.picodiploma.submission2bfaa.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.picodiploma.submission2bfaa.AlarmReceiver
import com.dicoding.picodiploma.submission2bfaa.R

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var NOTIFICATION: String
    private lateinit var LANGUAGE: String

    private lateinit var switchPreference: SwitchPreference
    private lateinit var changeLanguage: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        NOTIFICATION = resources.getString(R.string.notification)
        LANGUAGE = resources.getString(R.string.language)

        switchPreference = findPreference<SwitchPreference>(NOTIFICATION) as SwitchPreference
        changeLanguage = findPreference<Preference>(LANGUAGE) as Preference
        changeLanguage.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == NOTIFICATION) {
            switchPreference.isChecked = sharedPreferences.getBoolean(NOTIFICATION, false)
            val alarmReceiver = AlarmReceiver()
            if (switchPreference.isChecked) {
                activity?.let { alarmReceiver.setRepeatingAlarm(it) }
                switchPreference.isChecked = true
            } else {
                activity?.let { alarmReceiver.cancelAlarm(it) }
                switchPreference.isChecked = false
            }
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        switchPreference.isChecked = sh.getBoolean(NOTIFICATION, false)
    }

}