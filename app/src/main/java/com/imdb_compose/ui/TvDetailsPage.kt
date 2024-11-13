package com.imdb_compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.ripeMango
import com.imdb_compose.BottomBar
import com.imdb_compose.Pager
import com.imdb_compose.Tags
import com.imdb_compose.TopBar
import com.imdb_compose.domain.Resources
import com.imdb_compose.isLoading

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun TvDetailsPage(
    id: Int,
    show: String,
    catagory: String,
    navController: NavController,
    clickHandlerBackButton: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopBar(show, true, clickHandlerBackButton)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        val viewModel = hiltViewModel<CatagoryPageViewModel, CatagoryPageViewModel.DetailViewModelFactory> { factory ->
            factory.create(id, catagory)
        }

        if (viewModel.tvDetails.value?.id == id) {
            val seriesDetails = viewModel.tvDetails.collectAsState()
            val seriesImages = viewModel.tvImages.collectAsState()

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                seriesDetails.value?.let { details ->
                    LazyColumn {
                        item {
                            // Title
                            Text(
                                modifier = Modifier.padding(start = 6.dp),
                                text = details.name,
                                lineHeight = 50.sp,
                                fontStyle = MaterialTheme.typography.displayMedium.fontStyle
                            )
                            // Year Rating Duration
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .padding(start = 16.dp, bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${ details.first_air_date.substring(0, 4) } - ${ details.last_air_date.substring(0, 4) }")
                                Text("${ details.original_language.uppercase() }")
                            }

                            Pager(images = seriesImages)
                        }
                        // Image & description
                        item {
                            Row (modifier = Modifier.padding(start = 8.dp)) {
                                AsyncImage(
                                    modifier = Modifier.width(170.dp),
                                    model = "${ Resources.BASE_IMAGE_URL }${ Resources.IMAGE_PATH }${ details.poster_path }",
                                    contentDescription = ""
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    text = details.overview,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 10
                                )
                            }
                        }
                        // tags
                        item {
                            LazyRow {
                                details.genres.forEachIndexed { i, genre ->
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .padding(vertical = 8.dp)
                                        ) {
                                            Tags(txt = genre.name)
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                    }
                                }
                            }
                        }
                        // Add to watchlist
                        item {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .padding(horizontal = 8.dp),
                                shape = RoundedCornerShape(4.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonColors(
                                    containerColor = ripeMango,
                                    contentColor = Color.White,
                                    disabledContainerColor = Color.Red,
                                    disabledContentColor = Color.White,
                                )
                            ) {
                                Icon(imageVector = Icons.Outlined.Add, contentDescription = "", tint = Color.Black)
                                Text(text = "  Add to Watchlist", color = Color.Black)
                            }
                        }
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                isLoading()
                Text(text = "Loading....")
            }
        }
    }
}
