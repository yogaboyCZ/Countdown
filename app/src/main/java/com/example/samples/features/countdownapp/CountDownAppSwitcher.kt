package com.example.samples.features.countdownapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.samples.features.countdownapp.countdowncircle.TimerViewModel
import com.example.samples.features.countdownapp.countdowncircle.CountdownTimer
import com.example.samples.features.countdownapp.flipper.Button
import com.example.samples.features.countdownapp.flipper.Flipper

@Composable
fun CountDownAppSwitcher(onDarkThemeToggled: () -> Unit, viewModel: TimerViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val typeOfCountDownTimer = remember { mutableStateOf(false) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.4f))
            if (typeOfCountDownTimer.value) {
                Flipper(
                    onDarkThemeToggled = onDarkThemeToggled,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                val times by viewModel.times.collectAsState()

                CountdownTimer(times = times, viewModel = viewModel)
            }

            Spacer(modifier = Modifier.weight(0.6f))
            val showDialog = remember { mutableStateOf(true) }

            Button(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(32.dp),
                onClick = {
                    typeOfCountDownTimer.value = !typeOfCountDownTimer.value
                }) {
                Text(
                    text = "Switch",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.align(alignment = Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}