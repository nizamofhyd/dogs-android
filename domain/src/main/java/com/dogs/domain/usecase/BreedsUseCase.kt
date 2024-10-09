package com.dogs.domain.usecase

import com.dogs.data.models.BreedRemote
import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.models.Breed
import com.dogs.domain.models.Image
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class BreedsUseCaseImpl @Inject constructor(private val breedsRepository: BreedsRepository) :
    BreedsUseCase {

    override fun breeds(): Flow<List<Breed>> {
        return mapper(breedsRepository.breeds())
    }

    override fun findBreeds(searchBreeds: String): Flow<List<Breed>> {
        return mapper(breedsRepository.findBreeds(searchBreeds))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun mapper(breeds: Flow<List<BreedRemote>>): Flow<List<Breed>> {
        return breeds.mapLatest { it ->
            it.map {
                Breed(
                    it.name,
                    it.bredFor,
                    it.lifeSpan,
                    it.temperament,
                    Image(it.image?.url, it.image?.width, it.image?.height)
                )
            }
        }
    }
}


interface BreedsUseCase {
    fun breeds(): Flow<List<Breed>>

    fun findBreeds(searchBreeds: String): Flow<List<Breed>>
}
