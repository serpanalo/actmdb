package com.serpanalo.actmdb.ui.favs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.serpanalo.actmdb.R

class FavsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val root: View = inflater.inflate(R.layout.fragment_favorites, container, false)
        return root
    }
}