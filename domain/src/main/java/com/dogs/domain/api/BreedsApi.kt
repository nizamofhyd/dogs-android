package com.dogs.domain.api

import com.dogs.domain.models.Breed
import kotlinx.coroutines.flow.Flow

interface BreedsApi {
    fun breeds(): Flow<List<Breed>>

    fun findBreeds(searchBreeds: String): Flow<List<Breed>>
}