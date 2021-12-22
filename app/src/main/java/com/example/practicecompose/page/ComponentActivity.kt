package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicecompose.ui.navigation.PagerScreen
import com.example.practicecompose.ui.navigation.Router
import com.example.practicecompose.ui.navigation.TabLayoutScreen
import com.example.practicecompose.ui.navigation.TabPagerScreen

/**
 * @description Compose Component 的实验场，后续可继续新增更多组件的测试
 * @author: Created jiangjiwei in 2021/12/20 8:49 下午
 */
class ComponentActivity: AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ComponentActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Router.ComMain.finalRouter()) {
                composable(Router.ComMain.finalRouter()) {
                    EntranceScreen(names = listOf("TabRow", "Pager", "PagerWithTab"), onClickInvoke = listOf({
                        navController.navigate(Router.ComTab.finalRouter())
                    }, {
                        navController.navigate(Router.ComPager.finalRouter())
                    }, {
                        navController.navigate(Router.ComTabPager.finalRouter())
                    }))
                }
                composable(Router.ComTab.finalRouter()) { TabLayoutScreen() }
                composable(Router.ComPager.finalRouter()) { PagerScreen() }
                composable(Router.ComTabPager.finalRouter()) { TabPagerScreen() }
            }

        }
    }
}

@Composable
fun EntranceScreen(names: List<String>, onClickInvoke: List<() -> Unit>) {
    if (names.isEmpty()) {
        return
    }
    if (names.size != onClickInvoke.size) {
        return
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        names.forEachIndexed { idx, txt ->
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onClickInvoke[idx].invoke() }) { Text(text = txt) }
        }
    }
}