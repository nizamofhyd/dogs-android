package com.dogs.data.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder().apply {
                header(API_KEY_NAME, API_KEY)
            }.build()
            chain.proceed(newRequest)
        }
        return httpClient.build()
    }
}

private const val BASE_URL = "https://api.thedogapi.com/v1"
private const val API_KEY_NAME = "x-api-key"
private const val API_KEY = "00514b15-d48f-401b-bd4c-76f3e532d1af"