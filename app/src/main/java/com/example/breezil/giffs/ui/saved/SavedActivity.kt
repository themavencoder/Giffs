package com.example.breezil.giffs.ui.saved

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.breezil.giffs.R
import com.example.breezil.giffs.callbacks.SavedGifClickListener
import com.example.breezil.giffs.databinding.ActivitySavedBinding
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.ui.BaseActivity
import com.example.breezil.giffs.ui.bottom_sheet.SavedActionBottomSheetFragment
import com.example.breezil.giffs.ui.search.SearchActivity
import com.example.breezil.giffs.ui.adapter.SavedGifRecyclerAdapter
import com.example.breezil.giffs.ui.preference.AboutActivity
import com.example.breezil.giffs.ui.preference.PreferenceFragment
import com.example.breezil.giffs.ui.trending.MainActivity
import com.example.breezil.giffs.utils.BottomNavigationHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

class SavedActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : ActivitySavedBinding

    lateinit var savedViewModel: SavedViewModel

    internal var gifAdapter: SavedGifRecyclerAdapter? = null

    internal var savedActionBottomSheetFragment =
        SavedActionBottomSheetFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved)
        AndroidInjection.inject(this)

        binding.savedList.hasFixedSize()
        binding.deleteAll.setOnClickListener { v -> showDeleteAllDialog()  }

        setUpAdapter()
        setUpViewModel()
        setUpBottomNavigation()
        supportActionBar!!.title = getString(R.string.saved)
    }

    fun setUpAdapter(){
        val savedGifClickListener = object : SavedGifClickListener {
            override fun clickGif(savedGif: SavedGif) {
                savedActionBottomSheetFragment.getSavedGif(savedGif).show(supportFragmentManager, "choose")
            }
        }
        gifAdapter = SavedGifRecyclerAdapter(this, savedGifClickListener)
        binding.savedList.adapter = gifAdapter
    }


    @SuppressLint("RestrictedApi")
    private fun setUpViewModel(){
        savedViewModel = ViewModelProviders.of(this).get(SavedViewModel::class.java)
        savedViewModel.savedList.observe(this, Observer { savedGif ->

            if(!savedGif.isEmpty()){
                gifAdapter?.submitList(savedGif)
                binding.deleteAll.visibility = View.VISIBLE
            }else{
                binding.savedEmpty.visibility = View.VISIBLE
                binding.clickToSaveBtn.visibility = View.VISIBLE

                binding.clickToSaveBtn.setOnClickListener {
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }


        })
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

            }


            false
        }
    }

    //Creating the option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        //set the menu layout
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //Option menu selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //if logout item is selected


        if (item.itemId == R.id.preference) {
            preference()

        }
        if (item.itemId == R.id.about) {
            startActivity(Intent(this@SavedActivity, AboutActivity::class.java))
        }


        return true
    }
    fun preference(){
        val preferenceFragment = PreferenceFragment()
        preferenceFragment.show(supportFragmentManager, "show")
    }

    private fun showDeleteAllDialog() {

        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setCancelable(false)
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_all_saved)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                deleteAll()
                dialog.dismiss()

                Toast.makeText(this, R.string.saved_list_emptied, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.setTitle(getString(R.string.delete_all))
        alertDialog.show()

    }

    private fun deleteAll() {
        savedViewModel.deleteAll()
        restartActivity()

    }


    fun restartActivity() {
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
        finish()
    }
}
