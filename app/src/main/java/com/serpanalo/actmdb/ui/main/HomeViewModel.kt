package com.serpanalo.actmdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serpanalo.actmdb.ui.common.Event
import com.serpanalo.actmdb.ui.common.Scope
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Movie
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel(),
    Scope by Scope.Impl() {


    sealed class UiModel() {
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }


    private val _navigation = MutableLiveData<Event<Movie>>()

    val navigation: LiveData<Event<Movie>> = _navigation

    init {
        initScope()
    }


    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }


    fun onCoarsePermissionRequested() {

        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(movieRepository.getPopularMovies())
        }

    }

    fun onMovieClicked(movie: Movie) {
        _navigation.value = Event(movie)
    }

    override fun onCleared() {
        destroyScope()
    }

}