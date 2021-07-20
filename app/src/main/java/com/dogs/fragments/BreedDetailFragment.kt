package com.dogs.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.dogs.R
import com.dogs.databinding.FragmentBreedDetailBinding
import com.dogs.domain.models.Breed
import com.dogs.utils.viewBinding

class BreedDetailFragment : InjectedBaseFragment(R.layout.fragment_breed_detail) {

    private val binding by viewBinding(FragmentBreedDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initialiseView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.search_dog)
        searchMenuItem?.isVisible = false
    }

    private fun initialiseView() {
        val breed = arguments?.getParcelable<Breed>(KEY_BREED)
        breed?.let { breed ->
            bindBreed(breed)
        }
    }

    private fun bindBreed(breed: Breed) {
        context?.run {
            with(binding) {
                Glide.with(this@run)
                    .load(breed.image.url)
                    .into(breedThumbnail)
                breedTitle.text = breed.name
                breedLifeSpan.text = breed.lifeSpan
                breed.bredFor?.let {
                    breedBredFor.text = it
                }
                breed.temperament?.let {
                    breedTemperament.text = it
                }
            }
        }
    }

    companion object {
        const val TAG = "BreedDetailFragment_Tag"
        const val KEY_BREED = "Breed"
    }
}