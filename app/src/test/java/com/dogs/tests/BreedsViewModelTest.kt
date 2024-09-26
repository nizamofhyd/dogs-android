package com.dogs.tests

import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.viewmodels.BreedsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedsViewModelTest : BaseCoroutineTest() {

    @InjectMockKs
    private lateinit var breedsViewModel: BreedsViewModel

    @RelaxedMockK
    private lateinit var mockBreedsUseCase: BreedsUseCase

    @RelaxedMockK
    private lateinit var mockBreedsList: List<Breed>

    @RelaxedMockK
    private lateinit var mockBreed: Breed

    init {
        MockKAnnotations.init(this)
    }

    @Before
    override fun setUp() {
        super.setUp()
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `Verify state to display list of dog breeds from view model`() {
        //given
        coEvery {
            mockBreedsUseCase.breeds()
        } coAnswers { flow { emit(mockBreedsList) } }

        //when
        breedsViewModel.fetchBreeds()

        //then
        assertEquals(
            BreedsViewModel.BreedsViewState.ShowBreeds(mockBreedsList),
            breedsViewModel.breedsViewState.value
        )
    }

    @Test
    fun `Verify state on error during fetch of breeds list`() {
        //given
        coEvery {
            mockBreedsUseCase.breeds()
        } throws Exception("Unknown")

        //when
        breedsViewModel.fetchBreeds()

        //then
        assertEquals(
            BreedsViewModel.BreedsViewState.OnError(errorCode = null, message = "Unknown"),
            breedsViewModel.breedsViewState.value
        )
    }

    @Test
    fun `Verify state to display detail view of a breed`() {
        //given

        //when
        breedsViewModel.updateSelectedBreed(mockBreed)

        //then
        assertEquals(
            BreedsViewModel.BreedsViewState.ShowBreedDetail(mockBreed),
            breedsViewModel.breedsViewState.value
        )
    }
}