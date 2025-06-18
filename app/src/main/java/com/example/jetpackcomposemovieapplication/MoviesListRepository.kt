package com.example.jetpackcomposemovieapplication

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesListRepository @Inject constructor() {

    suspend fun getMoviesList(): Flow<List<MoviesListModel>> = flow {
        delay(1000)
        emit(moviesList)
    }

    suspend fun markFavMovie(id: Int) {
        val index = moviesList.indexOfFirst { it.id == id }
        if (index != -1) {
            val movie = moviesList[index]
            _moviesList[index] = movie.copy(isFav = !movie.isFav)
        }
    }
}

private val _moviesList = mutableListOf(
    MoviesListModel(
        1,
        "April May 99",
        "7.5/10 IMDB",
        "Krushna, Prasad and Siddhesh, inseparable friends in a picturesque Konkan village, have every moment of their summer holiday planned together. But things get rough between them when they meet the lovely Zai, visiting her aunt for the summer.",
        R.drawable.april_may
    ),
    MoviesListModel(
        2,
        "Ata Thambaycha Naay",
        "6/10 IMDB",
        "Ata Thambaycha Naay! is inspired by the true story of common people who despite being dismissed by society and burdened by difficult jobs, take a bold step of enrolling in night school to complete their SSC education. They confront the limits of their education, often leaning on humour and quiet defiance to cope. As exams near, pressure builds-but so does their resolve. They dared to dream_ but can they make it come true?",
        R.drawable.ata_thambaycha_naay
    ),
    MoviesListModel(
        3,
        "How to Train Your Dragon",
        "8.5/10 IMDB",
        "On the rugged isle of Berk, a Viking boy named Hiccup defies centuries of tradition by befriending a dragon named Toothless. However, when an ancient threat emerges that endangers both species, Hiccup`s friendship with Toothless becomes the key to forging a new future. Together, they must navigate the delicate path toward peace, soaring beyond the boundaries of their worlds and redefining what it means to be a hero and a leader.",
        R.drawable.how_to_train_yourdragon
    ),
    MoviesListModel(
        4,
        "Mission: Impossible - The Final Reckoning",
        "7.5/10 IMDB",
        "Our lives are a sum of our choices. Every choice, every mission has led to this.\n" +
                "Tom Cruise is Ethan Hunt in Mission: Impossible - The Final Reckoning. Get ready to light the fuse, one last time!",
        R.drawable.mission_impossible
    ),
    MoviesListModel(
        5,
        "Raid 2",
        "7.5/10 IMDB",
        "Test desc",
        R.drawable.raid
    )
)
val moviesList: List<MoviesListModel> = _moviesList