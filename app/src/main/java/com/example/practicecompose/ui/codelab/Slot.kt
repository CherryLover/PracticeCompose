package com.example.practicecompose.ui.codelab

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicecompose.R

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/6 1:16 下午
 */

@Preview(showBackground = false)
@Composable
fun SlotPreview() {
    Column {
        AppBar()
        UseTopAppBar()
    }
}

@Composable
fun UseTopAppBar(
    menuIconArray: MutableList<Int> = mutableListOf(
        R.drawable.ic_baseline_search_24,
        R.drawable.ic_baseline_favorite_border_24,
        R.drawable.ic_baseline_more_vert_24,
        R.drawable.ic_baseline_near_me_24
    )
) {
    TopAppBar(title = {
        Text(text = "Home", fontSize = 18.sp)
    }, navigationIcon = {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = "navigation"
            )
        }
    }, actions = {
        menuIconArray.forEachIndexed { index, it ->
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        Log.d("Menu_AppBar", "click menu $it");
                    },
                painter = painterResource(id = it), contentDescription = "menu_$it",
                tint = Color.Unspecified
            )
            if (index == menuIconArray.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    })
}

@Composable
fun AppBar(
    appBarBackgroundColor: Color = MaterialTheme.colors.primary,
    navigationResId: Int = R.drawable.ic_baseline_menu_24,
    navigationClick: (() -> Unit)? = null,
    title: String = "Home",
    menuIconArray: MutableList<Int> = mutableListOf(
        R.drawable.ic_baseline_search_24,
        R.drawable.ic_baseline_favorite_border_24,
        R.drawable.ic_baseline_more_vert_24,
        R.drawable.ic_baseline_near_me_24
    ),
    maxMenuCount: Int = 3
) {
    val menus = mutableListOf<Int>()
    if (menuIconArray.size > maxMenuCount) {
        repeat(maxMenuCount) {
            menus.add(menuIconArray[it])
        }
    } else {
        menus.addAll(menuIconArray)
    }
    val iconSize: Dp = 24.dp
    val iconTintColor: Color = Color.White
    val titleTextColor: Color = Color.White
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(appBarBackgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    navigationClick?.invoke()
                },
            painter = painterResource(id = navigationResId), contentDescription = "navigate_menu",
            tint = iconTintColor
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = title, fontSize = 18.sp, color = titleTextColor)
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            menus.forEachIndexed { index, it ->
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .clickable {
                            Log.d("Menu_AppBar", "click menu $it");
                        },
                    painter = painterResource(id = it), contentDescription = "menu_$it",
                    tint = iconTintColor
                )
                if (index == menus.size - 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                } else {
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}