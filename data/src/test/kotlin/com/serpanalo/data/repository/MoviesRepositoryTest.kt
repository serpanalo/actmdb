package com.serpanalo.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.testshared.mockedMovie
import com.serpanalo.testshared.mockedVideo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: ServerDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MovieRepository

    private val apiKey = "XXXXXX"

    @Before
    fun setUp() {
        moviesRepository = MovieRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
    }

    @Test
    fun `getPopularMovies gets from local data source first`() {
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(5))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)
            val result = moviesRepository.getPopularMovies()
            assertEquals(localMovies, result)
        }
    }

    @Test
    fun `getFavoriteMovies gets from local data source first`() {
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(5))
            whenever(localDataSource.isEmpty()).thenReturn(false)

            whenever(localDataSource.getFavoriteMovies()).thenReturn(localMovies)

            val result = moviesRepository.getFavoriteMovies()
            assertEquals(localMovies, result)
        }
    }


    @Test
    fun `getVideosOfMovie gets from local data source first`() {
        runBlocking {

            val localVideos = listOf(mockedVideo.copy("5"))
            whenever(localDataSource.isEmpty()).thenReturn(false)

            whenever(localDataSource.findVideosOfMovieById("5")).thenReturn(localVideos)

            val result = moviesRepository.getVideosOfMovies("5")
            assertEquals(localVideos, result)
        }
    }

    @Test
    fun `getPopularMovies saves remote data to local`() {
        runBlocking {

            val remoteMovies = listOf(mockedMovie.copy(5))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularMovies(any(), any())).thenReturn(remoteMovies)
            whenever(regionRepository.findLastRegion()).thenReturn("US")

            moviesRepository.getPopularMovies()

            verify(localDataSource).saveMovies(remoteMovies)
        }
    }


    @Test
    fun `getVideosOfMovie saves remote data to local`() {
        runBlocking {

            val remoteVideos = listOf(mockedVideo.copy("5"))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getMovieVideos(any(), any(),"5")).thenReturn(remoteVideos)
            whenever(regionRepository.findLastRegion()).thenReturn("US")

            moviesRepository.getVideosOfMovies("5")

            verify(localDataSource).saveVideos(remoteVideos,"5")
        }
    }


    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val movie = mockedMovie.copy(idm = 5)
            whenever(localDataSource.findById(5)).thenReturn(movie)

            val result = moviesRepository.findById(5)
            assertEquals(movie, result)
        }
    }


    @Test
    fun `update updates local data source`() {
        runBlocking {

            val movie = mockedMovie.copy(idm = 5)

            moviesRepository.update(movie)

            verify(localDataSource).update(movie)
        }
    }





}