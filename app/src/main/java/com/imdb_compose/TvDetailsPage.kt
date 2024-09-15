package com.imdb_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TvDetailsPage(
    show: String,
    viewModel: HomeScreenViewModel,
    navController: NavController,
    clickHandlerBackBtn: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarWithBackBtn(title = show, clickHandlerBackBtn)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text("Television Details")
                Text(show)
            }
        }
    }
}
