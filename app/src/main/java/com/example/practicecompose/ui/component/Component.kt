package com.example.practicecompose.ui.component

/**
 * @description
 * @author: Created jiangjiwei in 2021/12/20 7:29 下午
 */

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp

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

//@Composable
//fun ScrollableTabRow(
//    selectedTabIndex: Int,
//    modifier: Modifier = Modifier,
//    backgroundColor: Color = MaterialTheme.colors.primarySurface,
//    contentColor: Color = contentColorFor(backgroundColor),
//    edgePadding: Dp = TabRowDefaults.ScrollableTabRowPadding,
//    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
//        TabRowDefaults.Indicator(
//            Modifier.CustomTabIndicator(tabPositions[selectedTabIndex])
//        )
//    },
//    divider: @Composable () -> Unit = @Composable {
//        TabRowDefaults.Divider()
//    },
//    tabs: @Composable () -> Unit
//)

