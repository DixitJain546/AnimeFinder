package com.example.animefinder.respository

import com.example.animefinder.network.IAnimeApiService
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: IAnimeApiService
) {

    suspend fun getTopAnime() = apiService.getTopAnimeList()

}