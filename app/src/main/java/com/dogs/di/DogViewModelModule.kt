package com.dogs.di

import androidx.lifecycle.ViewModel
import com.dogs.viewmodels.BreedsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DogViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BreedsViewModel::class)
    internal abstract fun bindBreedsViewModel(viewModel: BreedsViewModel): ViewModel
}