package com.example.practicecompose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:30 下午
 */
@Composable
fun MainScreen(navController: NavController) {
    BasicScreen(Router.Companion.Main.router, {
        navController.navigate(Router.Companion.Feed.router)
    })
}
@Composable
fun FeedScreen(navController: NavController) {
    BasicScreen(Router.Companion.Feed.router, {
        navController.navigate(Router.Companion.Profile.router)
    }, {
        navController.popBackStack()
    })
}
@Composable
fun ProfileScreen(navController: NavController) {
    BasicScreen(Router.Companion.Profile.router, popUpClick = {
        navController.popBackStack()
    })
}

@Composable
fun BasicScreen(screenName: String = "Name", toNextClick: (() -> Unit)? = null, popUpClick: (() -> Unit)? = null) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = screenName, fontSize = MaterialTheme.typography.h3.fontSize)
        if (toNextClick != null) {
            Button(onClick = {
                toNextClick.invoke()
            }) {
                Text(text = "ToNext")
            }
        }
        if (popUpClick != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                popUpClick.invoke()
            }) {
                Text(text = "PopUp")
            }
        }
    }
}

@Preview
@Composable
fun ScreenPreview() {
    BasicScreen()
}