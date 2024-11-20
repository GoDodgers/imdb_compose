package com.imdb_compose.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.imdb_compose.domain.MovieApi
import com.imdb_compose.domain.PeopleApi
import com.imdb_compose.domain.TvApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryPageViewModel @Inject constructor(
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi
) : ViewModel() {

    private val _textFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val textFieldState: StateFlow<String> = _textFieldState.asStateFlow()

}
