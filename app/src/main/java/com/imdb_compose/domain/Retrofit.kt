package com.imdb_compose.domain

import com.imdb_compose.BuildConfig
import com.imdb_compose.domain.Resources.AIRING_TODAY_TV_PATH
import com.imdb_compose.domain.Resources.MOVIES_OF_WEEK_PATH
import com.imdb_compose.domain.Resources.MOVIE_DETAILS_PATH
import com.imdb_compose.domain.Resources.MOVIE_IMAGES_PATH
import com.imdb_compose.domain.Resources.PERSON_DETAILS_PATH
import com.imdb_compose.domain.Resources.POPULAR_PERSONS_PATH
import com.imdb_compose.domain.Resources.TRENDING_MOVIES_DAY_PATH
import com.imdb_compose.domain.Resources.TRENDING_PERSONS_DAY_PATH
import com.imdb_compose.domain.Resources.TRENDING_TV_DAY_PATH
import com.imdb_compose.domain.Resources.TV_SERIES_DETAILS_PATH
import com.imdb_compose.domain.Resources.TV_SERIES_IMGAGES_PATH
import com.imdb_compose.domain.Resources.UPCOMING_MOVIE_PATH
import retrofit2.http.GET
import retrofit2.http.Path

object Resources {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/"

    const val UPCOMING_MOVIE_PATH = "3/movie/upcoming"
    const val MOVIES_OF_WEEK_PATH = "3/trending/movie/week"
    const val TRENDING_MOVIES_DAY_PATH = "3/trending/movie/day"
    const val MOVIE_DETAILS_PATH = "3/movie/{id}"
    const val MOVIE_IMAGES_PATH = "3/movie/{id}/images"

    const val TRENDING_TV_DAY_PATH = "3/trending/tv/day"
    const val AIRING_TODAY_TV_PATH = "3/tv/airing_today"
    const val TV_SERIES_DETAILS_PATH = "/3/tv/{id}"
    const val TV_SERIES_IMGAGES_PATH = "/3/tv/{id}/images"

    const val POPULAR_PERSONS_PATH = "3/person/popular"
    const val TRENDING_PERSONS_DAY_PATH = "3/trending/person/day"
    const val PERSON_DETAILS_PATH = "3/person/{id}"
    const val TRENDING_PERSONS_WEEK_PATH = "3/trending/person/week"

    const val IMAGE_PATH =  "t/p/original/"
    const val IMAGE_PATH_w500 = "t/p/w500/"
}

interface MovieApi {
    @GET("${ MOVIES_OF_WEEK_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getMoviesOfWeekList(): MovieList

    @GET("${ TRENDING_MOVIES_DAY_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getTrendingMovies(): MovieList

    @GET("${ UPCOMING_MOVIE_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getUpcomingMovies(): MovieList

    @GET("${ MOVIE_DETAILS_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetail

    @GET("${ MOVIE_IMAGES_PATH }?api_key=${ BuildConfig.API_KEY }")
    suspend fun getMovieImages(@Path("id") id: Int): Images
}

interface TvApi {
    @GET("${ TRENDING_TV_DAY_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getTrendingTv(): TvList

    @GET("${ AIRING_TODAY_TV_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getAiringTodayTv(): TvList

//    @GET("3/tv/{id}/images/{img_path}?api_key=${BuildConfig.API_KEY}")
//    suspend fun getTvImg(@Path("id") id: Int, @Path("img_path") imgPath: String): ImageResults

    @GET("${ TV_SERIES_DETAILS_PATH }?api_key=${ BuildConfig.API_KEY }")
    suspend fun getTvSeriesDetails(@Path("id") id: Int): TvDetails

    @GET("${ TV_SERIES_IMGAGES_PATH }?api_key=${ BuildConfig.API_KEY }")
    suspend fun getTvSeriesImages(@Path("id") id: Int): Images
}

interface PeopleApi {
    @GET("${ POPULAR_PERSONS_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getPopularPersons(): ActorList

    @GET("${ TRENDING_PERSONS_DAY_PATH }?language=en-US&append_to_response=details&api_key=${ BuildConfig.API_KEY }")
    suspend fun getTrendingPersons(): ActorList

    @GET("${ PERSON_DETAILS_PATH }?language=en-US&api_key=${ BuildConfig.API_KEY }")
    suspend fun getPersonDetails(@Path("id") id: Int): ActorDetail
}
