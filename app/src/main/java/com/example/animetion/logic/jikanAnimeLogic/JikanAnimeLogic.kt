package com.example.animetion.logic.jikanAnimeLogic

import android.util.Log
import com.example.animetion.data.connection.ApiConnection
import com.example.animetion.data.endpoints.JikanEndpoint
import com.example.animetion.data.entities.jikanAnime.JikanAnimeEntity
import com.example.animetion.logic.data.Anime
import retrofit2.Response

class JikanAnimeLogic {

    private var currentPage = 1
    private val itemsPerPage = 25
    private val allAnimes = mutableListOf<Anime>()

    private var currentPageTemporada = 1 // Agregar una variable para la página actual de animes de temporada

    private var currentPageRandom = 1

    private var currentPageSearch = 1

    suspend fun getAllAnimes(): List<Anime> {
        val response = ApiConnection.getService(
            ApiConnection.typeApi.JikanAnime,
            JikanEndpoint::class.java
        ).getAllAnimes(currentPage, itemsPerPage)

        return processResponse(response)
    }

    suspend fun getAnimesTemporada(): List<Anime> {
        val response = ApiConnection.getService(
            ApiConnection.typeApi.JikanAnime,
            JikanEndpoint::class.java
        ).getAnimesTemporada(currentPageTemporada, itemsPerPage) // Usar la página actual para la consulta de animes de temporada

        return processResponse(response)
    }

    suspend fun getAnimesProximos(): List<Anime> {
        val response = ApiConnection.getService(
            ApiConnection.typeApi.JikanAnime,
            JikanEndpoint::class.java
        ).getAnimesProximos(currentPageRandom, itemsPerPage) // Usar la página actual para la consulta de animes de temporada

        return processResponse(response)
    }

    suspend fun searchAnimeByDate(startDate: String, genre: String): List<Anime> {
        try {
            val response = ApiConnection.getService(
                ApiConnection.typeApi.JikanAnime,
                JikanEndpoint::class.java
            ).searchAnimeByYearAndGenre(startDate, genre, currentPageSearch, itemsPerPage)

            Log.d("JikanAnimeLogic", "Search URL: ${response.raw().request().url()}")

            return processResponse(response)
        } catch (e: Exception) {
            Log.e("JikanAnimeLogic", "Error: ${e.message}", e)
            return emptyList()
        }
    }

    fun resetCurrentPageSearch() {
        currentPageSearch = 1
    }


    private fun processResponse(response: Response<JikanAnimeEntity>): List<Anime> {
        if (response.isSuccessful) {
            val newAnimes = response.body()?.data?.map {
                Anime(
                    it.mal_id,
                    it.title,
                    it.synopsis,
                    it.trailer?.embed_url ?: "",
                    it.images.jpg.image_url,
                    it.type,
                    it.episodes,
                    it.score,
                    it.genres?.map { genre -> genre.name },
                    it.studios?.map { studio -> studio.name },
                    it.source,
                    it.members,
                    it.favorites,
                    it.rank
                )
            } ?: emptyList()

            // Agregar una condición para determinar si la respuesta es de animes de temporada
            if (response.raw().request().url().encodedPathSegments().contains("seasons")) {
                currentPageTemporada++
            } else if(response.raw().request().url().encodedPathSegments().contains("now")){
                allAnimes.addAll(newAnimes)
                currentPage++
            }else if(response.raw().request().url().encodedPathSegments().contains("upcoming")){
                allAnimes.addAll(newAnimes)
                currentPageRandom++
            }else{
                allAnimes.addAll(newAnimes)
                Log.d("JikanAnimeLogic", "Search URL: ${currentPageSearch}")
                currentPageSearch++
            }

            return newAnimes
        }

        return emptyList()
    }
}

