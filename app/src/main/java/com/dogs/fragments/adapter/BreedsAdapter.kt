package com.dogs.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dogs.databinding.BreedsListItemBinding
import com.dogs.domain.models.Breed
import com.dogs.fragments.adapter.viewholder.BreedsViewHolder

class BreedsAdapter : RecyclerView.Adapter<BreedsViewHolder>() {

    private var breedsList: List<Breed> = emptyList()
    var onSelectedBreed: (breed: Breed) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val binding =
            BreedsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.apply {
            setOnClickListener {
                val selectedPosition = it.tag as? Int
                selectedPosition?.let {
                    onSelectedBreed.invoke(breedsList[selectedPosition])
                }
            }
        }
        return BreedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val commitItem = breedsList[position]
        holder.bind(commitItem, position)
    }

    override fun getItemCount(): Int {
        return breedsList.size
    }

    fun loadBreeds(breedsList: List<Breed>) {
        this.breedsList = breedsList
        notifyDataSetChanged()
    }
}