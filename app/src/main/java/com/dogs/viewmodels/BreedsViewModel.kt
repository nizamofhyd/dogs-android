package com.dogs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.common.utils.CoroutineContextProvider
import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedsViewModel @Inject constructor(private val breedsUseCase: BreedsUseCase) : ViewModel() {

    val breedsLiveData = MutableLiveData<List<Breed>>()
    val selectedBreed = MutableLiveData<Breed>()

    fun fetchBreeds(coroutineContextProvider: CoroutineContextProvider) {
        CoroutineScope(coroutineContextProvider.IO).launch {
            val breedsList = breedsUseCase.breeds()
            withContext(coroutineContextProvider.Main) {
                breedsLiveData.postValue(breedsList)
            }
        }
    }

    fun updateSelectedBreed(breed: Breed) {
        selectedBreed.postValue(breed)
    }
}