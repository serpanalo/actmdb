package com.serpanalo.actmdb.ui.main

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
import com.serpanalo.actmdb.ui.navigation.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class HomeFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter

    // private val coarsePermissionRequester = PermissionRequester(requireActivity().parent, Manifest.permission.ACCESS_COARSE_LOCATION)

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: HomeViewModel.UiModel) {

        val coarsePermissionRequester = PermissionRequester(
            MainActivity.getContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        progress.visibility == if (model == HomeViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {

            is HomeViewModel.UiModel.Content -> adapter.movies = model.movies


            HomeViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}