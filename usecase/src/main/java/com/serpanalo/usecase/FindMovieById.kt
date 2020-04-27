package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Movie

class FindMovieById(private val moviesRepository: MovieRepository){
    suspend fun invoke(id:Int):Movie =moviesRepository.findById(id)
}
