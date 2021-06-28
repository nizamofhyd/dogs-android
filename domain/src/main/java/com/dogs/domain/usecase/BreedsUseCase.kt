package com.dogs.domain.usecase

import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.models.Breed
import com.dogs.domain.models.Image
import javax.inject.Inject

internal class BreedsUseCaseImpl @Inject constructor(private val breedsRepository: BreedsRepository) : BreedsUseCase {

    override suspend fun breeds(): List<Breed> {
        return breedsRepository.breeds().map {
            Breed(it.name, it.bredFor, it.lifeSpan, it.temperament, Image(it.image.url, it.image.width, it.image.height))
        }.toList()
    }
}

interface BreedsUseCase {
    suspend fun breeds(): List<Breed>
}
