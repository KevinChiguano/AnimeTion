package com.example.animetion.data.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    enum class typeApi {
        JikanAnime
    }


    private val API_JIKAN_ANIME = "https://api.jikan.moe/v4/"

    private fun getConnection(base: String): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    suspend fun <T, E:Enum<E>> getService(api: E,service : Class<T>): T {
        var BASE = ""
        when(api.name){
            typeApi.JikanAnime.name ->{
                BASE = API_JIKAN_ANIME
            }
        }
        return getConnection(BASE).create(service)
    }

}