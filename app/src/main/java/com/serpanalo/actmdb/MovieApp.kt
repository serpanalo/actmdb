package com.serpanalo.actmdb

import android.app.Application
import com.serpanalo.actmdb.ui.initDI

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

}