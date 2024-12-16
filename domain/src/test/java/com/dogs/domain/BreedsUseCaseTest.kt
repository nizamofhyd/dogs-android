package com.dogs.domain

import com.dogs.domain.api.BreedsApi
import com.dogs.domain.models.Breed
import com.dogs.domain.models.Image
import com.dogs.domain.usecase.BreedsUseCase
import com.dogs.domain.usecase.BreedsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BreedsUseCaseTest {

    @RelaxedMockK
    private lateinit var breedsApi: BreedsApi

    private var breedsUseCase: BreedsUseCase

    init {
        MockKAnnotations.init(this)
        breedsUseCase = BreedsUseCaseImpl(breedsApi)
    }

    private fun mockDogBreeds() = flow {
        emit(
            listOf(
                Breed(
                    "Dog1",
                    "Racing",
                    "3years",
                    temperament = null,
                    Image("https://www.dummyurl.com/1", 450, 230)
                ), Breed(
                    "Dog2",
                    "Domestic",
                    "9years",
                    temperament = "Good",
                    Image("https://www.dummyurl.com/2", 450, 230)
                )
            )
        )
    }


    @Test
    fun `Verify dog breeds returned from repository`() = runTest {
        //given
        coEvery {
            breedsApi.breeds()
        } returns mockDogBreeds()

        //when
        val listOfBreeds = breedsUseCase.breeds()

        //then
        assert(listOfBreeds.first().size == mockDogBreeds().first().size)
    }
}