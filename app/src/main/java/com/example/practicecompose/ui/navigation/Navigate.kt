package com.example.practicecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:17 下午
 */
@Composable
fun NavigationEntrance() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Router.Companion.Main.router) {
        composable(Router.Companion.Main.router) {
            MainScreen(navController = navController)
        }
        composable(Router.Companion.Feed.router) {
            FeedScreen(navController = navController)
        }
        composable(Router.Companion.Profile.router) {
            ProfileScreen(navController = navController)
        }
    }
}
