package com.example.breezil.giffs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.breezil.giffs.R
import com.example.breezil.giffs.callbacks.SavedGifClickListener
import com.example.breezil.giffs.databinding.ActivitySavedBinding
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.ui.adapter.SavedGifRecyclerAdapter
import com.example.breezil.giffs.utils.BottomNavigationHelper
import com.example.breezil.giffs.view_model.SavedViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class SavedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : ActivitySavedBinding

    lateinit var savedViewModel: SavedViewModel

    internal var gifAdapter: SavedGifRecyclerAdapter? = null

    internal var savedActionBottomSheetFragment = SavedActionBottomSheetFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved)
        AndroidInjection.inject(this)

        binding.savedList.hasFixedSize()

        setUpAdapter()
        setUpViewModel()
        setUpBottomNavigation()
    }

    fun setUpAdapter(){
        val savedGifClickListener = object : SavedGifClickListener {
            override fun clickGif(savedGif: SavedGif) {
                savedActionBottomSheetFragment.getSavedGif(savedGif).show(supportFragmentManager, "choose")
//                actionBottomSheetFragment.show(supportFragmentManager, "choose")
            }
        }
        gifAdapter = SavedGifRecyclerAdapter(this, savedGifClickListener)
        binding.savedList.adapter = gifAdapter
    }


    private fun setUpViewModel(){
        savedViewModel = ViewModelProviders.of(this).get(SavedViewModel::class.java)
        savedViewModel.savedList.observe(this, Observer { savedGif ->
            gifAdapter?.submitList(savedGif)
            Toast.makeText(this, savedGif.size.toString() ,Toast.LENGTH_SHORT).show()
        })
    }


    fun restartActivity() {
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
        finish()
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

                R.id.trending -> {
                    startActivity(Intent (this@SavedActivity, MainActivity::class.java))
                    finish()
                }
                R.id.search -> {
                    startActivity(Intent(this@SavedActivity, SearchActivity::class.java))
                    finish()
                }
                R.id.saved -> {}
                R.id.preference -> {
                    startActivity(Intent(this@SavedActivity, PreferenceActivity::class.java))
                    finish()
                }
            }


            false
        }
    }
}
