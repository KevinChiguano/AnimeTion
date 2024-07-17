package com.example.animetion.data.endpoints

import com.example.animetion.data.entities.jikanAnime.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanEndpoint {

    @GET("top/anime")
    suspend fun getAllAnimes(@Query("page") page: Int, @Query("limit") limit: Int
    ): Response<JikanAnimeEntity>


    @GET("seasons/now")
    suspend fun getAnimesTemporada(@Query("page") page: Int, @Query("limit") limit: Int
    ): Response<JikanAnimeEntity>

    @GET("seasons/upcoming")
    suspend fun getAnimesProximos(@Query("page") page: Int, @Query("limit") limit: Int
    ): Response<JikanAnimeEntity>

    @GET("anime")
    suspend fun searchAnimeByYearAndGenre(
        @Query("start_date") startYear: String,
        @Query("genres") genres: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<JikanAnimeEntity>

}