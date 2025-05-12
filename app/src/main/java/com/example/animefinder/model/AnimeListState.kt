package com.example.animefinder.model

sealed class AnimeListState {
    data class Loading(val isLoading: Boolean) : AnimeListState()
    data class Success(val animeData: AnimeListResponse?) : AnimeListState()
    data class Error(val error: String) : AnimeListState()
}