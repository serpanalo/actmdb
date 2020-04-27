package com.serpanalo.actmdb.data.database

import androidx.room.Database

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase() {
    abstract fun movieDao(): MovieDao
}