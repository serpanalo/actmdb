package com.serpanalo.actmdb.ui.detail

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.serpanalo.actmdb.R
import com.serpanalo.actmdb.ui.common.inflate
import com.serpanalo.domain.Video
import kotlinx.android.synthetic.main.item_videos.view.*
import kotlin.properties.Delegates


class VideosAdapter(private val listener: (Video) -> Unit) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    var videos: List<Video> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_videos, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
        holder.itemView.setOnClickListener { listener(video) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(video: Video) {

            itemView.tvName.text = video.name
            itemView.tvViewType.text = video.type

            if (video.key.isNotEmpty()) {

                Glide.with(itemView.context)
                    .load(video.urlThumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.ivThumbnail)
            }


        }
    }
}