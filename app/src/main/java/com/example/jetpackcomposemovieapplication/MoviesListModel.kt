package com.example.jetpackcomposemovieapplication

data class MoviesListModel(
    val id: Int,
    val header: String,
    val rating: String,
    val description: String,
    val image: Int,
    val isFav: Boolean = false
)
