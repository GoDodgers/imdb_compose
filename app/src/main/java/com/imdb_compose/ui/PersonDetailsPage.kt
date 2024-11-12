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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.gray500
import com.example.compose.ripeMango
import com.imdb_compose.BottomBar
import com.imdb_compose.TopBar
import com.imdb_compose.domain.Resources
import com.imdb_compose.isLoading
import com.imdb_compose.shadow2
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun PersonDetailsPage(
    person: String,
    id: Int,
    viewModel: HomeScreenViewModel,
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
        viewModel.viewModelScope.launch {
            viewModel.getPersonDetails(id)
        }

        if (viewModel.personDetails.value?.id == id) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val personDetails = viewModel.personDetails.collectAsState()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow2(
                                color = gray500,
                                offsetX = 0.dp,
                                offsetY = 0.dp,
                                blurRadiusFilter = "SOLID"
                            ),
                    ) {
                        personDetails.value?.let { details ->
                            Box(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)) {
                                Column {
                                    // Name & Known For
                                    Column {
                                        Text(
                                            text = details.name,
                                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                                            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = details.known_for_department,
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                                            fontFamily = MaterialTheme.typography.titleMedium.fontFamily
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    // Profile Pic & Bio
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(240.dp)
                                    ) {
                                        Box(modifier = Modifier.width(125.dp)) {
                                            AsyncImage(
                                                model = "${ Resources.BASE_IMAGE_URL }${ Resources.IMAGE_PATH }${ details.profile_path }",
                                                contentDescription = null,
                                                contentScale = ContentScale.FillWidth
                                            )
                                        }

                                        Box(modifier = Modifier.padding(start = 8.dp)) {
                                            Column {
                                                details.biography.let { bio ->
                                                    Text(
                                                        text = bio,
                                                        maxLines = 6,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                                details.birthday.let { bDay ->
                                                    Text(text = "Born: ${ bDay }")
                                                }
                                            }
                                        }
                                    }
                                    // Add to favorits
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp),
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
                                        Text(text = "  Add to favorites", color = Color.Black)
                                    }
                                }
                            }
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
