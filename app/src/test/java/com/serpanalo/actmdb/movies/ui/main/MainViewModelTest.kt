package com.serpanalo.actmdb.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.actmdb.ui.main.HomeViewModel
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.data.repository.RegionRepository
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.testshared.mockedMovie
import com.serpanalo.usecase.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {



    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: ServerDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MovieRepository

    private val apiKey = "XXXXXX"


    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observer: Observer<HomeViewModel.UiModel>

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        moviesRepository = MovieRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
        vm = HomeViewModel(moviesRepository)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.model.observeForever(observer)

        verify(observer).onChanged(HomeViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(idm = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(HomeViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(idm = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(HomeViewModel.UiModel.Content(movies))
        }
    }

}