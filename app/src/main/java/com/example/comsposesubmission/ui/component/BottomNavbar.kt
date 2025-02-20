package com.example.comsposesubmission.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comsposesubmission.ui.theme.ComsposeSubmissionTheme
import com.example.comsposesubmission.ui.theme.NewPrimaryColor

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val routes = listOf("home", "favorites")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            routes.forEach { route ->
                val selected = currentRoute == route

                NavBarItem(
                    title = route.capitalize(),
                    icon = getIcon(route, selected),
                    selected = selected,
                    onSelect = { onNavigate(route) }
                )
            }
        }
    }
}

@Composable
fun NavBarItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected)
            NewPrimaryColor
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "colorAnimation"
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "widthAnimation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onSelect,
                modifier = Modifier.size(48.dp)

            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = animatedColor,
                    modifier = Modifier.size(28.dp),

                    )
            }

            // Create a glowing effect when selected
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            NewPrimaryColor.copy(alpha = 0.1f),
                            CircleShape
                        )
                )
            }
        }

        Text(
            text = title,
            color = animatedColor,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )

        // Indicator bar
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(3.dp)
                .width(indicatorWidth)
                .clip(CircleShape)
                .background(NewPrimaryColor)
        )
    }
}

fun getIcon(route: String, selected: Boolean): ImageVector {
    return when (route) {
        "home" -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
        "favorites" -> if (selected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
        else -> Icons.Filled.Home
    }
}

fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    ComsposeSubmissionTheme {
        Surface {
            BottomNavBar(
                currentRoute = "home",
                onNavigate = {}
            )
        }
    }
}