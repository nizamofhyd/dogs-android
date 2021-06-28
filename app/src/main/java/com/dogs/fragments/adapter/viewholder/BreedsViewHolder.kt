package com.dogs.fragments.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dogs.databinding.BreedsListItemBinding
import com.dogs.domain.models.Breed

class BreedsViewHolder constructor(private val binding: BreedsListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(breed: Breed, position: Int) {
        with(binding) {
            root.tag = position
            Glide.with(root.context)
                .load(breed.image.url)
                .into(breedThumbnail)
            breedTitle.text = breed.name
        }
    }
}