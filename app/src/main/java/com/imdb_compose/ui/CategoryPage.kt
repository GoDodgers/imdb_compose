package com.imdb_compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdb_compose.ui.viewmodel.CategoryPageViewModel

@Composable
fun CategoryPage(
    category: String,
    navController: NavController,
    clickHandlerBackButton: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val viewModel: CategoryPageViewModel = hiltViewModel()
//            BasicTextField(state = viewModel.textFieldState.collectAsState())
    }
}
