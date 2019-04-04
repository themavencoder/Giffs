package com.example.breezil.giffs.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.breezil.giffs.R

open class BaseActivity : AppCompatActivity() {

    internal var themeMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs : SharedPreferences =this@BaseActivity.getSharedPreferences(getString(R.string.PACKAGE_NAME),
            Context.MODE_PRIVATE
        )

        themeMode = sharedPrefs.getBoolean(getString(R.string.theme_state), false)


        if (themeMode) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}