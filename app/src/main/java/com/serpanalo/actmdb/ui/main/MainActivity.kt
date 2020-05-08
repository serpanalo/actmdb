package com.serpanalo.actmdb.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.ui.common.PermissionRequester
import com.serpanalo.actmdb.ui.common.startActivity
import com.serpanalo.actmdb.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MoviesAdapter

    private val coarsePermissionRequester = PermissionRequester(this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.movies = model.movies
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}