package com.serpanalo.actmdb.ui.player


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.serpanalo.actmdb.R
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    companion object {
        const val VIDEO_URL = "videoUrl"
    }

    lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        videoUrl = intent.getStringExtra(VIDEO_URL).toString()
        youtubePlayer.initialize(getString(R.string.key_youtube), this)

    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        restored: Boolean
    ) {

        if (!restored) {
            youtubePlayer?.cueVideo(videoUrl)
        }


    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {


        if (youTubeInitializationResult!!.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(this, 1).show()
        } else {
            Toast.makeText(this, "Error ", Toast.LENGTH_LONG).show()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==1){
           getYoutubePlayerProvider().initialize(getString(R.string.key_youtube), this)
        }

    }


    private fun getYoutubePlayerProvider() : YouTubePlayer.Provider{
        return youtubePlayer
    }


}
