package com.serpanalo.actmdb.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.serpanalo.actmdb.movies.ui.FakeLocalDataSource
import com.serpanalo.actmdb.movies.ui.defaultFakeMovies
import com.serpanalo.actmdb.movies.ui.initMockedDi
import com.serpanalo.actmdb.ui.main.HomeViewModel
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.testshared.mockedMovie
import com.serpanalo.usecase.GetPopularMovies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<HomeViewModel.UiModel>

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { HomeViewModel(get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(HomeViewModel.UiModel.Content(defaultFakeMovies))
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(HomeViewModel.UiModel.Content(fakeLocalMovies))
    }
}