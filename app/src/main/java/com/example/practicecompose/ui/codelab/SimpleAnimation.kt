package com.example.practicecompose.ui.codelab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
fun ColorChangeAnimation() {
    var tabPage by remember { mutableStateOf(TabPage.Home) }
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300, tween(500, easing = LinearEasing))
    val borderColor = if (tabPage == TabPage.Home) Purple700 else Green800
    TabRow(selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        indicator = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(it[tabPage.ordinal].left)
                    .width(it[tabPage.ordinal].right - it[tabPage.ordinal].left)
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

@Preview
@Composable
fun ColorChangeAnimationPreview() {
    ColorChangeAnimation()
}