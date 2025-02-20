package com.example.comsposesubmission

import com.example.comsposesubmission.ui.component.GhibliSearchBar
import com.example.comsposesubmission.ui.component.GhibliTopBar
import com.example.comsposesubmission.ui.list.ListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.comsposesubmission.ui.about.AboutScreen
import com.example.comsposesubmission.ui.component.BottomNavBar
import com.example.comsposesubmission.ui.detail.DetailScreen
import com.example.comsposesubmission.ui.favorite.FavoriteScreen
import com.example.comsposesubmission.ui.theme.ComsposeSubmissionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComsposeSubmissionTheme {
                MainScreen()
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
    val isAboutScreen = currentRoute == "about"
    val isFavoriteScreen = currentRoute.startsWith("favorites")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isDetailScreen && !isAboutScreen) {
                Column {
                    val topBarTitle = when {
                        currentRoute.startsWith("home") -> "Studio Ghibli Movies"
                        isFavoriteScreen -> "Favorite Movies"
                        else -> "Studio Ghibli Movies"
                    }
                    GhibliTopBar(
                        title = topBarTitle,
                        onAccountClick = {
                            navController.navigate("about")
                        }
                    )
                    if (!isFavoriteScreen) {
                        GhibliSearchBar(
                            query = searchQuery,
                            onQueryChange = {
                                sharedViewModel.searchMovies(it)
                            }
                        )
                    }
                }
            }
        },
        bottomBar = {
            if (!isDetailScreen && !isAboutScreen) {
                BottomNavBar(
                    currentRoute = when {
                        currentRoute.startsWith("home") -> "home"
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
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable("about") {
                AboutScreen(
                    onBackClick = {
                        navController.navigateUp()
                    }
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