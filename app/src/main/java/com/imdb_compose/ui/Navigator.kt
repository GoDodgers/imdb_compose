package com.imdb_compose.ui

import kotlinx.serialization.Serializable

interface Navigator {
    @Serializable
    data object HomeScreen: Navigator
    @Serializable
    data class CategoryPage(val catagory: String): Navigator
    @Serializable
    data class MovieDetailsPage(val id: Int, val title: String, val catagory: String): Navigator
    @Serializable
    data class PersonDetailsPage(val id: Int, val person: String, val catagory: String): Navigator
    @Serializable
    data class TvDetailsPage(val id: Int, val show: String, val catagory: String): Navigator
}
