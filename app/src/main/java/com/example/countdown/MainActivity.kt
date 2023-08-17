package com.example.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countdown.components.Flipper
import com.example.countdown.components.Chrono
import com.example.countdown.components.Controllers
import com.example.androiddevchallenge.ui.theme.SystemUiController
import com.example.countdown.components.Button
import com.example.countdown.ui.theme.CountdownTheme
import com.example.countdown.ui.theme.greenDark
import com.example.countdown.ui.theme.grey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CountdownTheme {
                // A surface container using the 'background' color from the theme
                systemUiController.setSystemBarsColor(color = Color.White)
                FullSizeBlur {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(LocalConfiguration.current.screenHeightDp.dp)
                    ) {
                        // Code that needs to have the blur background goes here ...
                        // You can even set an alpha value on your code here so that
                        // the blur is visible through this composable
                        Card(modifier = Modifier.alpha(0.6F)) {
                            // Card content here ...

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.radialGradient(listOf(grey, Color.White))),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    var darkTheme by remember { mutableStateOf(false) }
                                    CountdownTheme(darkTheme = darkTheme) {
                                        MyApp(onDarkThemeToggled = { darkTheme = !darkTheme })
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun MyApp(onDarkThemeToggled: () -> Unit) {
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
                    val viewModel by viewModels<TimerViewModel>()
                    val times by viewModel.times.collectAsState()

                    CountdownTimer(times = times, viewModel = viewModel)
                }

                Spacer(modifier = Modifier.weight(0.6f))
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
}

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
