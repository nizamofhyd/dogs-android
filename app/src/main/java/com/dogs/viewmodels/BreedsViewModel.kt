package com.dogs.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val breedsUseCase: BreedsUseCase) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is HttpException -> {
                _breedsViewState.postValue(
                    BreedsViewState.OnError(
                        throwable.code(),
                        throwable.message
                    )
                )
            }

            else -> _breedsViewState.postValue(BreedsViewState.OnError(null, throwable.message))
        }

    }

    fun fetchBreeds() {
        viewModelScope.launch(exceptionHandler) {
            breedsUseCase.breeds().collect { breedsList ->
                _breedsViewState.value = BreedsViewState.ShowBreeds(breedsList)
            }
        }
    }

    fun updateSelectedBreed(breed: Breed) {
        _breedsViewState.value = BreedsViewState.ShowBreedDetail(breed)
    }

    fun searchDogBreed(breedSearch: String?) {
        _breedsViewState.value = BreedsViewState.BreedSearch(breedSearch)
    }

    sealed class BreedsViewState constructor(val showDogSearch: Boolean = true) {
        data class ShowBreeds(val breedsList: List<Breed>) : BreedsViewState()
        data class ShowBreedDetail(val breed: Breed) : BreedsViewState(showDogSearch = false)
        data class BreedSearch(val breedSearchText: String?) : BreedsViewState()
        data class OnError(val errorCode: Int?, val message: String?) : BreedsViewState()
    }
}