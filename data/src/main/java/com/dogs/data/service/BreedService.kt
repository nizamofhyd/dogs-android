package com.dogs.data.service

import com.dogs.data.models.BreedRemote
import retrofit2.http.GET

internal interface BreedService {
    @GET("breeds")
    suspend fun breeds(): List<BreedRemote>
}