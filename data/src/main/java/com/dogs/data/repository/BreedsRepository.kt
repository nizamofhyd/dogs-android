package com.dogs.data.repository

import com.dogs.data.models.BreedRemote
import com.dogs.data.service.BreedService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

internal class BreedsRepositoryImpl @Inject constructor(private val retrofit: Retrofit) :
    BreedsRepository {

    override fun breeds(): Flow<List<BreedRemote>> {
        return flow {
            emit(retrofit.create(BreedService::class.java).breeds())
        }
    }
}

interface BreedsRepository {
    fun breeds(): Flow<List<BreedRemote>>
}