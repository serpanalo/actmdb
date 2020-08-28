package com.serpanalo.data.sources

import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video

interface ServerDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
    suspend fun getMovieVideos(apiKey: String, region: String, movieId: String): List<Video>
}