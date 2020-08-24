package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Movie

class GetFavoritesMovies(private val moviesRepository: MovieRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getFavoriteMovies()
}
