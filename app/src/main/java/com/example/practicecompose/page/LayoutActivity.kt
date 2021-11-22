package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicecompose.R
import com.example.practicecompose.ui.codelab.*
import com.example.practicecompose.ui.navigation.NavigationButton
import com.example.practicecompose.ui.navigation.Router
import com.example.practicecompose.ui.theme.PracticeComposeTheme

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/1 6:38 下午
 */
class LayoutActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LayoutActivity"

        fun start(context: Context) {
            val stater = Intent(context, LayoutActivity::class.java)
            context.startActivity(stater)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PracticeComposeTheme {
                NavHost(navController = navController, startDestination = Router.LayoutMain.router) {
                    composable(Router.LayoutMain.router) {
                        LayoutMainScreen(navController = navController)
                    }
                    composable(Router.LayoutUserInfo.router) {
                        UserInfoScreen()
                    }
                    composable(Router.LayoutCheckedButtonWithIcon.router) {
                        CheckedButtonWithIconScreen()
                    }
                    composable(Router.LayoutSlot.router) {
                        CustomSlotScreen()
                    }
                    composable(Router.LayoutList.router) {
                        ListScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ListScreen() {
    BasicLayoutScreen("List Part") {
        HorizontalPureList()
        Row {
            VerticalPureList()
            VerticalLazyList()
        }
    }
}
@Composable
fun CustomSlotScreen() {
    BasicLayoutScreen("CheckedButtonWithIcon") {
        Text(text = "自己通过 Row Image 完成对 AppBar 的绘制", fontSize = MaterialTheme.typography.h6.fontSize)
        AppBar()
        Text(text = "使用系统的 TopAppBar", fontSize = MaterialTheme.typography.h6.fontSize)
        UseTopAppBar()
        Text(text = "使用系统的 Scaffold 完成 Material Design 的实现", fontSize = MaterialTheme.typography.h6.fontSize)
        ScaffoldText()
    }
}
@Composable
fun CheckedButtonWithIconScreen() {
    BasicLayoutScreen("CheckedButtonWithIcon") {
        ButtonWithIcon(
            R.drawable.ic_baseline_near_me_24,
            R.drawable.ic_baseline_near_me_disabled_24,
            text = "Location"
        )
    }
}
@Composable
fun UserInfoScreen() {
    BasicLayoutScreen("UserInfo") {
        UserInfoCard()
    }
}

@Composable
fun LayoutMainScreen(navController: NavController) {
    BasicLayoutScreen(name = "Summary") {
        NavigationButton("UserInfo", Router.LayoutUserInfo.router, navController)
        Spacer(modifier = Modifier.height(10.dp))
        NavigationButton("CheckedButtonWithIcon", Router.LayoutCheckedButtonWithIcon.router, navController)
        Spacer(modifier = Modifier.height(10.dp))
        NavigationButton("FakeSlot", Router.LayoutSlot.router, navController)
        Spacer(modifier = Modifier.height(10.dp))
        NavigationButton("List Part", Router.LayoutList.router, navController)
        Spacer(modifier = Modifier.height(10.dp))
        ImageListItem()
        Spacer(modifier = Modifier.height(10.dp))
        CustomModifierToMargin()
        CustomCol()
        CustomRow()
        val topics = listOf(
            "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
            "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
            "Religion", "Social sciences", "Technology", "TV", "Writing"
        )
        val scrollState = rememberScrollState()
        StaggeredGridLayout(modifier = Modifier.horizontalScroll(scrollState)) {
            topics.forEachIndexed { index, s ->
                TagItem(modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .clickable {
                        Log.d("TagItem", "onClick $index")
                    }, text = s
                )
            }
        }
    }
}

@Composable
private fun CustomRow() {
    MyRow(Modifier.background(Color.Green)) {
        Text(text = "Line Count 2\nLine Count 2")
        Text(text = "Line Count 1")
        Text(text = "Line Count 3\nLine Count 3\nLine Count 3")
    }
}

@Composable
private fun CustomCol() {
    MyColumn(modifier = Modifier.background(Color.LightGray)) {
        Text(text = "Line 1: First Line")
        Text(text = "More width than line 1")
        Text(text = "leff Line 1")
    }
}

@Composable
private fun CustomModifierToMargin() {
    Row(modifier = Modifier.fillMaxWidth()) {
        // 此处用了整个屏幕 50% 的空间，若想对比使用 Spacer 将注释内容放开即可。
        /*Column(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .background(Color.LightGray)
        ) {
            Text(text = "with Spacer")
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(text = "MarginText", modifier = Modifier.background(Color.White))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "RightText")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "BottomText")
        }*/
        // 此处为以上 View 剩余的空间的 百分比
        Column(
            modifier = Modifier
                .fillMaxWidth(1F)
                .background(Color.DarkGray)
        ) {
            Text(text = "with Custom Modifier")
            Text(text = "TopText")
            Text(text = "MarginText", modifier = Modifier
                .margin(10.dp, 10.dp, 10.dp, 10.dp)
                .background(Color.White))
            Text(text = "RightText")
            Text(text = "BottomText")
        }
    }
}

@Composable
fun BasicLayoutScreen(name: String, content: @Composable ColumnScope.() -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "This Screen for show $name by Use Compose", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                content()
            }
        }
    }
}
