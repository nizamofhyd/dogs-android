package com.dogs.di


import com.dogs.fragments.BreedDetailFragment
import com.dogs.fragments.BreedsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [DogViewModelModule::class])
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeBreedsListFragment(): BreedsListFragment

    @ContributesAndroidInjector
    abstract fun contributeBreedDetailFragment(): BreedDetailFragment
}