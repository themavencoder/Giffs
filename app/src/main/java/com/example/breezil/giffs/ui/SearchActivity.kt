package com.example.breezil.giffs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.ActivitySearchBinding
import com.example.breezil.giffs.utils.BottomNavigationHelper

class SearchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setUpBottomNavigation()

    }


    private fun setUpBottomNavigation(){
        BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)

        val menu = binding.bottomNavViewBar.menu
        val menuItem = menu.getItem(1)
        menuItem.isChecked = true

        /*
         * here sets the navigations to its corresponding activities
         */
        binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.trending -> {
                    startActivity(Intent (this@SearchActivity, MainActivity::class.java))
                    finish()
                }
                R.id.search -> {}
                R.id.saved -> {
                    startActivity(Intent(this@SearchActivity, SavedActivity::class.java))
                    finish()
                }
                R.id.preference -> {
                    startActivity(Intent(this@SearchActivity, PreferenceActivity::class.java))
                    finish()
                }
            }


            false
        }
    }
}
