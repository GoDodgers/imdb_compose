package com.imdb_compose.ui.viewmodel

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.imdb_compose.domain.MovieApi
import com.imdb_compose.domain.PeopleApi
import com.imdb_compose.domain.TvApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(
    val movieApi: MovieApi,
    val tvApi: TvApi,
    val peopleApi: PeopleApi
): ViewModel() {
    private val _textFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val textFieldState: StateFlow<String> = _textFieldState.asStateFlow()

//    val viewModel: CategoryPageViewModel = hiltViewModel()
//    BasicTextField(state = viewModel.textFieldState.collectAsState())
}
