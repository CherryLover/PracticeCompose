package com.example.practicecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlin.random.Random

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:17 下午
 */
@Composable
fun NavigationEntrance() {
    val count = Random.nextInt(50, 100)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Router.Companion.Main.finalRouter()) {
        composable(Router.Companion.Main.finalRouter()) {
            MainScreen(navController = navController, count)
        }
        composable(
            Router.Companion.Feed.finalRouter(),
            arguments = listOf(navArgument(Router.Companion.Feed.KEY_NECESSARY_COUNT) {
                type = NavType.IntType
            })
        ) { it ->
            FeedScreen(navController = navController, it.arguments?.getInt(Router.Companion.Feed.KEY_NECESSARY_COUNT) ?: -1)
        }
        composable(Router.Companion.Profile.finalRouter(),
            arguments = listOf(navArgument(Router.Companion.Profile.KEY_OPTIONAL_COUNT) {
                type = NavType.IntType
                defaultValue = 1024
            })
        ) { it ->
            ProfileScreen(
                navController = navController,
                it.arguments?.getInt(Router.Companion.Profile.KEY_OPTIONAL_COUNT) ?: -2
            )
        }
        composable(Router.NewsContainer.finalRouter()) {
            NewsContainerScreen()
        }
    }
}
