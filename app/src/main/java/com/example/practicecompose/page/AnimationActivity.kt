package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicecompose.ui.codelab.BasicLayoutScreen
import com.example.practicecompose.ui.navigation.NavigationButton
import com.example.practicecompose.ui.navigation.Router
import com.example.practicecompose.ui.theme.PracticeComposeTheme

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/22 9:18 下午
 */
class AnimationActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AnimationActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PracticeComposeTheme {
                NavHost(navController = navController, startDestination = Router.AnimationMain.router) {
                    composable(Router.AnimationMain.router) { AnimationMainScreen(navController = navController) }
                    composable(Router.AnimationTranslationX.router) { TranslationScreen() }
                }
            }
        }
    }
}

@Composable
fun TranslationScreen() {

}

@Composable
fun AnimationMainScreen(navController: NavController) {
    BasicLayoutScreen(name = "Animation Main") {
        NavigationButton(buttonText = "translationX", router = Router.AnimationTranslationX.router, navController = navController)
    }
}