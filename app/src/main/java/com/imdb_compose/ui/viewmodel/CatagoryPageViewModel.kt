package com.imdb_compose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdb_compose.domain.ActorDetail
import com.imdb_compose.domain.Async
import com.imdb_compose.domain.AsyncState
import com.imdb_compose.domain.Images
import com.imdb_compose.domain.MovieApi
import com.imdb_compose.domain.MovieDetail
import com.imdb_compose.domain.PeopleApi
import com.imdb_compose.domain.TvApi
import com.imdb_compose.domain.TvDetails
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CatagoryPageViewModel.DetailViewModelFactory::class)
class CatagoryPageViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    @Assisted val catagory: String,
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi
) : ViewModel() {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(id: Int, catagory: String): CatagoryPageViewModel
    }

    // Movie
    private val _movieDetails: MutableStateFlow<MovieDetail?> = MutableStateFlow(null)
    val movieDetails: StateFlow<MovieDetail?> = _movieDetails.asStateFlow()

    private val _movieImages: MutableStateFlow<Images?> = MutableStateFlow(null)
    val movieImages: StateFlow<Images?> = _movieImages.asStateFlow()

    // Television
    private val _tvDetails: MutableStateFlow<TvDetails?> = MutableStateFlow(null)
    val tvDetails: StateFlow<TvDetails?> = _tvDetails.asStateFlow()

    private val _tvImages: MutableStateFlow<Images?> = MutableStateFlow(null)
    val tvImages: StateFlow<Images?> = _tvImages.asStateFlow()

    // Person
    private val _personDetails: MutableStateFlow<AsyncState?> = MutableStateFlow(null)
    val personDetails: StateFlow<AsyncState?> = _personDetails.asStateFlow()

    init {
        viewModelScope.launch {
            when (catagory) {
                "Movies of the week" -> { getMovieDetails(id); getMovieImages(id) }
                "Upcoming movies" -> { getMovieDetails(id); getMovieImages(id) }
                "Trending movies" -> { getMovieDetails(id); getMovieImages(id) }
                "Tv airing today" -> { getTvSeriesDetails(id); getTvSeriesImages(id) }
                "Trending tv" -> { getTvSeriesDetails(id); getTvSeriesImages(id) }
                "Popular actors" -> getPersonDetails(id)
                "Trending people" -> getPersonDetails(id)
                else -> {}
            }
        }
    }
    // Movie
    private suspend fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val result = movieApi.getMovieDetails(id)
            _movieDetails.value = result
        }
    }

    private fun getMovieImages(id: Int) {
        viewModelScope.launch {
            val result = movieApi.getMovieImages(id)
            _movieImages.value = result
        }
    }

    // Television
    private suspend fun getTvSeriesDetails(id: Int) {
        viewModelScope.launch {
            val result = tvApi.getTvSeriesDetails(id)
            _tvDetails.value = result
        }
    }

    private suspend fun getTvSeriesImages(id: Int) {
        viewModelScope.launch {
            val result = tvApi.getTvSeriesImages(id)
            _tvImages.value = result
        }
    }

    // Person
    private suspend fun getPersonDetails(id: Int) {
        viewModelScope.launch {
            val loading = AsyncState(Async.Loading)
            try {
                 val result = peopleApi.getPersonDetails(id)
                _personDetails.value = AsyncState(Async.Success(result))
            } catch (e: Exception) {
                _personDetails.value = AsyncState(Async.Error(e))
            }
        }
    }
}

