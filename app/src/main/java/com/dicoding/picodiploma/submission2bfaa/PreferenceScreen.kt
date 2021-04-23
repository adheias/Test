package com.dicoding.picodiploma.submission2bfaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.submission2bfaa.fragment.MyPreferenceFragment

class PreferenceScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_screen)

        val actionbar = supportActionBar
        actionbar!!.title = "Setting"

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment())
            .commit()
    }
}