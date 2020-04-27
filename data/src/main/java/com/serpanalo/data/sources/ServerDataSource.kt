package com.serpanalo.data.sources

import com.serpanalo.domain.Movie

interface ServerDataSource{
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}