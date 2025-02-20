package com.example.comsposesubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comsposesubmission.data.GhibliMovie
import com.example.comsposesubmission.data.GhibliMovieData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private val _favoriteMovies = MutableStateFlow<List<GhibliMovie>>(emptyList())
    val favoriteMovies: StateFlow<List<GhibliMovie>> = _favoriteMovies.asStateFlow()

    private val _movies = MutableStateFlow<List<GhibliMovie>>(emptyList())
    private val _selectedMovie = MutableStateFlow<GhibliMovie?>(null)
    val selectedMovie: StateFlow<GhibliMovie?> = _selectedMovie.asStateFlow()

    private val _filteredMovies = MutableStateFlow<List<GhibliMovie>>(emptyList())
    val filteredMovies: StateFlow<List<GhibliMovie>> = _filteredMovies

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _movies.value = GhibliMovieData.movies
            _filteredMovies.value = GhibliMovieData.movies
        }
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _filteredMovies.value = if (query.isEmpty()) {
                _movies.value
            } else {
                _movies.value.filter { it.title.contains(query, ignoreCase = true) }
            }
        }
    }

    fun getMovieById(movieId: String) {
        val movie = _movies.value.find { it.id == movieId }
            ?: _favoriteMovies.value.find { it.id == movieId }
        _selectedMovie.value = movie
    }

    fun toggleFavorite(movie: GhibliMovie) {
        _favoriteIds.update { currentIds ->
            if (currentIds.contains(movie.id)) {
                currentIds - movie.id
            } else {
                currentIds + movie.id
            }
        }

        _favoriteMovies.update { currentMovies ->
            if (currentMovies.any { it.id == movie.id }) {
                currentMovies.filter { it.id != movie.id }
            } else {
                currentMovies + movie
            }
        }
    }
}