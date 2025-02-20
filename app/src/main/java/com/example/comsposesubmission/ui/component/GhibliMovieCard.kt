package com.example.comsposesubmission.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.comsposesubmission.data.GhibliMovie
import com.example.comsposesubmission.ui.theme.NewPrimaryColor

@Composable
fun GhibliMovieCard(
    movie: GhibliMovie,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit = {},
    isFavorite: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }

    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 6f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing), label = ""
    )

    val favoriteColor by animateColorAsState(
        targetValue = if (isFavorite) NewPrimaryColor else Color.White,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                shadowElevation = elevation
                shape = RoundedCornerShape(16.dp)
            }
            .shadow(elevation = elevation.dp, shape = RoundedCornerShape(16.dp), spotColor = NewPrimaryColor.copy(alpha = 0.2f))
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = movie.photoUrl,
                    contentDescription = "Movie Poster ${movie.title}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.1f),
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 100f
                            )
                        )
                )

                Surface(
                    color = NewPrimaryColor.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = movie.releaseDate,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        onFavoriteClick()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = favoriteColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Director: ${movie.director}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val shortDescription = if (movie.description.length > 120) {
                    movie.description.take(120) + "..."
                } else {
                    movie.description
                }

                Text(
                    text = shortDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 16.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = NewPrimaryColor.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = "Ghibli Studios",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.White
                        )
                    }

                    TextButton(
                        onClick = onClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = NewPrimaryColor
                        )
                    ) {
                        Text(
                            text = "View Details",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AsyncImage(model: String, contentDescription: String, modifier: Modifier, contentScale: ContentScale) {
    Image(
        painter = rememberAsyncImagePainter(model = model),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}