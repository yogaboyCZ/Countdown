package com.example.samples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.samples.ui.theme.SystemUiController
import com.example.samples.features.countdownapp.CountDownAppSwitcher
import com.example.samples.features.countdownapp.countdowncircle.TimerViewModel
import com.example.samples.features.BlurBackgroud.BlurBackgroudScreen
import com.example.samples.ui.theme.CountdownTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabItems = listOf(
            TabItem("Countdown"),
            TabItem("Timer"),
            TabItem("Stopwatch"),
        )
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            val pagerState = rememberPagerState {
                tabItems.size
            }
            LaunchedEffect(key1 = selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(
                key1 = pagerState.currentPage,
                key2 = pagerState.isScrollInProgress
            ) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
            CountdownTheme {
                // A surface container using the 'background' color from the theme
                systemUiController.setSystemBarsColor(color = Color.White)
                val viewModel by viewModels<TimerViewModel>()
                Column(modifier = Modifier.fillMaxSize()) {
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        tabItems.forEachIndexed { index, tabItem ->
                            Tab(
                                text = {
                                    Text(
                                        text = tabItem.title,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index }
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { index ->
                        when (index) {
                            0 -> {
                                var darkTheme by remember { mutableStateOf(false) }
                                CountDownAppSwitcher(onDarkThemeToggled = {
                                    darkTheme = !darkTheme
                                }, viewModel = viewModel)
                            }

                            1 -> {
                                BlurBackgroudScreen()
                            }

                            2 -> {
                                Text(text = tabItems[index].title)
                            }
                        }
                    }

                }

//                FullSizeBlur {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .height(LocalConfiguration.current.screenHeightDp.dp)
//                    ) {
//                        // Code that needs to have the blur background goes here ...
//                        // You can even set an alpha value on your code here so that
//                        // the blur is visible through this composable
//                        Card(modifier = Modifier.alpha(0.6F)) {
//                            // Card content here ...
//
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(Brush.radialGradient(listOf(grey, Color.White))),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Column {
//                                    var darkTheme by remember { mutableStateOf(false) }
//                                    CountdownTheme(darkTheme = darkTheme) {
//                                        CountDownAppSwitcher(onDarkThemeToggled = {
//                                            darkTheme = !darkTheme
//                                        }, viewModel = viewModel)
//                                    }
//
//                                }
//                            }
//                        }
//                    }
            }
        }
    }

    data class TabItem(val title: String)

}


