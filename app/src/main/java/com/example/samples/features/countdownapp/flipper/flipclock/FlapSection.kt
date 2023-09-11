package com.example.samples.features.countdownapp.flipper.flipclock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FlapSection(
    title: String,
    currentValue: Int,
    nextValue: Int,
    factor: Float,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Flaps(
            currentText = currentValue.toString().padStart(2, '0'),
            nextText = nextValue.toString().padStart(2, '0'),
            factor = factor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            IconButton(onClick = onDecrement) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "$title decrement button")
            }

            IconButton(onClick = onIncrement) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "$title increment button")
            }
        }
    }
}
