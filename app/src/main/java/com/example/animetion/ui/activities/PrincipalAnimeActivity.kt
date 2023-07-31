package com.example.animetion.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetion.R
import com.example.animetion.databinding.ActivityPrincipalAnimeBinding
import com.example.animetion.logic.jikanAnimeLogic.JikanAnimeLogic
import com.example.animetion.ui.adapter.AnimeAdapter
import kotlinx.coroutines.launch

class PrincipalAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalAnimeBinding

    private lateinit var topAnimeAdapter: AnimeAdapter
    private lateinit var temporadaAdapter: AnimeAdapter
    private lateinit var proximosAdapter: AnimeAdapter

    private lateinit var lManagerTop: LinearLayoutManager
    private lateinit var lManagerTemporada: LinearLayoutManager
    private lateinit var lManagerProximos: LinearLayoutManager

    private val animeLogic = JikanAnimeLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lManagerTop = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        lManagerTemporada = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        lManagerProximos = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        configSwipeTop()
        configSwipeRecomendados()
        configSwipeTemporada()

        lifecycleScope.launch {
            val topAnimeList = animeLogic.getAllAnimes()
            val randomList = animeLogic.getAnimesProximos()
            val temporadaList = animeLogic.getAnimesTemporada()

            // Crea el adaptador y configura el RecyclerView para los top animes
            topAnimeAdapter = AnimeAdapter(topAnimeList)
            binding.rvAnime.apply {
                adapter = topAnimeAdapter
                layoutManager = lManagerTop
            }


            temporadaAdapter = AnimeAdapter(temporadaList)
            binding.rvAnimesTemporada.apply {
                adapter = temporadaAdapter
                layoutManager = lManagerTemporada
            }

            // Crea el adaptador y configura el RecyclerView para los animes recomendados
            proximosAdapter = AnimeAdapter(randomList)
            binding.rvAnimesProximos.apply {
                adapter = proximosAdapter
                layoutManager = lManagerProximos
            }

        }
    }

    private fun configSwipeTop() {
        binding.rvSwipeAnime.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeAnime.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.color_gray
            )
        )

        binding.rvSwipeAnime.setOnRefreshListener {
            lifecycleScope.launch {
                val newAnimes = animeLogic.getAllAnimes()
                topAnimeAdapter.updateListItem(newAnimes)
                binding.rvSwipeAnime.isRefreshing = false
            }
        }
    }

    private fun configSwipeRecomendados() {
        binding.rvSwipeProximos.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeProximos.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.color_gray
            )
        )

        binding.rvSwipeProximos.setOnRefreshListener {
            lifecycleScope.launch {
                val newAnimes = animeLogic.getAnimesProximos()
                proximosAdapter.updateListItem(newAnimes)
                binding.rvSwipeProximos.isRefreshing = false
            }
        }
    }

    private fun configSwipeTemporada() {
        binding.rvSwipeTemporada.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeTemporada.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.color_gray
            )
        )

        binding.rvSwipeTemporada.setOnRefreshListener {
            lifecycleScope.launch {
                val newAnimes = animeLogic.getAnimesTemporada()
                temporadaAdapter.updateListItem(newAnimes)
                binding.rvSwipeTemporada.isRefreshing = false
            }
        }
    }
}