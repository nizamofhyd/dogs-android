package com.dogs.fragments

import android.os.Bundle
import android.view.View
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
        binding.breedsList.apply {
            adapter = BreedsAdapter()
        }
        fetchDogBreeds()
    }

    private fun observeEvents() {
        breedsViewModel.breedsLiveData.observe(viewLifecycleOwner, Observer {
            val breedsAdapter = binding.breedsList.adapter as BreedsAdapter
            breedsAdapter.loadBreeds(it)
        })
    }

    private fun fetchDogBreeds() {
        breedsViewModel.fetchBreeds(coroutineContextProvider)
    }
}