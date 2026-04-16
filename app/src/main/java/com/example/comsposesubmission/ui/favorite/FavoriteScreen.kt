package com.example.comsposesubmission.ui.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comsposesubmission.ui.SharedViewModel
import com.example.comsposesubmission.ui.component.GhibliMovieCard
import kotlinx.coroutines.delay

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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No favorites yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap the heart on a movie to save it here",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                itemsIndexed(
                    items = favoriteMovies,
                    key = { _, movie -> movie.id }
                ) { index, movie ->
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
                            isFavorite = true,
                            onFavoriteClick = { viewModel.toggleFavorite(movie) },
                            onClick = { onMovieClick(movie.id) }
                        )
                    }
                }
            }
        }
    }
}
