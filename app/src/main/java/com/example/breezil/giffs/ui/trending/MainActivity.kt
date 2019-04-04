package com.example.breezil.giffs.ui.trending

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
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
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.callbacks.GifClickListener
import com.example.breezil.giffs.R
import com.example.breezil.giffs.databinding.ActivityMainBinding
import com.example.breezil.giffs.ui.BaseActivity
import com.example.breezil.giffs.ui.adapter.GifPagedRecyclerViewAdapter
import com.example.breezil.giffs.ui.bottom_sheet.ActionBottomSheetFragment
import com.example.breezil.giffs.ui.saved.SavedActivity
import com.example.breezil.giffs.ui.search.SearchActivity
import com.example.breezil.giffs.ui.adapter.GifRecyclerViewAdapter
import com.example.breezil.giffs.ui.preference.AboutActivity
import com.example.breezil.giffs.ui.preference.PreferenceFragment
import com.example.breezil.giffs.utils.BottomNavigationHelper
import dagger.android.AndroidInjection
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    internal var gifAdapter: GifRecyclerViewAdapter? = null
    internal var gifAdapters: GifPagedRecyclerViewAdapter? = null

    lateinit var viewModel : MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    internal var actionBottomSheetFragment = ActionBottomSheetFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AndroidInjection.inject(this)

        setUpBottomNavigation()

        binding.mainGifList.setHasFixedSize(true)


        setUpAdapter()
        setUpPageViewModel()

        val logging = HttpLoggingInterceptor { message -> Timber.tag(getString(R.string.okhttp)).d(message) }
        logging.redactHeader(getString(R.string.authorization))
        logging.redactHeader(getString(R.string.cookie))

        if (internetConnected()) {
            binding.swipeRefresh.setOnRefreshListener { refresh() }
        }

        supportActionBar!!.title = getString(R.string.trending)
    }
    private fun setUpAdapter(){

        val gifClickListener = object : GifClickListener {
            override fun clickGif(gif: Gif) {
                actionBottomSheetFragment.getGif(gif).show(supportFragmentManager, "choose")
            }
        }
        gifAdapter = GifRecyclerViewAdapter(this, gifClickListener)
        gifAdapters = GifPagedRecyclerViewAdapter(this, gifClickListener)
        binding.mainGifList.adapter = gifAdapters

    }



    private fun setUpPageViewModel(){
        if(internetConnected()){
            binding.swipeRefresh.visibility = View.VISIBLE
            binding.swipeRefresh.setColorSchemeResources(
                R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink
            )

            viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
            viewModel.gifList.observe(this, Observer { gifs ->
                gifAdapters?.submitList(gifs)
            })
        }else{

        }

        binding.swipeRefresh.isRefreshing = false

    }

    private fun setUpBottomNavigation(){
        BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)

        val menu = binding.bottomNavViewBar.menu
        val menuItem = menu.getItem(0)
        menuItem.isChecked = true

        /*
         * here sets the navigations to its corresponding activities
         */
        binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.trending -> {
                }
                R.id.search -> {
                    startActivity(Intent(this@MainActivity, SearchActivity::class.java))
                    finish()
                }
                R.id.saved -> {
                    startActivity(Intent(this@MainActivity, SavedActivity::class.java))
                    finish()
                }

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
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }


        return true
    }

    fun preference(){
        val preferenceFragment = PreferenceFragment()
        preferenceFragment.isCancelable = false
        preferenceFragment.show(supportFragmentManager, "show")
    }

    fun refresh(){
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.gifList.observe(this, Observer { gifs ->
            gifAdapters?.submitList(gifs)
        })

        binding.swipeRefresh.isRefreshing = false

    }


    fun internetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
