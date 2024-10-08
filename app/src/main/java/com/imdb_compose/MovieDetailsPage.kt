package com.imdb_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun MovieDetailsPage(
    movie: String,
    viewModel: HomeScreenViewModel,
    navController: NavController,
    clickHandlerBackBtn: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarWithBackBtn(movie, clickHandlerBackBtn)
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val movies = viewModel.movieListOfWeek.collectAsState()
            Column {
                Text(text = "Movie Details")
                Text(text = movie)
            }
        }
    }
}
