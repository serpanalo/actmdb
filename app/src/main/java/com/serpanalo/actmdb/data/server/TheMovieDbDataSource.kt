package com.serpanalo.actmdb.data.server

import com.serpanalo.actmdb.data.toDomainMovie
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.domain.Movie

class TheMovieDbDataSource : ServerDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}