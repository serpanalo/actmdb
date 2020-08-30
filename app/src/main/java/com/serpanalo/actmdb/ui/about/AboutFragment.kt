package com.serpanalo.actmdb.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.serpanalo.actmdb.databinding.FragmentAboutBinding
import com.serpanalo.domain.User

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAboutBinding.inflate(layoutInflater)

        val user: User = User(
            "0",
            "Sergio Pantoja Alonso",
            "serpanalo@gmail.com",
            "Amorebieta",
            "Ha sido un verdadero placer participar en este curso.\n" +
                    "Ahora solo queda seguir aprendiendo,\n\n"+
                    "muchas gracias por todo a tod@s  "
        )
        binding.user = user

        return binding.root
    }
}