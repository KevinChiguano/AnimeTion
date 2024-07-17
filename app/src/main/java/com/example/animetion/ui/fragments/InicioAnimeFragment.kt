package com.example.animetion.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetion.R
import com.example.animetion.databinding.FragmentInicioAnimeBinding
import com.example.animetion.ui.adapter.AnimeAdapter
import com.example.animetion.ui.utilities.AnimeViewModel

class InicioAnimeFragment : Fragment() {

    private lateinit var binding: FragmentInicioAnimeBinding
    private lateinit var viewModel: AnimeViewModel

    private lateinit var topAnimeAdapter: AnimeAdapter
    private lateinit var temporadaAdapter: AnimeAdapter
    private lateinit var proximosAdapter: AnimeAdapter

    private lateinit var lManagerTop: LinearLayoutManager
    private lateinit var lManagerTemporada: LinearLayoutManager
    private lateinit var lManagerProximos: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AnimeViewModel::class.java)

        lManagerTop = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        lManagerTemporada = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        lManagerProximos = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        configSwipeTop()
        configSwipeRecomendados()
        configSwipeTemporada()

        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        viewModel.topAnimeList.observe(viewLifecycleOwner) { topAnimeList ->
            topAnimeAdapter = AnimeAdapter(topAnimeList, true,true)
            binding.rvAnime.apply {
                adapter = topAnimeAdapter
                layoutManager = lManagerTop
            }
        }

        viewModel.temporadaList.observe(viewLifecycleOwner) { temporadaList ->
            temporadaAdapter = AnimeAdapter(temporadaList, true,true)
            binding.rvAnimesTemporada.apply {
                adapter = temporadaAdapter
                layoutManager = lManagerTemporada
            }
        }

        viewModel.randomList.observe(viewLifecycleOwner) { randomList ->
            proximosAdapter = AnimeAdapter(randomList, true,true)
            binding.rvAnimesProximos.apply {
                adapter = proximosAdapter
                layoutManager = lManagerProximos
            }
        }
    }

    private fun configSwipeTop() {
        binding.rvSwipeAnime.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeAnime.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.color_gray))

        binding.rvSwipeAnime.setOnRefreshListener {
            viewModel.refreshTopAnimeList()
            binding.rvSwipeAnime.isRefreshing = false
        }
    }

    private fun configSwipeRecomendados() {
        binding.rvSwipeProximos.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeProximos.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.color_gray))

        binding.rvSwipeProximos.setOnRefreshListener {
            viewModel.refreshRandomList()
            binding.rvSwipeProximos.isRefreshing = false
        }
    }

    private fun configSwipeTemporada() {
        binding.rvSwipeTemporada.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeTemporada.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.color_gray))

        binding.rvSwipeTemporada.setOnRefreshListener {
            viewModel.refreshTemporadaList()
            binding.rvSwipeTemporada.isRefreshing = false
        }
    }
}