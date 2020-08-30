package com.serpanalo.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.usecase.GetMovieVideosById
import com.serpanalo.usecase.GetPopularMovies
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesOfMovieTest {

    @Mock
    lateinit var moviesRepository: MovieRepository

    lateinit var getVideosOfMovie: GetMovieVideosById

    @Before
    fun setUp() {
        getVideosOfMovie = GetMovieVideosById(moviesRepository)
    }

    @Test
    fun `invoke calls videos repository`() {
        runBlocking {

            val videos = listOf(mockedVideo.copy("5"))

            whenever(moviesRepository.getVideosOfMovies("5")).thenReturn(videos)

            val result = getVideosOfMovie.invoke("5")

            Assert.assertEquals(videos, result)
        }
    }
}