package com.dogs.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dogs.R
import com.dogs.di.Injectable
import com.dogs.di.ViewModelFactory
import com.dogs.fragments.BreedDetailFragment
import com.dogs.fragments.BreedsListFragment
import com.dogs.viewmodels.BreedsViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val breedsViewModel by viewModels<BreedsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeEvents()
        initialiseView()
    }

    private fun initialiseView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, BreedsListFragment())
            .commit()
    }

    private fun observeEvents() {
        breedsViewModel.selectedBreed.observe( this, Observer {
            val fragment = BreedDetailFragment()
                .apply {
                    val bundle = Bundle()
                    bundle.putParcelable(BreedDetailFragment.KEY_BREED, it)
                    arguments = bundle
                }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(BreedDetailFragment.TAG)
                .commit()
        })
    }
}