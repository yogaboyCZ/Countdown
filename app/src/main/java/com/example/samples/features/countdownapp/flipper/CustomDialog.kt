package com.example.samples.features.countdownapp.flipper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(showDialog: Boolean, onDismissRequest: () -> Unit) {
    val inShowDialog = remember { mutableStateOf(showDialog) }

    Button(onClick = { inShowDialog.value = true }) {
        Text("Show Custom Dialog")
    }

    if (inShowDialog.value) {
        val alpha = animateFloatAsState(
            targetValue = if (inShowDialog.value) 1f else 0f,
            animationSpec = TweenSpec(durationMillis = 300),
            label = ""
        ).value

        Dialog(onDismissRequest = { inShowDialog.value = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = alpha * 0.7f))
            ) {
                AnimatedVisibility(
                    visible = inShowDialog.value,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(32.dp)
                            .background(color = Color.White)
                    ) {
                        Text(
                            text = "This is a custom Jetpack Compose dialog.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomDialogPreview() {
    CustomDialog(
        showDialog = true,
        onDismissRequest = {}
    )
}
