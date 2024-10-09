package com.dogs.data.service

import com.dogs.data.models.BreedRemote
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BreedService {
    @GET("breeds")
    suspend fun breeds(): List<BreedRemote>


    @GET("breeds/search")
    suspend fun findBreeds(@Query("q") searchBreeds: String): List<BreedRemote>
}