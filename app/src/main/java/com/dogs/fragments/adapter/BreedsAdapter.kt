package com.dogs.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dogs.databinding.BreedsListItemBinding
import com.dogs.domain.models.Breed
import com.dogs.fragments.adapter.viewholder.BreedsViewHolder
import timber.log.Timber

class BreedsAdapter : RecyclerView.Adapter<BreedsViewHolder>(), Filterable {

    private var originalBreedList: List<Breed> = emptyList()
    private var displayBreedList: List<Breed> = emptyList()
    private val breedFilter = BreedFilter(this)
    var onSelectedBreed: (breed: Breed) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val binding =
            BreedsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.apply {
            setOnClickListener {
                val selectedPosition = it.tag as? Int
                selectedPosition?.let {
                    onSelectedBreed.invoke(displayBreedList[selectedPosition])
                }
            }
        }
        return BreedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val commitItem = displayBreedList[position]
        holder.bind(commitItem, position)
    }

    override fun getItemCount(): Int {
        return displayBreedList.size
    }

    fun loadBreeds(breedsList: List<Breed>) {
        this.originalBreedList = breedsList
        this.displayBreedList = breedsList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return breedFilter
    }

    private class BreedFilter constructor(val breedsAdapter: BreedsAdapter) : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            Timber.d("BreedFilter => $charSequence")
            val filterResults = FilterResults()
            if (charSequence == null) {
                filterResults.values = breedsAdapter.originalBreedList
            } else {
                val filteredBreeds = breedsAdapter.originalBreedList.filter {
                    it.name.contains(charSequence.toString(), ignoreCase = true)
                }.toList()
                filterResults.values = filteredBreeds
            }
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
            filterResults?.let {
                breedsAdapter.displayBreedList = it.values as List<Breed>
                breedsAdapter.notifyDataSetChanged()
            }
        }
    }
}