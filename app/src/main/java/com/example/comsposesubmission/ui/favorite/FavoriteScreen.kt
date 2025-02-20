package com.example.comsposesubmission.ui.favorite

import com.example.comsposesubmission.ui.component.GhibliMovieCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comsposesubmission.ui.SharedViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: SharedViewModel,
    onMovieClick: (String) -> Unit = {}
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (favoriteMovies.isEmpty()) {
            Text(
                text = "No favorite movies yet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(
                    items = favoriteMovies,
                    key = { movie -> movie.id }
                ) { movie ->
                    GhibliMovieCard(
                        movie = movie,
                        isFavorite = true,
                        onFavoriteClick = {
                            viewModel.toggleFavorite(movie)
                        },
                        onClick = {
                            onMovieClick(movie.id)
                        }
                    )
                }
            }
        }
    }
}