package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicecompose.ui.theme.PracticeComposeTheme
import kotlinx.coroutines.launch
import kotlin.math.max
import com.example.practicecompose.R

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
            PracticeComposeTheme {
                Column {
                    UserInfoCard()
                    ButtonWithIcon()
//                    LayoutHello(text = "Hi There")
                    //        ScaffoldText()
//                    ScrollLayoutList()
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun LayoutPreview() {
    Column {
        UserInfoCard()
//        ButtonWithIcon()
//        LayoutHello(text = "Hi There")
//        ScaffoldText()
//        ScrollLayoutList()
//        TagItem()

//        val topics = listOf("Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary", "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy", "Religion")
//
//        Row(
//            modifier = Modifier
//                .background(color = Color.LightGray)
//                .padding(16.dp)
//                .size(200.dp)
//                .horizontalScroll(rememberScrollState())
//        ) {
//            StaggeredGridLayout(modifier = Modifier) {
//                topics.forEach { tagText ->
//                    TagItem(text = tagText, modifier = Modifier.padding(8.dp))
//                }
//            }
//        }
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


@Composable
fun ScrollLayoutList() {
    val dataList: MutableList<String> = MutableList(100) {
        return@MutableList "OnItem $it"
    }
    val lazyScrollSate = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            val next = if (lazyScrollSate.firstVisibleItemIndex < (dataList.size / 2)) {
                dataList.size - 1
            } else {
                0
            }
            lazyScrollSate.animateScrollToItem(next)
        }
    }) {
        Text(text = "Scroll with Anima")
    }
    Button(onClick = {
        coroutineScope.launch {
            val next = if (lazyScrollSate.firstVisibleItemIndex < (dataList.size / 2)) {
                dataList.size - 1
            } else {
                0
            }
            lazyScrollSate.firstVisibleItemIndex
            lazyScrollSate.scrollToItem(next)
        }
    }) {
        Text(text = "Scroll with fast")
    }
    LazyColumn(state = lazyScrollSate) {
        items(items = dataList) { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                // TODO-JiangJiweiFeat: 2021/3/2 CoilImage Loader not found after add Gradle implementation
//                CoilImage(
//                    data = "https://developer.android.com/images/brand/Android_Robot.png",
//                    contentDescription = "Android Logo",
//                    modifier = Modifier.size(50.dp)
//                )
//                Spacer(Modifier.width(10.dp))
                val clickState = remember { mutableStateOf(false) }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "icon", modifier = Modifier.size(50.dp)
                )
                Text(text = "on RecyclerView $item", Modifier.clickable {
                    clickState.value = !clickState.value
                    coroutineScope.launch {
                        lazyScrollSate.scrollToItem(0, 0)
                    }
                }, color = if (clickState.value) Color.Blue else Color.Red)
            }
        }
    }
}

@Composable
fun ScaffoldText(
    contentLine: List<String> = MutableList(10) {
        return@MutableList "line number $it"
    }
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Scaffold Tab")
            }, modifier = Modifier
                .fillMaxWidth()
                .height(20.dp), actions = {
                IconButton(onClick = {
                    Log.d("onClick", " --> Scaffold Tab Icon")
                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                }
            })/* {
        无用的大括号记得删除，
        > Note: 03-02 06:42 无用的大括号记得删除，否则可能引起编译报错
        }*/
        }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "This is a Scaffold Text Line !!!")
            contentLine.forEach { lineText ->
                Text(text = lineText)
            }
        }
    }
}
// TODO-JiangJiweiFeat: 2021/10/10 More Icon style to cutsom

@Composable
fun ButtonWithIcon() {
    var likeChecked by remember { mutableStateOf(false) }
    var iconResId by remember { mutableStateOf(R.drawable.ic_baseline_favorite_border_24)}
    Button(onClick = {
        likeChecked = !likeChecked
        iconResId = if (likeChecked) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        Log.d("IconButton", "onClick --> IconButton $likeChecked")
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "Like",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Like", fontSize = 14.sp)
        }
    }
}

@Composable
fun UserInfoCard() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .clip(RoundedCornerShape(5.dp))
        .background(MaterialTheme.colors.surface)
        .padding(16.dp)
        .clickable {
            Log.d(LayoutActivity.TAG, "onClick -> UserInfo")
        }) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    Log.d(LayoutActivity.TAG, "onClick -> UserInfo Avatar")
                }, shape = CircleShape, color = Color.Cyan
        ) {
            Image(
                painter = painterResource(id = R.mipmap.avatar_girl_01),
                contentDescription = "Girl Avatar",
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier
            .padding(8.dp)
            .clickable {
                Log.d(LayoutActivity.TAG, "onClick -> UserInfo text")
            }) {
            Text(text = "Andrew Jiang", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun LayoutHello(text: String) {
    Text(
        text = "$text", modifier = Modifier.padding(24.dp), color = MaterialTheme.colors.primary, style = MaterialTheme.typography.body1
    )
}