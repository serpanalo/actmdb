package com.serpanalo.actmdb.data.server

import com.serpanalo.actmdb.data.toDomainMovie
import com.serpanalo.actmdb.data.toDomainVideo
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video

class TheMovieDbDataSource : ServerDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }

    override suspend fun getMovieVideos(
        apiKey: String,
        region: String,
        movieId: String
    ): List<Video> =
        TheMovieDb.service
            .listMovieVideos(movieId, apiKey, region)
            .results
            .map { it.toDomainVideo() }


}