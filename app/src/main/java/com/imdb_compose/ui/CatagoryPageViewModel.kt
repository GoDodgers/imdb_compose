package com.imdb_compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdb_compose.domain.Images
import com.imdb_compose.domain.MovieApi
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
import javax.inject.Inject

@HiltViewModel(assistedFactory = CatagoryPageViewModel.DetailViewModelFactory::class)
class CatagoryPageViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi
) : ViewModel() {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(id: Int): CatagoryPageViewModel
    }

    // Television
    private val _tvDetails: MutableStateFlow<TvDetails?> = MutableStateFlow(null)
    val tvDetails: StateFlow<TvDetails?> = _tvDetails.asStateFlow()

    private val _tvImages: MutableStateFlow<Images?> = MutableStateFlow(null)
    val tvImages: StateFlow<Images?> = _tvImages.asStateFlow()


    init {
        viewModelScope.launch {
            getTvSeriesDetails(id)
            getTvSeriesImages(id)
        }
    }

    suspend fun getTvSeriesDetails(id: Int) {
        viewModelScope.launch {
            val result = tvApi.getTvSeriesDetails(id)
            _tvDetails.value = result
        }
    }

    suspend fun getTvSeriesImages(id: Int) {
        viewModelScope.launch {
            val result = tvApi.getTvSeriesImages(id)
            _tvImages.value = result
        }
    }

}
