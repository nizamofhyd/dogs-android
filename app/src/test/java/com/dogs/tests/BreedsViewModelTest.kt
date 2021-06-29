package com.dogs.tests

import androidx.lifecycle.Observer
import com.dogs.domain.models.Breed
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.viewmodels.BreedsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedsViewModelTest : BaseCoroutineTest() {

    @InjectMockKs
    private lateinit var breedsViewModel: BreedsViewModel

    @RelaxedMockK
    private lateinit var mockBreedsViewStateLiveDataObserver: Observer<BreedsViewModel.BreedsViewState>

    @RelaxedMockK
    private lateinit var mockBreedsUseCase: BreedsUseCase

    @RelaxedMockK
    private lateinit var mockBreedsList: List<Breed>

    @RelaxedMockK
    private lateinit var mockBreed: Breed

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    init {
        MockKAnnotations.init(this)
    }

    @Before
    override fun setUp() {
        super.setUp()
        breedsViewModel.breedsViewState.observeForever(mockBreedsViewStateLiveDataObserver)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        breedsViewModel.breedsViewState.removeObserver(mockBreedsViewStateLiveDataObserver)
    }

    @Test
    fun `Verify state to display list of dog breeds from view model`() {
        //given
        val slot = slot<BreedsViewModel.BreedsViewState.ShowBreeds>()
        lateinit var breedsViewState: BreedsViewModel.BreedsViewState.ShowBreeds
        coEvery {
            mockBreedsUseCase.breeds()
        } returns mockBreedsList
        every { mockBreedsViewStateLiveDataObserver.onChanged(capture(slot)) } answers {
            breedsViewState = slot.captured
        }

        //when
        breedsViewModel.fetchBreeds(testCoroutineContextProvider)

        //then
        verify {
            mockBreedsViewStateLiveDataObserver.onChanged(breedsViewState)
        }
    }

    @Test
    fun `Verify state to display detail view of a breed`() {
        //given
        val slot = slot<BreedsViewModel.BreedsViewState.ShowBreedDetail>()
        lateinit var breedsViewState: BreedsViewModel.BreedsViewState.ShowBreedDetail
        every { mockBreedsViewStateLiveDataObserver.onChanged(capture(slot)) } answers {
            breedsViewState = slot.captured
        }

        //when
        breedsViewModel.updateSelectedBreed(mockBreed)

        //then
        verify {
            mockBreedsViewStateLiveDataObserver.onChanged(breedsViewState)
        }
    }
}