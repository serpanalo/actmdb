package com.serpanalo.actmdb.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TheMovieDbVideoResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: ArrayList<Video>
)

@Parcelize
data class Video(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,

    var urlPlayback: String?,
    var urlThumbnail: String?

) : Parcelable