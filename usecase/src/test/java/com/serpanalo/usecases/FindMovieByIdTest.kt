package com.serpanalo.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.usecase.FindMovieById
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindMovieByIdTest {

    @Mock
    lateinit var moviesRepository: MovieRepository

    lateinit var findMovieById: FindMovieById

    @Before
    fun setUp() {
        findMovieById = FindMovieById(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {

            val movie = mockedMovie.copy(idm = 5)
            whenever(moviesRepository.findById(5)).thenReturn(movie)

            val result = findMovieById.invoke(5)

            assertEquals(movie, result)
        }
    }
}