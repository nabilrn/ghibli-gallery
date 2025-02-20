package com.example.comsposesubmission.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comsposesubmission.R

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    val profileName by viewModel.profileName.collectAsState()
    val profileTitle by viewModel.profileTitle.collectAsState()
    val emailAddress by viewModel.emailAddress.collectAsState()
    val githubUrl by viewModel.githubUrl.collectAsState()
    val linkedInUrl by viewModel.linkedInUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = profileName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = profileTitle,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Social Links",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.gmail),
                contentDescription = "Email",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { uriHandler.openUri(emailAddress) }
                    .padding(4.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(32.dp))

            Icon(
                painter = painterResource(id = R.drawable.github),
                contentDescription = "GitHub",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { uriHandler.openUri(githubUrl) }
                    .padding(4.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(32.dp))

            Icon(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "LinkedIn",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { uriHandler.openUri(linkedInUrl) }
                    .padding(4.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MaterialTheme {
        Surface {
            AboutScreen(onBackClick = {})
        }
    }
}