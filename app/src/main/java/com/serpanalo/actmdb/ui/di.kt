package com.serpanalo.actmdb.ui

import android.app.Application
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.data.AndroidPermissionChecker
import com.serpanalo.actmdb.data.PlayServicesLocationDataSource
import com.serpanalo.actmdb.data.database.MovieDatabase
import com.serpanalo.actmdb.data.database.RoomDataSource
import com.serpanalo.actmdb.data.server.TheMovieDbDataSource
import com.serpanalo.actmdb.ui.detail.DetailActivity
import com.serpanalo.actmdb.ui.detail.DetailViewModel
import com.serpanalo.actmdb.ui.main.MainActivity
import com.serpanalo.actmdb.ui.main.MainViewModel
import com.serpanalo.data.repository.MovieRepository
import com.serpanalo.data.repository.PermissionChecker
import com.serpanalo.data.repository.RegionRepository
import com.serpanalo.data.sources.LocalDataSource
import com.serpanalo.data.sources.LocationDataSource
import com.serpanalo.data.sources.ServerDataSource
import com.serpanalo.usecase.FindMovieById
import com.serpanalo.usecase.GetPopularMovies
import com.serpanalo.usecase.ToggleMovieFavorite
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
    factory<ServerDataSource> { TheMovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MovieRepository(get(), get(), get(), get(named("apiKey"))) }
}


private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }

}