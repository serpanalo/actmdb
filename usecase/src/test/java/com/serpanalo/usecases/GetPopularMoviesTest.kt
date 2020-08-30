package com.serpanalo.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.usecase.GetPopularMovies
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesTest {

    @Mock
    lateinit var moviesRepository: MovieRepository

    lateinit var getPopularMovies: GetPopularMovies

    @Before
    fun setUp() {
        getPopularMovies = GetPopularMovies(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(idm = 5))
            whenever(moviesRepository.getPopularMovies()).thenReturn(movies)

            val result = getPopularMovies.invoke()

            Assert.assertEquals(movies, result)
        }
    }
}