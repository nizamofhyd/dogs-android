package com.dogs.domain.di

import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.domain.usecase.BreedsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideBreedsUseCase(breedsRepository: BreedsRepository): BreedsUseCase {
        return BreedsUseCaseImpl(breedsRepository)
    }
}