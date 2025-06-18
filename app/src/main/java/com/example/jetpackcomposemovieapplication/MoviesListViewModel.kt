package com.example.jetpackcomposemovieapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    val repository: MoviesListRepository
) : ViewModel() {

    private val _moviesListFlow = MutableStateFlow<List<MoviesListModel>>(emptyList())
    val moviesList: StateFlow<List<MoviesListModel>> = _moviesListFlow

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            repository.getMoviesList().onStart {
                _loading.value = true
            }.collect {
                _loading.value = false
                _moviesListFlow.value = it
            }
        }
    }

    fun markFavMovie(id: Int) = viewModelScope.launch {
        repository.markFavMovie(id)
    }

}


