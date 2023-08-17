package com.example.countdown.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.countdown.ui.theme.green
import com.example.countdown.ui.theme.greenDark
import com.example.countdown.ui.theme.purpleDark
import com.example.countdown.ui.theme.purpleLightest

@Composable
fun Controllers(
    modifier: Modifier = Modifier,
    onPause: () -> Unit,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Pause(onPause = onPause)
        Start(onStart = onStart)
        Stop(onStop = onStop)
    }
}

@Composable
internal fun Stop(onStop: () -> Unit) {
    Button(
        color = purpleLightest,
        onClick = onStop,

        ) {
        Icon(
            imageVector = Icons.Rounded.Stop,
            contentDescription = "Stop",
            tint = purpleDark,
            modifier = Modifier
                .size(45.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Start(onStart: () -> Unit) {
    Button(
        color = green,
        onClick = onStart
    ) {
        Text(
            text = "START",
            style = MaterialTheme.typography.labelLarge,
            color = greenDark,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Pause(onPause: () -> Unit) {
    Button(
        color = purpleLightest,
        onClick = onPause
    ) {
        Icon(
            imageVector = Icons.Rounded.Pause,
            contentDescription = "Pause",
            tint = purpleDark,
            modifier = Modifier
                .size(35.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Button(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()
    val colorAnimated by animateColorAsState(
        targetValue = if (isPressed) color else Color.White,
        animationSpec = tween(400), label = ""
    )
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 3.dp else 8.dp,
        animationSpec = tween(400), label = ""
    )
    val brush = Brush.verticalGradient(listOf(color, colorAnimated))
    Box(
        modifier
            .shadow(elevation = elevation, shape = CircleShape)
            .border(width = 5.dp, color = Color.White, shape = CircleShape)
            .background(brush = brush, shape = CircleShape)
            .clip(CircleShape)
            .width(100.dp)
            .aspectRatio(1f)
            .clickable(
                interactionSource = interaction,
                indication = rememberRipple(
                    bounded = true,
                    radius = 250.dp,
                    color = Color.Green
                ),
                onClick = onClick
            )
//            .bouncingClickable {
//                println("Clicked...")
//            }
        ,
        content = content
    )
}

/**
 * Adds a bouncing click effect to a Composable.
 *
 * @param enabled whether the UI element should be enabled and clickable
 * @param onClick the action to perform when the UI element is clicked
 */
fun Modifier.bouncingClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animationTransition = updateTransition(isPressed, label = "BouncingClickableTransition")

    val scaleFactor by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.84f else 1f },
        label = "BouncingClickableScaleFactorTransition",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        }
    )

    val opacity by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.7f else 1f },
        label = "BouncingClickableOpacityTransition"
    )

    this
        .graphicsLayer {
            this.scaleX = scaleFactor
            this.scaleY = scaleFactor
            this.alpha = opacity
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}
