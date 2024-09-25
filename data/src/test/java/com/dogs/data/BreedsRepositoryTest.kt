package com.dogs.data

import com.dogs.data.models.BreedRemote
import com.dogs.data.models.ImageRemote
import com.dogs.data.repository.BreedsRepository
import com.dogs.data.repository.BreedsRepositoryImpl
import com.dogs.data.service.BreedService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class BreedsRepositoryTest {

    @RelaxedMockK
    private lateinit var retrofit: Retrofit

    private var breedsRepository: BreedsRepository

    init {
        MockKAnnotations.init(this)
        breedsRepository = BreedsRepositoryImpl(retrofit)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Verify api returns valid data`() = runTest {
        //given
        coEvery {
            retrofit.create(BreedService::class.java).breeds()
        } returns mockDogBreeds()

        //when
        val dogBreedsList = breedsRepository.breeds()

        //then
        assert(dogBreedsList.size == mockDogBreeds().size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = HttpException::class)
    fun `Verify api returns error`() = runTest {
        //given
        coEvery {
            retrofit.create(BreedService::class.java).breeds()
        } throws HttpException(Response.error<String>(404, ResponseBody.create(null, "error")))

        //when
        breedsRepository.breeds()
    }
}