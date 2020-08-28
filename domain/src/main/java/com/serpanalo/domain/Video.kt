package com.serpanalo.domain

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    var urlPlayback: String?,
    var urlThumbnail: String?
)
