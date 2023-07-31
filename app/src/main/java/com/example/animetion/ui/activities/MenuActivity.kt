package com.example.animetion.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animetion.databinding.ActivityMenuBinding
import com.example.animetion.ui.utilities.PreferenceHelper

import com.example.animetion.ui.utilities.PreferenceHelper.set


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCerrarSecion.setOnClickListener{
            clearSessionPreference()
            goToLogin()
        }

        binding.btnAnime.setOnClickListener{
            goToAnime()
        }



    }

    private fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goToAnime(){
        startActivity(Intent(this, PrincipalAnimeActivity::class.java))
    }

    private fun clearSessionPreference(){
        val preference = PreferenceHelper.defaultPrefs(this)
        preference["session"] = false
    }
}