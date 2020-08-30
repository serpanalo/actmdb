package com.serpanalo.actmdb.movies.ui

import com.serpanalo.actmdb.ui.dataModule
import com.serpanalo.data.repository.PermissionChecker
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.LocationDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video
import com.serpanalo.testshared.mockedMovie
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "XXXX" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<ServerDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var movies: List<Movie> = emptyList()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun getPopularMovies(): List<Movie> = movies


    override suspend fun findById(id: Int): Movie = movies.first { it.idm == id }

    override suspend fun update(movie: Movie) {
        movies = movies.filterNot { it.idm == movie.idm } + movie
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        TODO("Not yet implemented")
    }
    override suspend fun saveVideos(videos: List<Video>, id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun findVideosOfMovieById(id: String): List<Video> {
        TODO("Not yet implemented")
    }


}

class FakeRemoteDataSource : ServerDataSource {

    var movies = defaultFakeMovies

    override suspend fun getPopularMovies(apiKey: String, region: String) = movies


    override suspend fun getMovieVideos(
        apiKey: String,
        region: String,
        movieId: String
    ): List<Video> {
        TODO("Not yet implemented")
    }


}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}