package com.serpanalo.actmdb.data.server

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): TheMovieDbResult

    @GET("movie/upcoming")
    suspend fun listUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): TheMovieDbResult

    @GET("movie/{id}/videos")
    suspend fun listMovieVideos(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): TheMovieDbVideoResult

}