package com.example.animefinder.network

import com.example.animefinder.model.AnimeListResponse
import retrofit2.Response
import retrofit2.http.GET

interface IAnimeApiService {

    @GET("v4/top/anime")
    suspend fun getTopAnimeList(): Response<AnimeListResponse>
}