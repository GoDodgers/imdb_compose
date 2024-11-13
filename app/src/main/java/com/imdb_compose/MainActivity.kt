@file:OptIn(ExperimentalMaterial3Api::class)
package com.imdb_compose

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.SubcomposeAsyncImage
import com.example.compose.AppTheme
import com.example.compose.blue400
import com.example.compose.gray100
import com.example.compose.gray200
import com.example.compose.gray300
import com.example.compose.gray400
import com.example.compose.gray500
import com.example.compose.gray600
import com.example.compose.ripeMango
import com.imdb_compose.domain.Resources
import com.imdb_compose.domain.ActorList
import com.imdb_compose.domain.ActorResult
import com.imdb_compose.domain.Images
import com.imdb_compose.domain.MovieList
import com.imdb_compose.domain.MovieResult
import com.imdb_compose.domain.TvList
import com.imdb_compose.domain.TvResult
import com.imdb_compose.ui.CategoryPage
import com.imdb_compose.ui.HomeScreenViewModel
import com.imdb_compose.ui.MovieDetailsPage
import com.imdb_compose.ui.Navigator
import com.imdb_compose.ui.PersonDetailsPage
import com.imdb_compose.ui.TvDetailsPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    MaterialTheme.colorScheme.primary.toArgb(),
                    MaterialTheme.colorScheme.primary.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    MaterialTheme.colorScheme.primary.toArgb(),
                    MaterialTheme.colorScheme.primary.toArgb()
                )
            )
            AppTheme {
                Theme()
            }
        }
    }
}

@Composable
fun Theme() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        val navController = rememberNavController()
        val viewModel: HomeScreenViewModel = hiltViewModel()

        NavHost(navController = navController, startDestination = Navigator.HomeScreen ) {
            composable<Navigator.HomeScreen> {
                HomeScreen(
                    top = { TopBar(showBackButton = false, backButton = {}) },
                    bottom = { BottomBar(navController) },
                    viewModel.catagories,
                    viewModel.noMovies.collectAsState(),
                    viewModel.movieListOfWeek.collectAsState(),
                    viewModel.trendingMovies.collectAsState(),
                    viewModel.airingTodayTv.collectAsState(),
                    viewModel.trendingTv.collectAsState(),
                    viewModel.boxOffice,
                    viewModel.upcomingMovies.collectAsState(),
                    viewModel.popularPersons.collectAsState(),
                    viewModel.trendingPersons.collectAsState(),
                    navController = navController
                )
            }
            composable<Navigator.CategoryPage> {
                val args =  it.toRoute<Navigator.CategoryPage>()
                CategoryPage(args.catagory, navController = navController, { navController.popBackStack() })
            }
            composable<Navigator.MovieDetailsPage> {
                val args =  it.toRoute<Navigator.MovieDetailsPage>()
                MovieDetailsPage(args.id, args.title, args.catagory, navController = navController, { navController.popBackStack() })
            }
            composable<Navigator.PersonDetailsPage> {
                val args =  it.toRoute<Navigator.PersonDetailsPage>()
                PersonDetailsPage(args.id, args.person, args.catagory, navController = navController, { navController.popBackStack() })
            }
            composable<Navigator.TvDetailsPage> {
                val args =  it.toRoute<Navigator.TvDetailsPage>()
                TvDetailsPage(args.id, args.show, args.catagory, navController = navController, { navController.popBackStack() })
            }
        }
    }
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    catagories: List<String>,
    noMovies: State<MovieList?>,
    movieListOfWeek: State<MovieList?>,
    trendingMovies: State<MovieList?>,
    airingTodayTv: State<TvList?>,
    trendingTv: State<TvList?>,
    boxOffice: List<Map<String, String>>,
    upcomingMovies: State<MovieList?>,
    popularPersons: State<ActorList?>,
    trendingPersons: State<ActorList?>,
    navController: NavController
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
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
                item {
                    catagories.forEachIndexed { i, catagory ->
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.95f)
                                .fillMaxHeight(fraction = 0.95f)
                                .background(color = gray400)
                                .padding(bottom = 16.dp)
                        ) {
                            Column (modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Orange Accent <Catagory>
                                    Row (verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .clip(RoundedCornerShape(24.dp))
                                                .height(36.dp)
                                                .width(6.dp)
                                                .background(ripeMango)
                                        )
                                        Text(
                                            text = catagory,
                                            modifier = Modifier.padding(start = 8.dp),
                                            color = MaterialTheme.colorScheme.onSecondary,
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                    }
                                    // See All Btn
                                    Row (verticalAlignment = Alignment.CenterVertically) {
                                        TextButton(
                                            modifier = Modifier.padding(start = 8.dp),
                                            onClick = { navController.navigate(Navigator.CategoryPage(catagory = catagory)) }
                                        ) {
                                            Text(
                                                text = stringResource(R.string.see_all),
                                                modifier = Modifier.padding(start = 8.dp),
                                                color = blue400,
                                                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                when(catagory) {
                                    "Popular actors" -> PersonBox(catagory, popularPersons, navController)
//                                    "Trending movies" -> MovieBox(catagory, trendingMovies, navController)
//                                    "Movies of the week" -> MovieBox(catagory, movieListOfWeek, navController)
//                                    "Upcoming movies" -> UpcommingBox(catagory, upcomingMovies, navController)
//                                    "Tv airing today" -> TvBox(catagory, airingTodayTv, navController)
//                                    "Trending tv" -> TvBox(catagory, trendingTv, navController)
//                                    "Trending people" -> PersonBox(catagory, trendingPersons, navController)
//                                    "Top box office" -> BoxOfficeBox(catagory, boxOffice, navController)
                                    else -> MovieBox(catagory, noMovies, navController)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    title: String = stringResource(R.string.what_to_watch),
    showBackButton: Boolean,
    backButton: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { backButton() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = TopAppBarDefaults.topAppBarColors().titleContentColor,
            actionIconContentColor = TopAppBarDefaults.topAppBarColors().actionIconContentColor,
            scrolledContainerColor = TopAppBarDefaults.topAppBarColors().scrolledContainerColor,
            navigationIconContentColor = TopAppBarDefaults.topAppBarColors().navigationIconContentColor
        )
    )
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        actions = {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = { navController.navigate(Navigator.HomeScreen) }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "home")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "home")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "profile")
                }
            }
        },
        containerColor = Color.Transparent
    )
}

@Composable
fun MovieBox(
    catagory: String,
    movies: State<MovieList?>,
    navController: NavController
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        LazyRow {
            movies.value?.results?.filter {
                it.poster_path != null
            }?.forEachIndexed { i, movie ->
                item {
                    BoxA(
                        catagory = catagory,
                        id = movie.id,
                        name = movie.title,
                        posterPath = movie.poster_path,
                        details = movie.overview,
                        score = movie.vote_average,
                        onClick = { id ->
                            navController.navigate(
                                Navigator.MovieDetailsPage(
                                    title = movie.title,
                                    id = id,
                                    catagory = catagory
                                )
                            )
                        },
                        rank = "${ i + 1 }"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun TvBox(
    catagory: String,
    tvShows: State<TvList?>,
    navController: NavController
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        LazyRow {
            tvShows.value?.results?.filter {
                it.poster_path != null
            }?.forEachIndexed { i, show ->
                item {
                    BoxA(
                        catagory = catagory,
                        id = show.id,
                        name = show.name,
                        posterPath = show.poster_path,
                        details = show.overview,
                        score = show.vote_average,
                        onClick = { id ->
                            navController.navigate(
                                Navigator.TvDetailsPage(
                                    show = show.name,
                                    id = id,
                                    catagory = catagory
                                )
                            )
                        },
                        rank = "${ i + 1 }"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun PersonBox(
    catagory: String,
    persons: State<ActorList?>,
    navController: NavController
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        LazyRow {
            persons.value?.results?.filter {
                it.profile_path != null
            }?.forEachIndexed { i, person ->
                item {
                    BoxClipped(
                        catagory = catagory,
                        rank = "${ i + 1 }",
                        id = person.id,
                        name = person.name,
                        bestKnownFor = person.known_for_department,
                        popularity = person.popularity,
                        profilePath = person.profile_path,
                        navController = navController,
                        onClick = { id ->
                            navController.navigate(
                                Navigator.PersonDetailsPage(
                                    person = person.name,
                                    id = id,
                                    catagory = catagory
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun BoxClipped(
    catagory: String,
    id: Int,
    rank: String,
    name: String,
    bestKnownFor: String,
    popularity: String,
    profilePath: String,
    navController: NavController,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .height(336.dp)
            .width(176.dp)
            .shadow(elevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(176.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .shadow(elevation = 8.dp)
                .clickable { onClick(id) },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(256.dp)
                    .width(160.dp)
                    .shadow(8.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageAsync(
                    contentDescription = "${ catagory } ${ name }",
                    clip = true,
                    aspectRatio = 5f / 8f,
                    backDropPath = profilePath
                )
                Heart(name = name, paddingStart = 8.dp, paddingBottom = 8.dp)
            }
        }
        Details(catagory = catagory, name = name, score = popularity, rank = rank, details = "", width = 176.dp, trending = false, showCircleI = false)
    }
}

@Composable
fun Heart(
    name: String,
    paddingStart: Dp,
    paddingBottom: Dp
) {
    // Favorite
    Box(
        modifier = Modifier
            .padding(start = paddingStart, bottom = paddingBottom)
            .size(40.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = Color.Magenta,
                shape = CircleShape
            )
            .background(gray200.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier,
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite ${ name }"
        )
    }
}

@Composable
fun UpcommingBox(
    catagory: String,
    movies: State<MovieList?>,
    navController: NavController
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        LazyRow {
            movies.value?.results?.forEachIndexed { i, movie ->
                item {
                    BoxB(
                        catagory = catagory,
                        id = movie.id,
                        name = movie.title,
                        details = movie.overview,
                        date = movie.release_date,
                        score = movie.vote_average,
                        posterPath = movie.poster_path,
                        onClick = { id ->
                            navController.navigate(
                                Navigator.MovieDetailsPage(
                                    title = movie.title,
                                    id = id,
                                    catagory = catagory
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun BoxOfficeBox(
    catagory: String,
    boxOfficeNumbers: List<Map<String, String>>,
    navController: NavController
) {
    // date range
    Row (
        Modifier
            .padding(start = 16.dp)
            .fillMaxWidth(0.975f)
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .shadow(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                text = "${ boxOfficeNumbers.first()["date range"] }"
            )
        }
    }

    Box (
        Modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth(0.98f)) {
        Box(
            modifier = Modifier
                .height(650.dp)
                .fillMaxWidth(0.99f)
                .shadow(8.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                boxOfficeNumbers.forEachIndexed { i, movieMap ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 64.dp, max = 80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .width(72.dp)
                                .padding(start = 8.dp, end = 8.dp),
                            text = "${ i + 1 }",
                            fontSize = MaterialTheme.typography.displaySmall.fontSize,
                            textAlign = TextAlign.Center
                        )

                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(36.dp)
                                .background(gray200.copy(alpha = 0.6f))
                                .drawBehind {
                                    val path = Path().apply {
                                        moveTo(75f, 126f)
                                        lineTo(0f, 166f)
                                        lineTo(0f, 126f)
                                        moveTo(45f, 126f)
                                        lineTo(125f, 126f)
                                        lineTo(125f, 166f)
                                        close()
                                    }
                                    drawPath(path, color = gray200.copy(alpha = 0.6f))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "add favorite"
                            )
                        }

                        Column (
                            modifier = Modifier
                        ) {
                            Text(text = "${ movieMap["Release"] }")
                            Text(text = "${ movieMap["Gross"] }", textAlign = TextAlign.Center)
                        }

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                            Icon(
                                modifier = Modifier.padding(end = 16.dp),
                                imageVector = Icons.Outlined.BookOnline,
                                contentDescription = "book online"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxA(
    catagory: String,
    id: Int,
    name: String,
    posterPath: String,
    details: String,
    score: String,
    onClick: (Int) -> Unit,
    rank: String
) {
    Column(
        modifier = Modifier
            .width(225.dp)
            .height(525.dp)
            .shadow(elevation = 8.dp)
    ) {
        Box (
            modifier = Modifier
                .width(220.dp)
                .clickable { onClick(id) },
            contentAlignment = Alignment.TopStart
        ) {
            Column (modifier = Modifier.padding(5.dp)) {
                Box(modifier = Modifier.width(210.dp)) {
                    ImageAsync(
                        contentDescription = "${ catagory } ${ name }",
                        backDropPath = posterPath
                    )
                    Ribbon()
                }
            }
        }
        val trending = Regex("trending").containsMatchIn(catagory.lowercase())
        Details(catagory = catagory, rank = rank, name = name, details = details, score = score, width = 225.dp, trending = trending, showCircleI = !trending)
    }
}

@Composable
fun BoxB(
    catagory: String,
    id: Int,
    name: String,
    details: String,
    score: String,
    date: String,
    posterPath: String,
    onClick: (Int) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .width(323.dp)
        ) {
            // upcoming date
            Box(
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, bottom = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                //"2024-07-31"
                val dateParts = date.split("-")
                val monthMap = mapOf(
                    "01" to "Jan",
                    "02" to "Feb",
                    "03" to "Mar",
                    "04" to "Apr",
                    "05" to "May",
                    "06" to "Jun",
                    "07" to "Jul",
                    "08" to "Aug",
                    "09" to "Sep",
                    "10" to "Oct",
                    "11" to "Nov",
                    "12" to "Dec",
                )

                Text(
                    text = "${monthMap[dateParts[1]]}-${dateParts[2]}",
//                    text = date,
                    fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                    color = ripeMango,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .width(323.dp)
                .shadow(elevation = 8.dp)
        ) {
            // movie box
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .clickable { onClick(id) },
                contentAlignment = Alignment.TopStart
            ) {
                Column(modifier = Modifier.padding(5.dp)) {
                    Box(modifier = Modifier.width(310.dp)) {
                        ImageAsync(
                            contentDescription = "${ catagory } ${ name }",
                            aspectRatio = 5f / 8f,
                            backDropPath = posterPath,
                        )
                        // ribbon
                        Ribbon(paddingStart = 12.dp)
                    }
                }
            }
            Details(catagory = catagory, name = name, rank = "", details = details, score = score, width = 323.dp, trending = false, showCircleI = false)
        }
    }
}

@Composable
fun Details(
    catagory: String,
    rank: String,
    name: String,
    details: String,
    score: String,
    width: Dp,
    showCircleI: Boolean,
    trending: Boolean
) {
    Column (
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .padding(start = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Trending
        if (trending) {
            Box(
                modifier = Modifier.padding(start = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = rank,
                    fontStyle = MaterialTheme.typography.displaySmall.fontStyle
                )
            }
        }
        // movie || show || person name
        Box(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = name, minLines = 1, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        // details
        if (details.isNotEmpty()) {
            Text(
                text = details,
                modifier = Modifier.padding(all = 12.dp),
                minLines = 1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        // rating
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "rating",
                tint = ripeMango
            )
            Text(modifier = Modifier.padding(start = 8.dp), text = score)
        }
        // Cirlce i
        if (showCircleI) {
            InformationCircle(paddingEnd = 16.dp)
        }
    }
}

@Composable
fun Ribbon(
    paddingStart: Dp = 0.dp,
    paddingTop: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
    left: Float = 0.0f,
    top: Float = 0.0f
) {
    Box(
        modifier = Modifier
            .padding(
                start = paddingStart,
                top = paddingTop,
                end = paddingEnd,
                bottom = paddingBottom
            )
            .size(36.dp)
            .background(gray200.copy(alpha = 0.6f))
            .drawBehind {
                val path = Path().apply {
                    moveTo(75f, 126f)
                    lineTo(0f, 166f)
                    lineTo(0f, 126f)
                    moveTo(45f, 126f)
                    lineTo(125f, 126f)
                    lineTo(125f, 166f)
                    close()
                }
                this.translate(left = left, top = top, {})
                drawPath(path, color = gray200.copy(alpha = 0.6f))
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "add favorite"
        )
    }
}

@Composable
fun InformationCircle(
    paddingStart: Dp = 0.dp,
    paddingTop: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = paddingStart,
                top = paddingTop,
                end = paddingEnd,
                bottom = paddingBottom
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = gray200),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(color = gray600),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "i",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = gray100,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ImageAsync(
    clip: Boolean = false,
    crop: Boolean = false,
    contentDescription: String,
    aspectRatio: Float = 2f / 3f,
    imgPath: String = Resources.IMAGE_PATH,
    backDropPath: String
) {
    SubcomposeAsyncImage(
        model = "${ Resources.BASE_IMAGE_URL }${ imgPath }${ backDropPath }",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .clip(
                if (clip) {
                    RoundedCornerShape(16.dp)
                } else {
                    RoundedCornerShape(0.dp)
                }
            ),
        contentScale = if (crop) {
            ContentScale.Crop
        } else {
            ContentScale.FillBounds
        },
        contentDescription = contentDescription,
        loading = { isLoading() }
    )
}

@Composable
fun Tags(txt: String) {
    Box(modifier = Modifier.shadow(8.dp)) {
        Box(
            modifier = Modifier
                .shadow(8.dp)
                .background(color = gray300)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                text = txt,
                color = Color.White
            )
        }
    }
}

@Composable
fun Pager(images: State<Images?>) {
    val pagerState = rememberPagerState { images.value?.backdrops?.size!! }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) { i ->
        Card(modifier = Modifier.fillMaxWidth()) {
            ImageAsync(
                backDropPath = when (i) {
                    0 -> images.value?.backdrops!![0].file_path
                    1 -> images.value?.backdrops!![1].file_path
                    2 -> images.value?.backdrops!![2].file_path
                    3 -> images.value?.backdrops!![3].file_path
                    else -> images.value?.backdrops!![4].file_path
                },
                aspectRatio = when (i) {
                    0 -> images.value?.backdrops!![0].aspect_ratio
                    1 -> images.value?.backdrops!![1].aspect_ratio
                    2 -> images.value?.backdrops!![2].aspect_ratio
                    3 -> images.value?.backdrops!![3].aspect_ratio
                    else -> images.value?.backdrops!![4].aspect_ratio
                },
                contentDescription = ""
            )
        }
    }
}

@Composable
fun Carousel(images: State<Images?>) {
    var rowSize by remember {
        mutableStateOf(Size.Zero)
    }
    val state = rememberCarouselState { 5 }

    Row (
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                rowSize = layoutCoordinates.size.toSize()
            }
    ) {
        val width = LocalDensity.current.run { rowSize.width.toDp() }

        Column {
            HorizontalMultiBrowseCarousel(
                state = state,
                preferredItemWidth = width,
                itemSpacing = 0.dp
            ) {i ->
                Box(modifier = Modifier.fillMaxSize()) {
                    ImageAsync(
                        backDropPath = when (i) {
                            0 -> images.value?.backdrops!![0].file_path
                            1 -> images.value?.backdrops!![1].file_path
                            2 -> images.value?.backdrops!![2].file_path
                            3 -> images.value?.backdrops!![3].file_path
                            else -> images.value?.backdrops!![4].file_path
                        },
                        aspectRatio = when (i) {
                            0 -> images.value?.backdrops!![0].aspect_ratio
                            1 -> images.value?.backdrops!![1].aspect_ratio
                            2 -> images.value?.backdrops!![2].aspect_ratio
                            3 -> images.value?.backdrops!![3].aspect_ratio
                            else -> images.value?.backdrops!![4].aspect_ratio
                        },
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun isLoading() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(fraction = 0.5f),
            color = MaterialTheme.colorScheme.outline
        )
    }
}
