package com.serpanalo.data.repository

import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.domain.Movie

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: ServerDataSource,
    private val regionRepository: RegionRepository,
    private val apikey: String){


    suspend fun getPopularMovies():List<Movie>{

        if(localDataSource.isEmpty()){
            val movies = remoteDataSource.getPopularMovies(apikey,regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun getFavoriteMovies():List<Movie>{

        if(localDataSource.isEmpty()){
            val movies = remoteDataSource.getPopularMovies(apikey,regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getFavoriteMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)

}