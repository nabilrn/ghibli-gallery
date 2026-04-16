package com.example.comsposesubmission.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comsposesubmission.ui.SharedViewModel
import com.example.comsposesubmission.ui.component.GhibliMovieCard
import kotlinx.coroutines.delay

@Composable
fun ListScreen(viewModel: SharedViewModel, onMovieClick: (String) -> Unit) {
    val movies by viewModel.filteredMovies.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    
    if (movies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No movies found",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            itemsIndexed(movies, key = { _, m -> m.id }) { index, movie ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(movie.id) {
                    delay(index * 50L)
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        animationSpec = spring(stiffness = Spring.StiffnessLow)
                    ) + slideInVertically(
                        initialOffsetY = { it / 5 },
                        animationSpec = spring(
                            dampingRatio = 0.75f,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    GhibliMovieCard(
                        movie = movie,
                        onClick = { onMovieClick(movie.id) },
                        onFavoriteClick = { viewModel.toggleFavorite(movie) },
                        isFavorite = favoriteIds.contains(movie.id)
                    )
                }
            }
        }
    }
}
