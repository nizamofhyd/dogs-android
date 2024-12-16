package com.dogs.domain.usecase

import com.dogs.domain.api.BreedsApi
import com.dogs.domain.models.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BreedsUseCaseImpl @Inject constructor(private val breedsApi: BreedsApi) :
    BreedsUseCase {

    override fun breeds(): Flow<List<Breed>> {
        return breedsApi.breeds()
    }

    override fun findBreeds(searchBreeds: String): Flow<List<Breed>> {
        return breedsApi.findBreeds(searchBreeds)
    }
}


interface BreedsUseCase {
    fun breeds(): Flow<List<Breed>>

    fun findBreeds(searchBreeds: String): Flow<List<Breed>>
}
