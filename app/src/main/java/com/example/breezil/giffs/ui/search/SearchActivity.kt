package com.example.breezil.giffs.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.breezil.giffs.BuildConfig.API_KEY
import com.example.breezil.giffs.R
import com.example.breezil.giffs.callbacks.GifClickListener
import com.example.breezil.giffs.databinding.ActivitySearchBinding
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.ui.bottom_sheet.ActionBottomSheetFragment
import com.example.breezil.giffs.ui.preference.PreferenceActivity
import com.example.breezil.giffs.ui.adapter.GifRecyclerViewAdapter
import com.example.breezil.giffs.ui.saved.SavedActivity
import com.example.breezil.giffs.ui.trending.MainActivity
import com.example.breezil.giffs.utils.BottomNavigationHelper
import dagger.android.AndroidInjection
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchBinding

    internal var gifAdapter: GifRecyclerViewAdapter? = null

    lateinit var viewModel : SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    internal var actionBottomSheetFragment = ActionBottomSheetFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        AndroidInjection.inject(this)

        setUpBottomNavigation()

        binding.searchList.setHasFixedSize(true)


        setUpAdapter()
        setUpViewModel()

        val logging = HttpLoggingInterceptor { message -> Timber.tag(getString(R.string.okhttp)).d(message) }
        logging.redactHeader(getString(R.string.authorization))
        logging.redactHeader(getString(R.string.cookie))


    }

    private fun setUpAdapter(){

        val gifClickListener = object : GifClickListener {
            override fun clickGif(gif: Gif) {
                actionBottomSheetFragment.getGif(gif).show(supportFragmentManager, "choose")
            }

        }
        gifAdapter = GifRecyclerViewAdapter(this, gifClickListener)
        binding.searchList.adapter = gifAdapter

    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SearchViewModel::class.java)



        viewModel.getSearch(API_KEY,"ronaldo",24).observe(this, Observer { gifs ->
            gifAdapter?.submitList(gifs)
        })
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
