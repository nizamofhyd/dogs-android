package com.dogs.domain.di

import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.domain.usecase.BreedsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideBreedsUseCase(breedsRepository: BreedsRepository): BreedsUseCase {
        return BreedsUseCaseImpl(breedsRepository)
    }
}