package com.example.animetion.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Anime(
    val id: Int,
    val name: String,
    val synopsis: String?,
    val trailerUrl: String?,
    val imageUrl: String,
    val type: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<String>?,
    val studios: List<String>?,
    val source: String?,
    val members: Int?,
    val favorites: Int?,
    val ranked: Int?
) : Parcelable
