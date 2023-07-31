package com.example.animetion.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animetion.databinding.ActivityLoginBinding
import com.example.animetion.ui.utilities.PreferenceHelper
import com.example.animetion.ui.utilities.PreferenceHelper.get
import com.example.animetion.ui.utilities.PreferenceHelper.set


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["session", false])
            goToMenu()

        binding.tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnIniciarSecion.setOnClickListener {
            goToMenu()
        }

    }

    private fun goToMenu() {
        createSessionPreference()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    private fun createSessionPreference() {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
    }
}