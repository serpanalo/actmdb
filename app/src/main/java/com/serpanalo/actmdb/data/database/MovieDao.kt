package com.serpanalo.actmdb.data.database

import androidx.room.*

@Dao
interface MovieDao{

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE idm = :id")
    fun findById(id: Int): Movie

    @Query("SELECT COUNT(idm) FROM Movie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE favorite = 1")
    fun getFavorites(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieVideos(movies: List<Video>)

    @Query("SELECT * FROM Video WHERE idm LIKE :id")
    fun findVideosOfMovieById(id: String): List<Video>

}