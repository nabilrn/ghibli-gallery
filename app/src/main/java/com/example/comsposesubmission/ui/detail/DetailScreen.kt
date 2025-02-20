package com.example.comsposesubmission.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.comsposesubmission.data.GhibliMovie
import com.example.comsposesubmission.ui.SharedViewModel
import com.example.comsposesubmission.ui.theme.ComsposeSubmissionTheme
import com.example.comsposesubmission.ui.theme.NewPrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    movieId: String,
    viewModel: SharedViewModel,
    onBackClick: () -> Unit,
) {
    ComsposeSubmissionTheme {
        val context = LocalContext.current
        viewModel.getMovieById(movieId)

        val movie by viewModel.selectedMovie.collectAsState()

        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
        }

        Scaffold { innerPadding ->
            movie?.let { ghibliMovie ->
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = ghibliMovie.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(0f)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.7f),
                                        Color.Black.copy(alpha = 0.9f)
                                    )
                                )
                            )
                            .zIndex(1f)
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(top = 24.dp, start = 16.dp)
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .zIndex(10f)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(spring(stiffness = Spring.StiffnessLow)) +
                                slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                ),
                        modifier = Modifier.zIndex(2f)
                    ) {
                        DetailContentStyled(
                            movie = ghibliMovie,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            onWatchTrailerClick = { trailerUrl ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            } ?: run {
                LoadingScreen(innerPadding)
            }
        }
    }
}
@Composable
fun LoadingScreen(innerPadding: PaddingValues) {
    val coroutineScope = rememberCoroutineScope()
    val animationColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )
    var colorIndex by remember { mutableIntStateOf(0) }
    var color by remember { mutableStateOf(animationColors[0]) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(750)
                colorIndex = (colorIndex + 1) % animationColors.size
                color = animationColors[colorIndex]
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            color = color,
            strokeWidth = 8.dp
        )

        Text(
            text = "Loading Ghibli Magic...",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 120.dp)
        )
    }
}

@Composable
fun DetailContentStyled(
    movie: GhibliMovie,
    modifier: Modifier = Modifier,
    onWatchTrailerClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 24.dp)
        )  {
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .aspectRatio(2/3f)
                    .shadow(24.dp)
                    .zIndex(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = movie.photoUrl,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .shadow(8.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.title,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        FilmYearBadge(year = movie.releaseDate)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = NewPrimaryColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = " • Classic",
                            fontSize = 14.sp,
                            color = NewPrimaryColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )

                    Text(
                        text = "FILM CREDITS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DetailCredit(
                            title = "Director",
                            name = movie.director,
                            modifier = Modifier.weight(1f)
                        )
                        DetailCredit(
                            title = "Producer",
                            name = movie.producer,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )

                    Text(
                        text = "SYNOPSIS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = movie.description,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Watch button
                    Button(
                        onClick = {
                            onWatchTrailerClick(movie.trailerUrl)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NewPrimaryColor
                        )
                    ) {
                        Text(
                            text = "Watch Trailer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilmYearBadge(year: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = NewPrimaryColor.copy(alpha = 0.2f) // Use a lighter version of NewPrimaryColor
    ) {
        Text(
            text = year,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = NewPrimaryColor, // Use NewPrimaryColor for the text
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun DetailCredit(
    title: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title.uppercase(),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}