package com.serpanalo.actmdb.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.ui.common.loadUrl
import com.serpanalo.actmdb.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity(){

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val viewModel: DetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(intent.getIntExtra(MOVIE, -1))
    }

    private lateinit var adapter: VideosAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        rvVideo.layoutManager = LinearLayoutManager(this)
        rvVideo.isNestedScrollingEnabled = true
        rvVideo.hasFixedSize()

        adapter = VideosAdapter(viewModel::onVideoClicked)
        rvVideo.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
        movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
        viewModel.modelVideo.observe(this, Observer(::updateUiVideo))

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle()
                bundle.putString(PlayerActivity.VIDEO_URL, it.key)
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtras(bundle)
                this.startActivity(intent)
            }
        })


        movieDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }

        })


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {

        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(this)

        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(getDrawable(icon))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun updateUiVideo(model: DetailViewModel.UiModelVideo) {
        Log.d("XX", "updateUiVideo: " + model.videos.size)
        adapter.videos = model.videos
    }



}
