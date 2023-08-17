package com.example.countdown.components

import android.os.SystemClock
import android.text.format.DateUtils
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countdown.ui.flipclock.FlipClock
import com.example.countdown.ui.flipclock.FlipClockEvents
import com.example.countdown.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun Flipper(onDarkThemeToggled: () -> Unit, modifier: Modifier = Modifier) {
    var endTime by remember { mutableStateOf(SystemClock.uptimeMillis()) }
    var remainingSeconds by remember { mutableStateOf(0) }

    fun updateRemainingTime() {
        remainingSeconds = ceil(max(endTime - SystemClock.uptimeMillis(), 0L).toFloat() / 1000F).toInt()
    }

    fun addTime(millis: Long) {
        endTime = max(endTime, SystemClock.uptimeMillis()) + millis
        updateRemainingTime()
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main) {
            while (true) {
                updateRemainingTime()
                delay(100L)
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(48.dp))

        FlipClock(
            seconds = remainingSeconds,
            endMillis = endTime,
            events = FlipClockEvents(
                onHoursIncrement = { addTime(DateUtils.HOUR_IN_MILLIS) },
                onHoursDecrement = { addTime(-DateUtils.HOUR_IN_MILLIS) },
                onMinutesIncrement = { addTime(DateUtils.MINUTE_IN_MILLIS) },
                onMinutesDecrement = { addTime(-DateUtils.MINUTE_IN_MILLIS) },
                onSecondsIncrement = { addTime(DateUtils.SECOND_IN_MILLIS) },
                onSecondsDecrement = { addTime(-DateUtils.SECOND_IN_MILLIS) }
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        IconButton(onClick = onDarkThemeToggled) {
            Icon(
                imageVector = if (isSystemInDarkTheme()) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = "Dark theme button"
            )
        }
    }
}
