package com.example.samples.features.BlurBackgroud

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.samples.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlurBackgroudScreen() {
    // https://www.youtube.com/watch?v=WixQtSjpqhg&t=951s
    val targetBlur = 100.dp
    val animatedBlur by animateDpAsState(targetBlur, TweenSpec(500))
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
//            .blur(animatedBlur)
//        topBar = {
//            TopAppBar(title = { Text(text = "Blur Backgroud") })
//        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .alpha(1f)
                .blur(animatedBlur)

        ) {
            Image(
                painter = painterResource(id = R.drawable.stock),
                contentDescription = ""
            )

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            Text(
                text = "Hello World",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(1f)
                    .align(alignment = androidx.compose.ui.Alignment.Center)
            )

        }

    }

}