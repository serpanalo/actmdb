package com.serpanalo.actmdb.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.serpanalo.actmdb.movies.ui.FakeLocalDataSource
import com.serpanalo.actmdb.movies.ui.defaultFakeMovies
import com.serpanalo.actmdb.movies.ui.initMockedDi
import com.serpanalo.actmdb.ui.detail.DetailViewModel
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.testshared.mockedMovie
import com.serpanalo.testshared.mockedVideo
import com.serpanalo.testshared.mockedVideos
import com.serpanalo.usecase.FindMovieById
import com.serpanalo.usecase.ToggleMovieFavorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    @Mock
    lateinit var observerVideo: Observer<DetailViewModel.UiModelVideo>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get(),get(), any()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(5) }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @Test
    fun `observing LiveData finds the movie`() {
        vm.model.observeForever(observer)
        verify(observer).onChanged(DetailViewModel.UiModel(mockedMovie.copy(5)))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.model.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(5).favorite)
        }
    }

    @Test
    fun `observing LiveData finds videos of the movie`() {
        vm.modelVideo.observeForever(observerVideo)
        verify(observerVideo).onChanged(DetailViewModel.UiModelVideo(listOf(mockedVideo.copy("5"))))

    }



}