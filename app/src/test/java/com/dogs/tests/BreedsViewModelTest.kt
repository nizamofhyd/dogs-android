package com.dogs.tests

import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.viewmodels.BreedsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedsViewModelTest : BaseCoroutineTest() {

    private lateinit var breedsViewModel: BreedsViewModel

    @RelaxedMockK
    private lateinit var mockBreedsUseCase: BreedsUseCase

    @RelaxedMockK
    private lateinit var mockBreedsList: List<Breed>


    init {
        MockKAnnotations.init(this)
    }

    @Before
    override fun setUp() {
        super.setUp()
        // work around for breedsviewmodel init block execution
        breedsViewModel = BreedsViewModel(mockBreedsUseCase)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `Verify state to display list of dog breeds from view model`() = runTest {
        //given
        coEvery {
            mockBreedsUseCase.breeds()
        } coAnswers { flow { emit(mockBreedsList) } }

        //when
        breedsViewModel.fetchBreeds()

        //then
        assertEquals(
            BreedsViewModel.BreedsUiState.ShowBreeds(mockBreedsList),
            breedsViewModel.uiState.value
        )
    }

    @Test
    fun `Verify state on error during fetch of breeds list`() = runTest {
        //given
        coEvery {
            mockBreedsUseCase.breeds()
        } throws Exception("Unknown")

        //when
        breedsViewModel.fetchBreeds()

        //then
        assertEquals(
            BreedsViewModel.BreedsUiState.OnError(errorCode = null, message = "Unknown"),
            breedsViewModel.uiState.value
        )
    }
}