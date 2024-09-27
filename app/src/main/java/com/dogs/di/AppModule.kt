package com.dogs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideTimber(): Timber.Tree {
        return Timber.DebugTree()
    }
}