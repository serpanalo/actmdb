package com.serpanalo.actmdb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val id: String,
    val name: String,
    val surmana1: String,
    val surname2: String,
    val city: String,
    val message: String
) {

}