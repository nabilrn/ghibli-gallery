package com.example.comsposesubmission.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comsposesubmission.R
import com.example.comsposesubmission.ui.theme.ComsposeSubmissionTheme
import com.example.comsposesubmission.ui.theme.NewPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GhibliTopBar(
    title: String = "Studio Ghibli Movies",
    onAccountClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = NewPrimaryColor,
        shadowElevation = 4.dp
    ) {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Logo
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ghibli),
                        contentDescription = "Ghibli Logo",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )

                    // Title with custom style
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color.White
                    )
                }
            },
            actions = {
                // Account button with ripple effect
                IconButton(
                    onClick = onAccountClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "Account",
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .padding(2.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = NewPrimaryColor.copy(alpha = 0.95f)
            ),
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GhibliTopBarPreview() {
    ComsposeSubmissionTheme {
        Column {
            GhibliTopBar(
                onAccountClick = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GhibliTopBarDarkPreview() {
    ComsposeSubmissionTheme {
        Column {
            GhibliTopBar(
                onAccountClick = {}
            )
        }
    }
}