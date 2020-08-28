package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Video

class GetMovieVideosById(private val moviesRepository: MovieRepository) {
    suspend fun invoke(id:String): List<Video> = moviesRepository.getVideosOfMovies(id)
}
