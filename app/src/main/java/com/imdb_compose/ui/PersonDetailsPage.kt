package com.imdb_compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose.ripeMango
import com.imdb_compose.BottomBar
import com.imdb_compose.ImageAsync
import com.imdb_compose.TopBar
import com.imdb_compose.isLoading
import com.imdb_compose.ui.viewmodel.CatagoryPageViewModel

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun PersonDetailsPage(
    id: Int,
    person: String,
    catagory: String,
    navController: NavController,
    clickHandlerBackButton: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopBar(person, true, clickHandlerBackButton)
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        val viewModel = hiltViewModel<CatagoryPageViewModel, CatagoryPageViewModel.DetailViewModelFactory> { factory ->
            factory.create(id, catagory)
        }
        val personDetails by viewModel.personDetails.collectAsState()

        if (personDetails != null) {
            personDetails!!.status.data.let { details -> details!!
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 8.dp)
                        .fillMaxSize()
                        .shadow(8.dp)
                ) {
                    // Name & Known For
                    Column(modifier = Modifier) {
                        Text(
                            text = details.name,
                            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = details.known_for_department,
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    // Profile Pic & Bio
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.width(170.dp)) {
                            ImageAsync(
                                backDropPath = details.profile_path,
                                contentDescription = "",
                                clip = true,
                                crop = false
                            )
                        }

                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = details.biography,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(text = "Born: ${ details.birthday }")
                        }
                    }
                    // Add to favorits
                    Row(modifier = Modifier) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(4.dp),
                            onClick = { /*TODO*/ },
                            colors = ButtonColors(
                                containerColor = ripeMango,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Red,
                                disabledContentColor = Color.White,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "",
                                tint = Color.Black
                            )
                            Text(text = "  Add to favorites", color = Color.Black)
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    isLoading()
                    Text("Loading...")
                }
            }
        }
    }
}
