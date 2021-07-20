package com.dogs.fragments

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.dogs.R
import com.dogs.common.utils.CoroutineContextProvider
import com.dogs.databinding.FragmentBreedsBinding
import com.dogs.di.ViewModelFactory
import com.dogs.fragments.adapter.BreedsAdapter
import com.dogs.utils.viewBinding
import com.dogs.viewmodels.BreedsViewModel
import javax.inject.Inject

class BreedsListFragment : InjectedBaseFragment(R.layout.fragment_breeds) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    private val breedsViewModel by activityViewModels<BreedsViewModel> { viewModelFactory }

    private val binding by viewBinding(FragmentBreedsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        initialiseView()
    }

    private fun initialiseView() {
        with(binding) {
            breedsList.apply {
                adapter = BreedsAdapter().apply {
                    onSelectedBreed = {
                        breedsViewModel.updateSelectedBreed(it)
                    }
                }
            }
            fetchDogBreeds()
        }
    }


    private fun observeEvents() {
        breedsViewModel.breedsViewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BreedsViewModel.BreedsViewState.ShowBreeds -> {
                    val breedsAdapter = binding.breedsList.adapter as BreedsAdapter
                    breedsAdapter.loadBreeds(it.breedsList)
                    binding.breedsListProgressView.visibility = GONE
                }

                is BreedsViewModel.BreedsViewState.BreedSearch -> {
                    val breedsAdapter = binding.breedsList.adapter as BreedsAdapter
                    breedsAdapter.filter.filter(it.breedSearchText)
                }
                else -> {
                }
            }
        })
    }

    private fun fetchDogBreeds() {
        breedsViewModel.fetchBreeds(coroutineContextProvider)
    }
}