package com.example.comsposesubmission.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comsposesubmission.ui.SharedViewModel
import com.example.comsposesubmission.ui.component.GhibliMovieCard

@Composable
fun ListScreen(viewModel: SharedViewModel, onMovieClick: (String) -> Unit) {
    val movies by viewModel.filteredMovies.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    if (movies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No data match with your search", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                GhibliMovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onFavoriteClick = {
                        viewModel.toggleFavorite(movie)
                    },
                    isFavorite = favoriteIds.contains(movie.id)
                )
            }
        }
    }
}