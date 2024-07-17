package com.example.animetion.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetion.R
import com.example.animetion.databinding.FragmentBuscarAnimeBinding
import com.example.animetion.logic.data.Anime
import com.example.animetion.logic.jikanAnimeLogic.JikanAnimeLogic
import com.example.animetion.ui.adapter.AnimeAdapter
import com.example.animetion.ui.utilities.AnimeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BuscarAnimeFragment : Fragment() {

    private var genre: String = ""
    private var startDate: String = ""
    private lateinit var binding: FragmentBuscarAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter

    private val jikanAnimeLogic: JikanAnimeLogic = JikanAnimeLogic()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView y el AnimeAdapter
        animeAdapter = AnimeAdapter(emptyList(), false, true,false)
        binding.rvAnimeBuscar.layoutManager = GridLayoutManager(requireContext(), 2) // Dos columnas
        binding.rvAnimeBuscar.adapter = animeAdapter

        // Configurar el evento de clic para el botón de búsqueda
        binding.btnSearch.setOnClickListener {
            // Llamar a performSearch dentro de un bloque coroutine
            viewLifecycleOwner.lifecycleScope.launch {
                performSearch()
            }
        }
        configSwipeBuscar()
    }

    private suspend fun performSearch() {
        val startYear = binding.editTextStartYear.text.toString()
        genre = binding.editTextGenre.text.toString()

        startDate = "$startYear-01-01"

        JikanAnimeLogic().resetCurrentPageSearch()

        // Realizar la búsqueda de animes utilizando la lógica de JikanAnimeLogic
        val searchResults = JikanAnimeLogic().searchAnimeByDate(startDate, genre)

        // Actualizar el adaptador con los resultados de la búsqueda
        animeAdapter.replaceListAdapter(searchResults)
    }

    private fun configSwipeBuscar() {
        binding.rvSwipeAnimeBuscar.setColorSchemeResources(R.color.color_morado)
        binding.rvSwipeAnimeBuscar.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.color_gray))

        binding.rvSwipeAnimeBuscar.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                // Llamar a la función suspendida searchAnimeByDate dentro de un bloque coroutine
                val newAnime = withContext(Dispatchers.IO) {
                    jikanAnimeLogic.searchAnimeByDate(startDate, genre)
                }
                animeAdapter.updateListItem(newAnime)
                binding.rvSwipeAnimeBuscar.isRefreshing = false
            }
        }
    }
}