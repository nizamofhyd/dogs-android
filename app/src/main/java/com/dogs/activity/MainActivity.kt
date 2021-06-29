package com.dogs.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        return true
    }

    private fun initialiseView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, BreedsListFragment())
            .commit()
    }

    private fun observeEvents() {
        breedsViewModel.breedsViewState.observe(this, {
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
                    searchMenuItem.collapseActionView()
                    searchMenuItem.isVisible = it.showDogSearch
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
                Timber.d("BreedFilter Dog search text >> $this")
                breedsViewModel.searchDogBreed(this)
            }
        }
    }
}