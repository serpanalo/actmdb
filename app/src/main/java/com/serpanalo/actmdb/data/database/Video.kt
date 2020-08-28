package com.serpanalo.actmdb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video (
    @PrimaryKey val id: String,
    val idm : String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    var urlPlayback: String?,
    var urlThumbnail: String?
) {

}