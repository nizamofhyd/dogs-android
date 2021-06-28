package com.dogs.di

import com.dogs.common.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider()
    }
}