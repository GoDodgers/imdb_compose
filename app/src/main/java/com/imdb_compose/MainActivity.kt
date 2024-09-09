package com.imdb_compose

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.Action
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.imdb_compose.ui.theme.Imdb_composeTheme
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Imdb_composeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Navigator.HomeScreen ) {
                        composable<Navigator.HomeScreen> {
                            HomeScreen(top = { TopBar() }, bottom = { BottomBar() }, navController = navController)
                        }
                        composable<Navigator.CategoryPage> {
                            val args =  it.toRoute<Navigator.CategoryPage>()
                            CategoryPage(args.id, args.catagory)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    navController: NavController
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            top()
        },
        bottomBar = {
            bottom()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(catagories.size) { i ->
                    GenerateLazyRows(catagories[i], navController)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "What to watch")
        }
    )
}

@Composable
fun BottomBar() {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        actions = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "home")
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "home")
                    Icon(imageVector = Icons.Default.Person, contentDescription = "profile")
                }
            }
        }
    )
}

@Composable
fun GenerateLazyRows(catagory: String, navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Column {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = catagory,
                    modifier = Modifier.padding(start = 8.dp)
                )
                TextButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { navController.navigate(Navigator.CategoryPage(id = 222, catagory = catagory)) }
                ) {
                    Text(text = "see all")
                }
            }
            LazyRow {
                items(movies.size) { i ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.padding(bottom = 16.dp)) {
                        Box (
                            modifier = Modifier
                                .width(125.dp)
                                .height(175.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                .padding(end = 8.dp),
                                contentAlignment = Alignment.TopStart
                        ) {
                            Box(modifier = Modifier.padding(top = 4.dp, start = 4.dp)) {
                                Icon(imageVector = Icons.Outlined.AddBox, contentDescription = "add")
                            }
                            Text(
                                text = movies[i],
                                softWrap = false,
                                modifier = Modifier.align(Alignment.Center)
                            )

                        }
                        Box (
                            modifier = Modifier
                                .width(125.dp)
                                .height(100.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                .padding(end = 8.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Box(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Column (
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = "${ i + 1 }",
                                        modifier = Modifier,
                                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    )
                                    Icon(imageVector = Icons.Filled.Star, contentDescription = "rating")
                                    Text(text = movies[i])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

interface Navigator {
    @Serializable
    data object HomeScreen: Navigator
    @Serializable
    data class CategoryPage(val id: Int, val catagory: String): Navigator
}

var catagories: MutableList<String> = mutableListOf("Top 10", "Top picks for your", "Now Streaming", "In theaters")
var movies: MutableList<String> = mutableListOf("Game of Thrones", "Deadpool", "Dumb & Dumber", "New Hope", "Empire", "Last Jedi", "Foobar", "Bazboo", "Foobaz", "FooLee")
