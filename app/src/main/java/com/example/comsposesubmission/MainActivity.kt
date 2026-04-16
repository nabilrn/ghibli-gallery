package com.example.comsposesubmission

import com.example.comsposesubmission.ui.component.GhibliTopBar
import com.example.comsposesubmission.ui.list.ListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.comsposesubmission.ui.SharedViewModel
import com.example.comsposesubmission.ui.component.BottomNavBar
import com.example.comsposesubmission.ui.detail.DetailScreen
import com.example.comsposesubmission.ui.favorite.FavoriteScreen
import com.example.comsposesubmission.ui.splash.SplashScreen
import com.example.comsposesubmission.ui.theme.ComsposeSubmissionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash has a dark background, so start with white status bar icons.
        // Compose theme SideEffect will adjust after splash finishes.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        setContent {
            ComsposeSubmissionTheme {
                var splashDone by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize()) {
                    AnimatedVisibility(
                        visible = splashDone,
                        enter = fadeIn(spring(stiffness = Spring.StiffnessVeryLow))
                    ) {
                        MainScreen()
                    }

                    if (!splashDone) {
                        SplashScreen(onFinished = { splashDone = true })
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    val searchQuery by sharedViewModel.searchQuery.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    val isDetailScreen = currentRoute.startsWith("detail/")
    val isFavoriteScreen = currentRoute.startsWith("favorites")
    val isHomeScreen = currentRoute.startsWith("home")

    var searchOpen by remember { mutableStateOf(false) }

    // Close search when navigating away from home
    if (!isHomeScreen && searchOpen) {
        searchOpen = false
        sharedViewModel.searchMovies("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isDetailScreen) {
                GhibliTopBar(
                    title = if (isFavoriteScreen) "Favorites" else "Studio Ghibli",
                    showSearch = searchOpen,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { sharedViewModel.searchMovies(it) },
                    onSearchToggle = {
                        searchOpen = !searchOpen
                        if (!searchOpen) sharedViewModel.searchMovies("")
                    },
                    searchEnabled = isHomeScreen
                )
            }
        },
        bottomBar = {
            if (!isDetailScreen) {
                BottomNavBar(
                    currentRoute = when {
                        isHomeScreen -> "home"
                        isFavoriteScreen -> "favorites"
                        else -> "home"
                    },
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                ListScreen(
                    viewModel = sharedViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate("detail/$movieId")
                    }
                )
            }
            composable("favorites") {
                FavoriteScreen(
                    viewModel = sharedViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate("detail/$movieId")
                    }
                )
            }
            composable(
                route = "detail/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
                DetailScreen(
                    movieId = movieId,
                    viewModel = sharedViewModel,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityPreview() {
    ComsposeSubmissionTheme {
        MainScreen()
    }
}
