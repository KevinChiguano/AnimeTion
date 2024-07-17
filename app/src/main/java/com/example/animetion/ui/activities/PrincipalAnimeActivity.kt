package com.example.animetion.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.animetion.R
import com.example.animetion.databinding.ActivityPrincipalAnimeBinding
import com.example.animetion.ui.fragments.BuscarAnimeFragment
import com.example.animetion.ui.fragments.InicioAnimeFragment
import com.example.animetion.ui.utilities.FragmentsManager

class PrincipalAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalAnimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()



        binding.bottomNavigationView.setOnItemSelectedListener(){item ->
            when(item.itemId) {
                R.id.menu_item_inicio -> {
                    //instanciar fragment

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        InicioAnimeFragment()
                    )


                    true
                }
                R.id.menu_item_buscar -> {
                    // Respond to navigation item 2 click

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        BuscarAnimeFragment()
                    )

                    true
                }
                R.id.menu_item_favoritos -> {
                    // Respond to navigation item 2 click

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        InicioAnimeFragment()
                    )

                    true
                }
                else -> false
            }
        }
    }
}