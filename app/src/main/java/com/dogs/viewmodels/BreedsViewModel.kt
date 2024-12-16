package com.dogs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val breedsUseCase: BreedsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedsUiState>(BreedsUiState.Loading)
    val uiState: StateFlow<BreedsUiState> = _uiState.asStateFlow()

    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        viewModelScope.launch {
            breedsUseCase.breeds()
                .onStart {
                    _uiState.value = BreedsUiState.Loading
                }
                .catch { throwable ->
                    _uiState.value = BreedsUiState.OnError(null, throwable.message)
                }
                .collect { breedsList ->
                    _uiState.value = BreedsUiState.ShowBreeds(breedsList)
                }
        }
    }

    fun findBreeds(searchBreeds: String) {
        if (searchBreeds.isEmpty()) {
            fetchBreeds()
        } else {
            viewModelScope.launch {
                breedsUseCase.findBreeds(searchBreeds)
                    .catch { throwable ->
                        _uiState.value = BreedsUiState.OnError(null, throwable.message)
                    }
                    .collect { breedsList ->
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