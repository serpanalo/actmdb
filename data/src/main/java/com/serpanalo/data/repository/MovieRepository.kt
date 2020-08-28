package com.serpanalo.data.repository

import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: ServerDataSource,
    private val regionRepository: RegionRepository,
    private val apikey: String
) {


    suspend fun getPopularMovies(): List<Movie> {

       // if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apikey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
       // }

        return localDataSource.getPopularMovies()
    }

    suspend fun getFavoriteMovies(): List<Movie> {
        return localDataSource.getFavoriteMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)

    suspend fun getVideosOfMovies(movieId: String): List<Video> {
        val videos = remoteDataSource.getMovieVideos(apikey, regionRepository.findLastRegion(), movieId)
            localDataSource.saveVideos(videos, movieId)
        return localDataSource.findVideosOfMovieById(movieId)
    }

    suspend fun findVideosOfMovieById(id: String): List<Video> =
        localDataSource.findVideosOfMovieById(id)

}