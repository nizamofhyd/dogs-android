package com.dogs.domain

import com.dogs.data.models.BreedRemote
import com.dogs.data.models.ImageRemote
import com.dogs.data.repository.BreedsRepository
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.domain.usecase.BreedsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedsUseCaseTest {

    @RelaxedMockK
    private lateinit var breedsRepository: BreedsRepository

    private var breedsUseCase: BreedsUseCase

    init {
        MockKAnnotations.init(this)
        breedsUseCase = BreedsUseCaseImpl(breedsRepository)
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun mockDogBreeds() =
        listOf(
            BreedRemote(
                "Dog1",
                "Racing",
                "3years",
                temperament = null,
                ImageRemote("https://www.dummyurl.com/1", 450, 230)
            ), BreedRemote(
                "Dog2",
                "Domestic",
                "9years",
                temperament = "Good",
                ImageRemote("https://www.dummyurl.com/2", 450, 230)
            )
        )


    @Test
    fun `Verify dog breeds returned from repository`() = runBlockingTest {
        //given
        coEvery {
            breedsRepository.breeds()
        } returns mockDogBreeds()

        //when
        val listOfBreeds = breedsUseCase.breeds()

        //then
        assert(listOfBreeds.size == mockDogBreeds().size)
    }
}