package com.imdb_compose.ui.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdb_compose.domain.ActorDetail
import com.imdb_compose.domain.Async
import com.imdb_compose.domain.MovieApi
import com.imdb_compose.domain.MultiSearch
import com.imdb_compose.domain.PeopleApi
import com.imdb_compose.domain.SearchApi
import com.imdb_compose.domain.TvApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(
    val movieApi: MovieApi,
    val tvApi: TvApi,
    val peopleApi: PeopleApi,
    val searchApi: SearchApi
): ViewModel() {
    private val _textFieldSearchState: MutableStateFlow<TextFieldState> = MutableStateFlow(TextFieldState(""))
    var textFieldSearchState: StateFlow<TextFieldState> = _textFieldSearchState.asStateFlow()

    private val _searchResults: MutableStateFlow<Async<MultiSearch>> = MutableStateFlow(Async.Init)
    val searchResults: StateFlow<Async<MultiSearch>> = _searchResults.asStateFlow()

//    init {
//        getSearchResults("simpsons")
//    }

    fun getSearchResults(query: String) {
        viewModelScope.launch {
            _searchResults.value = Async.Loading
            try {
                val result = searchApi.queryMultiSearch(query)
                _searchResults.value = Async.Success(result)
            } catch (e: Exception) {
                _searchResults.value = Async.Error(e)
            }
        }
    }
}
