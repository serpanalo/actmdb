package com.serpanalo.actmdb.data.database

import android.util.Log
import com.serpanalo.actmdb.data.toDomainMovie
import com.serpanalo.actmdb.data.toDomainVideo
import com.serpanalo.actmdb.data.toRoomMovie
import com.serpanalo.actmdb.data.toRoomVideo
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { movieDao.movieCount() <= 0 }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) { movieDao.insertMovies(movies.map { it.toRoomMovie() }) }
    }

    override suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun getFavoriteMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getFavorites().map { it.toDomainMovie() }
    }

    override suspend fun findById(id: Int): Movie = withContext(Dispatchers.IO) {
        movieDao.findById(id).toDomainMovie()
    }

    override suspend fun update(movie: Movie) {
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) }
    }

    /// videos
    override suspend fun saveVideos(videos: List<Video>, id: String) {
        Log.d("XX", "saveVideos: " + videos.size)
        withContext(Dispatchers.IO) { movieDao.insertMovieVideos(videos.map { it.toRoomVideo(id) }) }
    }

    override suspend fun findVideosOfMovieById(id: String): List<Video> =
        withContext(Dispatchers.IO) {
            movieDao.findVideosOfMovieById(id).map { it.toDomainVideo() }
        }


}