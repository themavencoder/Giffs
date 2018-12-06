package com.example.breezil.giffs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.ActivityPreferenceAcivityBinding
import com.example.breezil.giffs.utils.BottomNavigationHelper

class PreferenceActivity : AppCompatActivity() {

    lateinit var binding : ActivityPreferenceAcivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_preference_acivity)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation(){
        BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)

        val menu = binding.bottomNavViewBar.menu
        val menuItem = menu.getItem(3)
        menuItem.isChecked = true

        /*
         * here sets the navigations to its corresponding activities
         */
        binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.trending ->startActivity(Intent (this@PreferenceActivity, MainActivity::class.java))
                R.id.search -> startActivity(Intent(this@PreferenceActivity, SearchActivity::class.java))
                R.id.saved -> startActivity(Intent(this@PreferenceActivity, SavedActivity::class.java))
                R.id.preference -> {}
            }


            false
        }
    }
}
