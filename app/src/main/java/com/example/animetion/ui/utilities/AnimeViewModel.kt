package com.example.animetion.ui.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetion.logic.data.Anime
import com.example.animetion.logic.jikanAnimeLogic.JikanAnimeLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    private val animeLogic = JikanAnimeLogic()

    private val _topAnimeList = MutableLiveData<List<Anime>>()
    val topAnimeList: LiveData<List<Anime>>
        get() = _topAnimeList

    private val _temporadaList = MutableLiveData<List<Anime>>()
    val temporadaList: LiveData<List<Anime>>
        get() = _temporadaList

    private val _randomList = MutableLiveData<List<Anime>>()
    val randomList: LiveData<List<Anime>>
        get() = _randomList

    private val _searchResults = MutableLiveData<List<Anime>>(emptyList())
    val searchResults: LiveData<List<Anime>> get() = _searchResults

    init {
        _searchResults.value = emptyList()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _topAnimeList.postValue(animeLogic.getAllAnimes())
            _randomList.postValue(animeLogic.getAnimesProximos())
            _temporadaList.postValue(animeLogic.getAnimesTemporada())
        }
    }

    fun refreshTopAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            _topAnimeList.postValue(animeLogic.getAllAnimes())
        }
    }

    fun refreshTemporadaList() {
        viewModelScope.launch(Dispatchers.IO) {
            _temporadaList.postValue(animeLogic.getAnimesTemporada())
        }
    }

    fun refreshRandomList() {
        viewModelScope.launch(Dispatchers.IO) {
            _randomList.postValue(animeLogic.getAnimesProximos())
        }
    }

    fun searchAnimeByYearAndGenre(startYear: String, genre: String) {
        viewModelScope.launch {
            val results = animeLogic.searchAnimeByDate(startYear, genre)
            _searchResults.value = results
        }
    }
}