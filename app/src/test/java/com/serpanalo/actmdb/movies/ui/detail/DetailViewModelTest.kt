package com.serpanalo.actmdb.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.serpanalo.actmdb.ui.detail.DetailViewModel
import com.serpanalo.testshared.mockedMovie
import com.serpanalo.usecase.FindMovieById
import com.serpanalo.usecase.FindMovieVideosById
import com.serpanalo.usecase.GetMovieVideosById
import com.serpanalo.usecase.ToggleMovieFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var findMovieVideosById: FindMovieVideosById

    @Mock
    lateinit var getMovieVideosById: GetMovieVideosById

    @Mock
    lateinit var any: Any

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(
            5,
            findMovieById,
            toggleMovieFavorite,
            findMovieVideosById,
            getMovieVideosById,
            any
        )
    }

    @Test
    fun `observing LiveData finds the movie`() {

        runBlocking {
            val movie = mockedMovie.copy(idm = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)

            vm.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel(movie))
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val movie = mockedMovie.copy(idm = 1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))
            vm.model.observeForever(observer)

            vm.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }
    }
}