package com.dogs.data.repository

import com.dogs.data.models.BreedRemote
import com.dogs.data.service.BreedService
import com.dogs.domain.api.BreedsApi
import com.dogs.domain.models.Breed
import com.dogs.domain.models.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

internal class BreedsRepositoryImpl @Inject constructor(private val retrofit: Retrofit) :
    BreedsApi {

    override fun breeds(): Flow<List<Breed>> {
        return flow {
            emit(retrofit.create(BreedService::class.java).breeds().map { breedRemote ->
                mapper(breedRemote)
            })
        }
    }

    override fun findBreeds(searchBreeds: String): Flow<List<Breed>> {
        return flow {
            emit(
                retrofit.create(BreedService::class.java).findBreeds(searchBreeds)
                    .map { breedRemote ->
                        mapper(breedRemote)
                    })
        }
    }

    private fun mapper(breedRemote: BreedRemote): Breed {
        return Breed(
            breedRemote.name,
            breedRemote.bredFor,
            breedRemote.lifeSpan,
            breedRemote.temperament,
            Image(breedRemote.image?.url, breedRemote.image?.width, breedRemote.image?.height)
        )
    }
}