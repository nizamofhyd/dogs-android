package com.dogs.fragments

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
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
                    onNoFilteredResults = {
                        showNoFilteredResults()
                    }
                }
            }
            fetchDogBreeds()
        }
    }


    private fun observeEvents() {
        breedsViewModel.breedsViewState.observe(viewLifecycleOwner, Observer {
            val breedsAdapter = binding.breedsList.adapter as BreedsAdapter
            when (it) {
                is BreedsViewModel.BreedsViewState.ShowBreeds -> {
                    breedsAdapter.loadBreeds(it.breedsList)
                    binding.breedsListProgressView.visibility = GONE
                }

                is BreedsViewModel.BreedsViewState.BreedSearch -> {
                    breedsAdapter.filter.filter(it.breedSearchText)
                }
                is BreedsViewModel.BreedsViewState.OnError -> {
                    binding.breedsListProgressView.visibility = GONE
                    Toast.makeText(
                        context,
                        "${getString(R.string.error_prefix)} ${it.message} ${getString(R.string.error_try_later)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                }
            }
        })
    }

    private fun fetchDogBreeds() {
        breedsViewModel.fetchBreeds(coroutineContextProvider)
    }

    private fun showNoFilteredResults() {
        Toast.makeText(
            context,
            getString(R.string.search_no_results),
            Toast.LENGTH_SHORT
        ).show()
    }
}