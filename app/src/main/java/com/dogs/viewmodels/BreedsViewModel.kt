package com.dogs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val breedsUseCase: BreedsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedsUiState>(BreedsUiState.Loading)
    val uiState: StateFlow<BreedsUiState> = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is HttpException -> {
                _uiState.value = BreedsUiState.OnError(throwable.code(), throwable.message)
            }

            else -> _uiState.value = BreedsUiState.OnError(null, throwable.message)
        }

    }

    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        viewModelScope.launch(exceptionHandler) {
            breedsUseCase.breeds().collect { breedsList ->
                _uiState.value = BreedsUiState.ShowBreeds(breedsList)
            }
        }
    }

    fun findBreeds(searchBreeds: String) {
        if (searchBreeds.isEmpty()) {
            fetchBreeds()
        } else {
            viewModelScope.launch(exceptionHandler) {
                breedsUseCase.findBreeds(searchBreeds).collect { breedsList ->
                    _uiState.value = BreedsUiState.ShowBreeds(breedsList)
                }
            }
        }
    }

    sealed class BreedsUiState {
        data object Loading : BreedsUiState()

        data class ShowBreeds(val breedsList: List<Breed>) : BreedsUiState()

        data class OnError(val errorCode: Int?, val message: String?) : BreedsUiState()
    }
}