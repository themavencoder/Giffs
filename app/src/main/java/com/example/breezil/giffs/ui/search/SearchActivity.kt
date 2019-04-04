package com.example.breezil.giffs.ui.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breezil.giffs.R
import com.example.breezil.giffs.callbacks.GifClickListener
import com.example.breezil.giffs.callbacks.QuickSearchListener
import com.example.breezil.giffs.databinding.ActivitySearchBinding
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.ui.BaseActivity
import com.example.breezil.giffs.ui.adapter.GifPagedRecyclerViewAdapter
import com.example.breezil.giffs.ui.adapter.QuickSearchRecyclerListAdapter
import com.example.breezil.giffs.ui.bottom_sheet.ActionBottomSheetFragment
import com.example.breezil.giffs.ui.preference.PreferenceFragment
import com.example.breezil.giffs.ui.saved.SavedActivity
import com.example.breezil.giffs.ui.trending.MainActivity
import com.example.breezil.giffs.utils.BottomNavigationHelper
import dagger.android.AndroidInjection
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SearchActivity : BaseActivity() {

    lateinit var binding : ActivitySearchBinding

    internal var gifAdapter: GifPagedRecyclerViewAdapter? = null

    lateinit var viewModel : SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    internal var actionBottomSheetFragment = ActionBottomSheetFragment()

    lateinit var quickSearchList: List<String>
    lateinit var quickSearchRecyclerListAdapter: QuickSearchRecyclerListAdapter
    var searchText: String = "happy"

    internal var theme : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs : SharedPreferences =this@SearchActivity.getSharedPreferences(getString(R.string.PACKAGE_NAME),
            Context.MODE_PRIVATE
        )

        theme = sharedPrefs.getBoolean(getString(R.string.theme_state), false)
        if (theme) {
            setTheme(R.style.DarkNoActionTheme)
        } else {
            setTheme(R.style.AppNoActionTheme)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        AndroidInjection.inject(this)

        setUpBottomNavigation()

        binding.searchList.setHasFixedSize(true)


        setUpAdapter()
        setUpViewModel()

        val logging = HttpLoggingInterceptor { message -> Timber.tag(getString(R.string.okhttp)).d(message) }
        logging.redactHeader(getString(R.string.authorization))
        logging.redactHeader(getString(R.string.cookie))


        search()


    }



    private fun setUpAdapter(){

        val gifClickListener = object : GifClickListener {
            override fun clickGif(gif: Gif) {
                actionBottomSheetFragment.getGif(gif).show(supportFragmentManager, "choose")
            }

        }

        val quickSearchListener = object : QuickSearchListener{
            override fun getString(string: String) {
                binding.searchView.setQuery(string, true)
                refresh(string)
            }
        }

        gifAdapter = GifPagedRecyclerViewAdapter(this, gifClickListener)
        binding.searchList.adapter = gifAdapter

        val textArray = resources.getStringArray(R.array.search_list)

        quickSearchList = Arrays.asList(*textArray)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        quickSearchRecyclerListAdapter = QuickSearchRecyclerListAdapter(quickSearchList, quickSearchListener)
        binding.quickChooseList.layoutManager = layoutManager
        binding.quickChooseList.adapter = quickSearchRecyclerListAdapter
        Collections.shuffle(quickSearchList)
        quickSearchRecyclerListAdapter.setList(quickSearchList)

    }



    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SearchViewModel::class.java)

        if(searchText.isEmpty()){
            viewModel.setParameter("happy")
        }else{
            viewModel.setParameter("happy")
        }

        viewModel.getSearchList().observe(this, Observer {
            gifAdapter?.submitList(it)
        })
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    refresh(query)
                    searchText = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    refresh(newText)
                }
                return true
            }
        })
    }

    private fun refresh(search : String){
        viewModel.setParameter(search)
        viewModel.refreshGifs().observe(this, Observer { gifs ->
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

            }


            false
        }
    }






}
