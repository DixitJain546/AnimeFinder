package com.example.animefinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animefinder.model.AnimeListState
import com.example.animefinder.respository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
): ViewModel() {

    private val _animeListState = MutableStateFlow<AnimeListState>(AnimeListState.Loading(false))
    val animeListState: StateFlow<AnimeListState> = _animeListState

    fun callAnimeListApi() {
        viewModelScope.launch {
            _animeListState.value = AnimeListState.Loading(true)
            try {
                animeRepository.getTopAnime().also { response ->
                    _animeListState.value = if(response.isSuccessful) {
                        AnimeListState.Success(response.body())
                    } else {
                        AnimeListState.Error("Error ${response.code()}: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _animeListState.value = AnimeListState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }
}