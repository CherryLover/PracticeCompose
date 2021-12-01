package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicecompose.R
import com.example.practicecompose.ui.codelab.*
import com.example.practicecompose.ui.navigation.Router
import com.example.practicecompose.ui.theme.PracticeComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationMainScreen(navController: NavController) {
    BasicLayoutScreen(name = "Animation Main") {
        var showEditText by remember { mutableStateOf(true) }
        var showNoticeText by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var showWeatherLoading by remember { mutableStateOf(false) }

        val topicList = stringArrayResource(id = R.array.topics).toList()
        val taskList = stringArrayResource(id = R.array.tasks)

        val tasks = remember { mutableStateListOf(*taskList) }

        suspend fun showNoticeDelayDismiss() {
            if (!showNoticeText) {
                showNoticeText = true
                delay(3000L)
                showNoticeText = false
            }
        }

        suspend fun fakeRequestNetFetchData() {
            if (!showWeatherLoading) {
                showWeatherLoading = true
                delay(3000L)
                showWeatherLoading = false
            }
        }



        Scaffold(
            topBar = {
                PageTab()
            },
            floatingActionButton = {
                EditWithIcon(showEditText) { coroutineScope.launch { showNoticeDelayDismiss() } }
            },
        ) {
            Column(modifier = Modifier.background(Green300)) {

                Spacer(modifier = Modifier.height(12.dp))
                LoadingWeather(modifier = Modifier.padding(horizontal = 16.dp), showWeatherLoading) { coroutineScope.launch { fakeRequestNetFetchData() } }


                Spacer(modifier = Modifier.height(12.dp))
                topicList.forEach { topic ->
                    TopicItem(topic = topic)
                }
                if (tasks.isEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { tasks.addAll(taskList) }, modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(text = "添加 TODO")
                    }
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn() {
                        items(count = tasks.size) { position->
                            val get = tasks.getOrNull(position)
                            if (get != null) {
                                key(get) {
                                    TaskItem(task = get, modifier = Modifier.padding(horizontal = 16.dp)) {
                                        tasks.remove(get)
                                    }
                                }
                            }
                        }
                    }
                }

                Button(onClick = { showEditText = !showEditText }, modifier = Modifier.margin(top = 12.dp)) {
                    Text(text = "Toggle Edit Text Show")
                }
            }
            NoticeLayout(showNoticeText)
        }
    }
}