package com.example.breezil.giffs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.ActivitySavedBinding
import com.example.breezil.giffs.utils.BottomNavigationHelper

class SavedActivity : AppCompatActivity() {

    lateinit var binding : ActivitySavedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation(){
        BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)

        val menu = binding.bottomNavViewBar.menu
        val menuItem = menu.getItem(2)
        menuItem.isChecked = true

        /*
         * here sets the navigations to its corresponding activities
         */
        binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.trending ->startActivity(Intent (this@SavedActivity, MainActivity::class.java))
                R.id.search -> startActivity(Intent(this@SavedActivity, SearchActivity::class.java))
                R.id.saved -> {}
                R.id.preference -> startActivity(Intent(this@SavedActivity, PreferenceActivity::class.java))
            }


            false
        }
    }
}
