package com.dogs.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.dogs.R
import com.dogs.di.Injectable
import com.dogs.di.ViewModelFactory
import com.dogs.fragments.BreedDetailFragment
import com.dogs.fragments.BreedsListFragment
import com.dogs.viewmodels.BreedsViewModel
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val breedsViewModel by viewModels<BreedsViewModel> { viewModelFactory }

    private lateinit var searchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
        observeEvents()
        initialiseView()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.run {
            handleIntent(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchMenuItem = menu.findItem(R.id.search_dog)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                breedsViewModel.searchDogBreed(null)
                return true
            }
        })
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            supportActionBar?.apply {
                setTitle(R.string.app_name)
                setDisplayHomeAsUpEnabled(false)
            }
        }
        super.onBackPressed()
    }

    private fun initialiseView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, BreedsListFragment())
            .commit()
    }

    private fun observeEvents() {
        breedsViewModel.breedsViewState.observe(this, {
            Timber.d("Search observeEvents() >>>> $it")
            when (it) {
                is BreedsViewModel.BreedsViewState.ShowBreedDetail -> {
                    val fragment = BreedDetailFragment()
                        .apply {
                            val bundle = Bundle()
                            bundle.putParcelable(BreedDetailFragment.KEY_BREED, it.breed)
                            arguments = bundle
                        }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, fragment)
                        .addToBackStack(BreedDetailFragment.TAG)
                        .commit()
                    supportActionBar?.apply {
                        setTitle(R.string.header_details)
                        setDisplayHomeAsUpEnabled(true)
                    }
                }
                is BreedsViewModel.BreedsViewState.ShowBreeds -> {
                    searchMenuItem.isVisible = it.showDogSearch
                }
                else -> {
                }
            }
        })
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val dogSearchText = intent.getStringExtra(SearchManager.QUERY)
            dogSearchText?.run {
                breedsViewModel.searchDogBreed(this)
            }
        }
    }
}