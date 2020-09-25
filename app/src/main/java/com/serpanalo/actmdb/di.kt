package com.serpanalo.actmdb

import android.app.Application
import com.serpanalo.actmdb.data.AndroidPermissionChecker
import com.serpanalo.actmdb.data.PlayServicesLocationDataSource
import com.serpanalo.actmdb.data.database.MovieDatabase
import com.serpanalo.actmdb.data.database.RoomDataSource
import com.serpanalo.actmdb.data.server.TheMovieDb
import com.serpanalo.actmdb.data.server.TheMovieDbDataSource
import com.serpanalo.actmdb.ui.detail.DetailActivity
import com.serpanalo.actmdb.ui.detail.DetailViewModel
import com.serpanalo.actmdb.ui.favs.FavoritesFragment
import com.serpanalo.actmdb.ui.favs.FavoritesViewModel
import com.serpanalo.actmdb.ui.main.HomeFragment
import com.serpanalo.actmdb.ui.main.HomeViewModel
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.data.repository.PermissionChecker
import com.serpanalo.data.repository.RegionRepository
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.LocationDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.usecase.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<ServerDataSource> { TheMovieDbDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { TheMovieDb(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MovieRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<FavoritesFragment>()) {
        viewModel { FavoritesViewModel(get()) }
        scoped { (GetFavoritesMovies(get())) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(),get(),get(),get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
        scoped { FindMovieVideosById(get()) }
        scoped { GetMovieVideosById(get()) }
    }

}