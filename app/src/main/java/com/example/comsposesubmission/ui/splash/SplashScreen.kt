package com.example.comsposesubmission.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comsposesubmission.R
import com.example.comsposesubmission.ui.theme.GoldenHour
import com.example.comsposesubmission.ui.theme.Nunito
import com.example.comsposesubmission.ui.theme.TwilightBlue
import com.example.comsposesubmission.ui.theme.MutedSky
import com.example.comsposesubmission.ui.theme.PaperCream
import com.example.comsposesubmission.ui.theme.SunsetCoral
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val speed: Float,
    val phase: Float
)

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    // Animation values
    val logoScale = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val contentFadeOut = remember { Animatable(1f) }

    // Floating particles — tiny fireflies / dust motes
    val particles = remember {
        List(18) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 3f + 1.5f,
                speed = Random.nextFloat() * 0.4f + 0.2f,
                phase = Random.nextFloat() * 6.28f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    LaunchedEffect(Unit) {
        // Logo pops in with spring
        logoAlpha.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
    }

    LaunchedEffect(Unit) {
        logoScale.animateTo(
            1f,
            spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessLow)
        )
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(350)
        titleAlpha.animateTo(1f, tween(500, easing = FastOutSlowInEasing))
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(600)
        subtitleAlpha.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1800)
        contentFadeOut.animateTo(0f, tween(400, easing = FastOutSlowInEasing))
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = contentFadeOut.value }
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        TwilightBlue,
                        TwilightBlue.copy(alpha = 0.85f),
                        MutedSky.copy(alpha = 0.3f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Floating particles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            particles.forEach { p ->
                val px = p.x * w
                val py = p.y * h + sin((time + p.phase).toDouble()).toFloat() * 30f
                val particleAlpha = (sin((time * p.speed + p.phase).toDouble()).toFloat() + 1f) / 2f * 0.6f
                drawCircle(
                    color = GoldenHour.copy(alpha = particleAlpha * contentFadeOut.value),
                    radius = p.radius * density,
                    center = Offset(px, py)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo icon
            Icon(
                painter = painterResource(id = R.drawable.ic_ghibli),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .graphicsLayer {
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                    }
                    .alpha(logoAlpha.value),
                tint = PaperCream
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Title
            Text(
                text = "Studio Ghibli",
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = PaperCream,
                modifier = Modifier.alpha(titleAlpha.value)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle
            Text(
                text = "Gallery",
                fontFamily = Nunito,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = PaperCream.copy(alpha = 0.7f),
                letterSpacing = 3.sp,
                modifier = Modifier.alpha(subtitleAlpha.value)
            )
        }
    }
}
