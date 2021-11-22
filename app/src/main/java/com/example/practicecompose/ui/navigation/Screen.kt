package com.example.practicecompose.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.random.Random

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:30 下午
 */
@Composable
fun MainScreen(navController: NavController, randomNumber: Int = 0) {
    val randomText= "Random Int：$randomNumber"
    BasicScreen(Router.Companion.Main.router, {
        navController.navigate(Router.Companion.Feed.argRouter(randomNumber))
    }, showArgs = randomText)
}
@Composable
fun FeedScreen(navController: NavController, necessaryCount: Int) {
    val passCount = Random.nextBoolean()
    BasicScreen(Router.Companion.Feed.router, {
        if (passCount) {
            navController.navigate(Router.Companion.Profile.argRouter(necessaryCount))
        } else {
            navController.navigate(Router.Companion.Profile.router)
        }
    }, {
        navController.popBackStack()
    }, showArgs = "Necessary Arg：$necessaryCount \npass it? $passCount")
}

@Composable
fun ProfileScreen(navController: NavController, optionalCount: Int = 0) {
    BasicScreen(Router.Companion.Profile.router, toNextClick = {
        navController.navigate(Router.NewsContainer.finalRouter())
    }, popUpClick = {
        navController.popBackStack()
    }, showArgs = "Optional Arg：$optionalCount")
}

@Composable
fun NewsContainerScreen() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation {
                val cNavBackStackEntry by navController.currentBackStackEntryAsState()
                val cDestination = cNavBackStackEntry?.destination
                NewsContainerBottomNavigateItem.list.forEach { itemBean ->
                    BottomNavigationItem(
                        icon = { Icon(painter = painterResource(id = itemBean.normalIconId), contentDescription = itemBean.name) },
                        label = { Text(text = itemBean.name) },
                        selected = cDestination?.hierarchy?.any { it.route == itemBean.router } == true,
                        onClick = {
                            navController.navigate(itemBean.router) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                // 若不设置 saveState restore State 则 Screen 内的状态不会保留，若不设置 launchSingleTop 则可能会打开多个 Screen
                            }
                        })
                }
            }

        }) {
        NavHost(navController = navController, startDestination = Router.News.finalRouter()) {
            composable(Router.News.finalRouter()) {
                NewsScreen()
            }
            composable(Router.Explore.finalRouter()) {
                ExploreScreen()
            }
            composable(Router.Favorite.finalRouter()) {
                FavoriteScreen()
            }
        }
    }
}

@Composable
fun NewsScreen() {
    TextScreen("News")
}

@Composable
fun ExploreScreen() {
    TextScreen("Explore")
}

@Composable
fun FavoriteScreen() {
    TextScreen("Favorite")
}

@Composable
fun TextScreen(
    screenName: String = "Name",
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = screenName, fontSize = MaterialTheme.typography.h3.fontSize)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun BasicScreen(
    screenName: String = "Name",
    toNextClick: (() -> Unit)? = null,
    popUpClick: (() -> Unit)? = null,
    showArgs: String = ""
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = screenName, fontSize = MaterialTheme.typography.h3.fontSize)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = showArgs, fontSize = MaterialTheme.typography.h5.fontSize, textAlign = TextAlign.Center)
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

@Composable
fun NavigationButton(buttonText: String, router: String, navController: NavController, modifier: Modifier = Modifier) {
    Button(onClick = {
        navController.navigate(route = router)
    }, modifier = modifier) {
        Text(text = buttonText)
    }
}

@Preview
@Composable
fun ScreenPreview() {
    BasicScreen()
}