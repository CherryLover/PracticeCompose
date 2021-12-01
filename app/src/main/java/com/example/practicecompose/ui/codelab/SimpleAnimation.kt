package com.example.practicecompose.ui.codelab

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/23 10:07 上午
 */
private enum class TabPage {
    Home, Account
}

val Purple100 = Color(0xFFE1BEE7)
val Green300 = Color(0xFF81C784)
val Purple700 = Color(0xFF3700B3)
val Green800 = Color(0xFF2E7D32)

@Composable
fun PageTab() {
    var tabPage by remember { mutableStateOf(TabPage.Home) }
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300, tween(500, easing = LinearEasing))
    val borderColor = if (tabPage == TabPage.Home) Purple700 else Green800

    val transition = updateTransition(tabPage, label = "Tab indicator")


    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        indicator = {
            val indicatorLeft by transition.animateDp(label = "indicatorLeft", transitionSpec = {
                if (TabPage.Home isTransitioningTo TabPage.Account) {
                    spring(stiffness = Spring.StiffnessVeryLow)
                } else {
                    spring(stiffness = Spring.StiffnessMedium)
                }
            }) { tab ->
                it[tab.ordinal].left
            }
            val indicatorRight by transition.animateDp(label = "indicatorRight", transitionSpec = {
                if (TabPage.Home isTransitioningTo TabPage.Account) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            }) { tab ->
                it[tab.ordinal].right
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.BottomStart)
//                    .offset(it[tabPage.ordinal].left)
                    .offset(indicatorLeft)
//                    .width(it[tabPage.ordinal].right - it[tabPage.ordinal].left)
                    .width(indicatorRight - indicatorLeft)
                    .padding(4.dp)
                    .fillMaxSize()
                    .border(BorderStroke(2.dp, borderColor), RoundedCornerShape(4.dp))
            )
        }) {
        TabItemWithIcon(
            modifier = Modifier.fillMaxHeight(),
            Icons.Default.Home, "Home"
        ) {
            tabPage = TabPage.Home
        }
        TabItemWithIcon(
            modifier = Modifier.fillMaxHeight(),
            Icons.Default.AccountBox, "Account"
        ) {
            tabPage = TabPage.Account
        }
    }
}

@Composable
fun TabItemWithIcon(
    modifier: Modifier,
    icon: ImageVector = Icons.Default.Home,
    text: String = "Home",
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, text)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text)
    }
}

@ExperimentalAnimationApi
@Composable
fun EditWithIcon(showText: Boolean = true, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable { onClick() }
            .height(48.dp)
            .background(MaterialTheme.colors.primary, RoundedCornerShape(50))
    ) {
        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White,modifier = Modifier
            .padding(horizontal = 12.dp)
            .size(24.dp))
        AnimatedVisibility(visible = showText) {
//            Row {
                Text(text = "Edit", color = Color.White, modifier = Modifier.padding(end = 14.dp))
//                Text(text = "Copy Edit Text")
//            }
        }
        // 使用 if 判断 和 AnimatedVisibility 需要通过不同的设置方式来达到同样的效果
        // 原因是 AnimatedVisibility 的 scope 只能单个 Item，若多个，需要通过 Layout 进行包裹
//        if (showText) {
//            Text(text = "Edit")
//            Spacer(modifier = Modifier.width(14.dp))
//        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NoticeLayout(show: Boolean) {
    AnimatedVisibility(
        visible = show, enter = slideInVertically(initialOffsetY = { fullHeight ->
            -fullHeight
        }, animationSpec = tween(150, easing = LinearEasing)),
        exit = slideOutVertically(targetOffsetY = { fullHeight ->
            -fullHeight
        }, animationSpec = tween(150, easing = LinearEasing))
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            color = MaterialTheme.colors.secondary
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Edit is not support", Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
fun TopicItem(
    topic: String = "3 new packages arrived",
    content: String = "ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
    onClick: (() -> Unit)? = null
) {
    var showContent by remember { mutableStateOf(false) }
    Column {
        if (showContent) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        Surface(
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable {
                    showContent = !showContent
                    onClick?.invoke()
                }
                .animateContentSize(),
            elevation = 5.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(48.dp)) {
                    Icon(Icons.Default.Info, contentDescription = "Info")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = topic,
                        style = MaterialTheme.typography.body1
                    )
                }
                if (showContent) {
                    Row {
                        Text(text = content)
                    }
                }
            }
        }
        if (showContent) {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LoadingWeather(modifier: Modifier = Modifier, loading: Boolean = true, refresh: (() -> Unit)? = null) {
    Row(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        if (loading) {
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(initialValue = 0.7F, targetValue = 1F, animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                }, repeatMode = RepeatMode.Reverse))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .clip(RoundedCornerShape(0))
                        .background(Color.LightGray.copy(alpha))
                )
            }
        } else {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFB300))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "18 ℃", fontSize = 24.sp, modifier = Modifier.height(36.dp))
                Spacer(modifier = Modifier.weight(1F))
                IconButton(onClick = { refresh?.invoke() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
                }
            }
        }
    }
}

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: String = "Buy some milk", onDismissed: () -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .swipeToDismiss(onDismissed)
    ) {
        Row(
            Modifier
                .height(36.dp)
                .background(Color.White), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

private fun Modifier.swipeToDismiss(onDismissed: () -> Unit): Modifier = composed {
    var offset = remember { Animatable(0F) }
    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)
        coroutineScope {
            while (true) {
                val pointId = awaitPointerEventScope { awaitFirstDown().id }
                offset.stop()
                val velocityTracker = VelocityTracker()
                awaitPointerEventScope {
                    horizontalDrag(pointerId = pointId) { change ->
                        val hDragOffset = offset.value + change.positionChange().x
                        launch {
                            offset.snapTo(hDragOffset)
                        }
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        change.consumePositionChange()
                    }
                }
                val velocity = velocityTracker.calculateVelocity().x
                val targetOffset = decay.calculateTargetValue(offset.value, velocity)
                offset.updateBounds(lowerBound = -size.width.toFloat(), upperBound = size.width.toFloat())
                launch {
                    if (targetOffset.absoluteValue <= size.width) {
                        offset.animateTo(targetValue = 0F, initialVelocity = velocity)
                    } else {
                        offset.animateDecay(velocity, decay)
                        onDismissed()
                    }
                }
            }
        }
    }.offset { IntOffset(offset.value.roundToInt(), 0) }
}

@Preview
@Composable
fun LoadingWeatherPreview() {
    LoadingWeather(loading = true) { }
}

@Preview
@Composable
fun TopicItemPreview() {
    TopicItem()
}

@Preview
@Composable
fun ColorChangeAnimationPreview() {
    PageTab()
}

@ExperimentalAnimationApi
@Preview
@Composable
fun EditWithIconPreview() {
    EditWithIcon() { }
}