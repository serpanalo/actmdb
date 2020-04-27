package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Movie

class GetPopularMovies(private val moviesRepository: MovieRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}
