package com.serpanalo.actmdb.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serpanalo.actmdb.ui.common.Event
import com.serpanalo.actmdb.ui.common.Scope
import com.serpanalo.actmdb.ui.common.ScopedViewModel
import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video
import com.serpanalo.usecase.FindMovieById
import com.serpanalo.usecase.FindMovieVideosById
import com.serpanalo.usecase.GetMovieVideosById
import com.serpanalo.usecase.ToggleMovieFavorite
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    private val findMovieVideosById: FindMovieVideosById,
    private val getMovieVideosById: GetMovieVideosById,
    get: Any
) : ViewModel(),
    Scope by Scope.Impl() {

    init {
        initScope()
    }

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
        getMoviesFromServer(_model.value!!.movie.idm.toString())
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(toggleMovieFavorite.invoke(it))
        }
    }


    private val _navigation = MutableLiveData<Event<Video>>()

    val navigation: LiveData<Event<Video>> = _navigation


    class UiModelVideo(var videos: List<Video>)

    private val _modelVideo = MutableLiveData<UiModelVideo>()

    val modelVideo: LiveData<UiModelVideo>
        get() {
            return _modelVideo
        }

    private fun getMoviesFromServer(movieId: String) = launch {
        _modelVideo.value = UiModelVideo(getMovieVideosById.invoke(movieId))
        Log.d("XX", "------->: A server")
    }


    private fun findMovieVideos(movieId: String) = launch {
        _modelVideo.value = UiModelVideo(findMovieVideosById.invoke(movieId))
        Log.d("XX", "------->: A local")
    }

    fun onVideoClicked(video: Video) {
        Log.d("XX", "onVideoClicked: " + video.name)
        _navigation.value = Event(video)
    }

    override fun onCleared() {
        destroyScope()
    }


}