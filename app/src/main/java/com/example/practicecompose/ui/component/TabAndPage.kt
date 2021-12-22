package com.example.practicecompose.ui.component

/**
 * @description Tabs 和 ViewPage
 * @author: Created jiangjiwei in 2021/12/20 7:29 下午
 */

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.practicecompose.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

@Composable
fun TextTabs(tabData: List<String> = listOf("Music", "Market", "Films", "Books")) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex) {
        tabData.forEachIndexed { idx, text ->
            Tab(selected = tabIndex == idx, onClick = { tabIndex = idx }, text = { Text(text = text) })
        }
    }
}

@Composable
fun ScrollTextTab(tabData: List<String> = listOf("Music", "Market", "Films", "Books", "Favorite", "PE", "History", "Art", "CS", "Math", "Chinese")) {
    var tabIndex by remember { mutableStateOf(0) }
    ScrollableTabRow(selectedTabIndex = tabIndex, edgePadding = 0.dp) {
        tabData.forEachIndexed { idx, text ->
            Tab(selected = tabIndex == idx, onClick = { tabIndex = idx }, text = { Text(text = text) })
        }
    }
}

@Composable
fun IconTabs(tabData: List<ImageVector> = listOf(Icons.Filled.Home, Icons.Filled.ShoppingCart, Icons.Filled.AccountBox, Icons.Filled.Settings)) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex) {
        tabData.forEachIndexed { idx, icon ->
            Tab(selected = tabIndex == idx, onClick = { tabIndex = idx }, icon = { Icon(imageVector = icon, contentDescription = "") })
        }
    }
}

@Composable
fun TextIconTabs(
    tabData: List<Pair<String, ImageVector>> = listOf(
        "Music" to Icons.Filled.Home,
        "Market" to Icons.Filled.ShoppingCart,
        "Films" to Icons.Filled.AccountBox,
        "Books" to Icons.Filled.Settings,
    )
) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex) {
        tabData.forEachIndexed { idx, data ->
            Tab(selected = tabIndex == idx,
                onClick = { tabIndex = idx },
                text = { Text(text = data.first) },
                icon = { Icon(imageVector = data.second, contentDescription = data.first) })
        }
    }
}

@Composable
fun CustomTabs(tabData: List<String> = listOf("Music", "Market", "Films", "Books", "Favorite"), indicatorColor: Color = Color.Green) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex, indicator = { tabPositions ->
        TabRowDefaults.Indicator(
            modifier = Modifier.CustomTabIndicator(tabPositions[tabIndex]),
            height = 5.dp,
            color = indicatorColor
        )
    }) {
        tabData.forEachIndexed { idx, text ->
            val selected = idx == tabIndex

            Tab(selected = selected, onClick = { tabIndex = idx }) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(color = if (selected) indicatorColor else Color.LightGray)
                    )
                    Text(
                        text = text, style = MaterialTheme.typography.body1,
                        color = if (selected) indicatorColor else Color.LightGray, modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun Modifier.CustomTabIndicator(currentTabPosition: TabPosition): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "CustomIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 32.dp
    val cTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + cTabWidth / 2F - indicatorWidth / 2F,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HPagers(
    texts: List<String> = listOf(
        "Overview",
        "Insets",
        "System UI Controller",
        "AppCompat Theme",
        "Pager layouts",
        "Swipe Refresh",
        "Placeholder",
        "DrawablePainter",
        "Flow layouts",
        "Permissions",
        "Navigation Animation",
        "Navigation Material"
    )
) {
    HorizontalPager(
        count = texts.size, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) { pagePosition ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.LightGray), contentAlignment = Alignment.Center
        ) {
            Text(text = texts[pagePosition])
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HPagerFeatures(
    texts: List<Int> = listOf(
        R.mipmap.avatar_girl_01,
        R.mipmap.avatar_girl_02,
        R.mipmap.avatar_girl_03,
        R.mipmap.avatar_girl_04,
        R.mipmap.avatar_girl_05,
        R.mipmap.avatar_girl_06,
        R.mipmap.avatar_girl_07,
    )
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val timer = Timer()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (pager, indicator) = createRefs()
        val (btnPre, btnNext) = createRefs()
        HorizontalPager(count = texts.size, state = pagerState, modifier = Modifier.constrainAs(pager) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) { position ->
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(position).absoluteValue
                        lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray), contentAlignment = Alignment.Center
                ) {
                    Image(modifier = Modifier.fillMaxSize(),painter = painterResource(id = texts[position]), contentDescription = "Avatar", contentScale = ContentScale.Crop)
                }
            }
        }
        HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier.constrainAs(indicator) {
            start.linkTo(pager.start)
            end.linkTo(pager.end)
            bottom.linkTo(pager.bottom, 8.dp)
        })

        Button(modifier = Modifier.constrainAs(btnPre) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }, onClick = {
            coroutineScope.launch {
                if (pagerState.currentPage == 0) {
                    // 0 1 2 3 4 到最后一个，所以不能加动画
                    pagerState.scrollToPage(texts.size - 1)
                } else {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }
        }) {
            Text(text = "上一个")
        }
        Button(modifier = Modifier.constrainAs(btnNext) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }, onClick = {
            coroutineScope.launch {
                if (pagerState.currentPage == texts.size - 1) {
                    pagerState.scrollToPage(0)
                } else {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }) {
            Text(text = "下一个")
        }
    }
    timer.schedule(object : TimerTask() {
        override fun run() {
            Log.d("HPager Loop", "cPosition ${pagerState.currentPage}");
            val nextPosition = if (pagerState.currentPage == texts.size - 1) 0 else pagerState.currentPage + 1
            coroutineScope.launch {
                if (nextPosition == 0) {
                    pagerState.scrollToPage(nextPosition)
                } else {
                    pagerState.animateScrollToPage(nextPosition)
                }
            }
        }
    }, 5000, 3000)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VPagers(
    texts: List<String> = listOf(
        "Overview",
        "Insets",
        "System UI Controller",
        "AppCompat Theme",
        "Pager layouts",
        "Swipe Refresh",
        "Placeholder",
        "DrawablePainter",
        "Flow layouts",
        "Permissions",
        "Navigation Animation",
        "Navigation Material"
    ),
    avatars: List<Int> = listOf(
        R.mipmap.avatar_girl_01,
        R.mipmap.avatar_girl_02,
        R.mipmap.avatar_girl_03,
        R.mipmap.avatar_girl_04,
        R.mipmap.avatar_girl_05,
        R.mipmap.avatar_girl_06,
        R.mipmap.avatar_girl_07,
    )
) {
    VerticalPager(
        count = texts.size, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) { position ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray), contentAlignment = Alignment.Center
        ) {
            Box {
                HorizontalPager(count = avatars.size) { innerPosition ->
                    Image(painter = painterResource(id = avatars[innerPosition]), contentDescription = "Avatar", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                }
            }
        }
    }
}
