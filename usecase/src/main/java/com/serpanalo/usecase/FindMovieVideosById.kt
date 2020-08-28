package com.serpanalo.usecase

import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.domain.Video

class FindMovieVideosById(private val moviesRepository: MovieRepository){
    suspend operator fun invoke(id:String):List<Video> = moviesRepository.findVideosOfMovieById(id)
}
