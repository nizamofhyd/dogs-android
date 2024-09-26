package com.dogs.domain.usecase

import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.models.Breed
import com.dogs.domain.models.Image
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class BreedsUseCaseImpl @Inject constructor(private val breedsRepository: BreedsRepository) :
    BreedsUseCase {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun breeds(): Flow<List<Breed>> {
        return breedsRepository.breeds().mapLatest { it ->
            it.map {
                Breed(
                    it.name,
                    it.bredFor,
                    it.lifeSpan,
                    it.temperament,
                    Image(it.image.url, it.image.width, it.image.height)
                )
            }
        }
    }
}

interface BreedsUseCase {
    fun breeds(): Flow<List<Breed>>
}
