package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MovieRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}