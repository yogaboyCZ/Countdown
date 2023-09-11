package com.example.samples.features.countdownapp.countdowncircle

import androidx.compose.runtime.Composable
import com.example.samples.features.countdownapp.flipper.Chrono
import com.example.samples.features.countdownapp.flipper.Controllers

@Composable
fun CountdownTimer(
    times: Int,
    viewModel: TimerViewModel
) {
    Chrono(times)
    Controllers(
        onPause = { viewModel.pause() },
        onStart = { viewModel.start() },
        onStop = { viewModel.stop() }
    )
}
