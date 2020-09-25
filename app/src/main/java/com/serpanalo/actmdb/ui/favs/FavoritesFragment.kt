package com.serpanalo.actmdb.ui.favs

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.ui.common.PermissionRequester
import com.serpanalo.actmdb.ui.detail.DetailActivity
import com.serpanalo.actmdb.ui.navigation.MainActivity
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class FavoritesFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter

    private val viewModel: FavoritesViewModel by lifecycleScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MoviesAdapter(viewModel::onMovieClicked)

        recycler_favs.adapter = adapter
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))

        viewModel.navigation.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle()
                bundle.putInt(DetailActivity.MOVIE, it.idm)
                findNavController().navigate(R.id.action_navigation_favs_to_detailActivity, bundle)
            }
        })

    }

    private fun updateUi(model: FavoritesViewModel.UiModel) {

        val coarsePermissionRequester = PermissionRequester(
            MainActivity.getContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        progress.visibility == if (model == FavoritesViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {

            is FavoritesViewModel.UiModel.Content -> {

                if (model.movies.isEmpty()){
                    tvEmpty.visibility=View.VISIBLE
                    recycler_favs.visibility=View.INVISIBLE
                }else{
                    adapter.movies = model.movies
                    tvEmpty.visibility=View.INVISIBLE
                    recycler_favs.visibility=View.VISIBLE
                }
            }


            FavoritesViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}