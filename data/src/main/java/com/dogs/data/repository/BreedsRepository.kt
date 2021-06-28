package com.dogs.data.repository

import com.dogs.data.models.BreedRemote
import com.dogs.data.service.BreedService
import retrofit2.Retrofit
import javax.inject.Inject

internal class BreedsRepositoryImpl @Inject constructor(private val retrofit: Retrofit) : BreedsRepository {

    override suspend fun breeds(): List<BreedRemote> {
        return retrofit.create(BreedService::class.java).breeds()
    }
}

interface BreedsRepository {
    suspend fun breeds(): List<BreedRemote>
}