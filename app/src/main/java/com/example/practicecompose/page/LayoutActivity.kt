package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import kotlin.math.max

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

/**
 * 如何自定义 Layout
 * 通过 Layout 方法的 measurables 进行逐个测量，测量的为 items 的宽高
 * 根据 Item 的摆放规则，计算得出目标摆放方式下的尺寸信息
 *
 * 以上相当于 ViewGroup#onMeasure
 * 以下相当于 ViewGroup#onLayout 方法，不过最终设置到 ViewGroup 的尺寸信息是在 onMeasure 方法中，而非在 layout 方法中进行的设置
 *
 * 遍历 Items 进行摆放
 */
@Composable
fun StaggeredGridLayout(modifier: Modifier, rows: Int = 3, content: @Composable() () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth
        val height = rowHeights.sumBy { it }.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width = width, height = height) {
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(x = rowX[row], y = rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun TagItem(modifier: Modifier, text: String = "Hi Compose") {
    Card(
        modifier = modifier, shape = MaterialTheme.shapes.small, border = BorderStroke(Dp.Hairline, Color.Black)
    ) {
        Row(modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(MaterialTheme.colors.primary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, style = MaterialTheme.typography.body1)
        }
    }
}

