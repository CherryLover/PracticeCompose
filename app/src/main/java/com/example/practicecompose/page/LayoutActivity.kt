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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.practicecompose.R
import com.example.practicecompose.ui.codelab.AppBar
import com.example.practicecompose.ui.codelab.ButtonWithIcon
import com.example.practicecompose.ui.codelab.UseTopAppBar
import com.example.practicecompose.ui.codelab.UserInfoCard
import com.example.practicecompose.ui.theme.PracticeComposeTheme
import kotlinx.coroutines.launch
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
            PracticeComposeTheme {
                Column {
                    UserInfoCard()
                    ButtonWithIcon(
                        R.drawable.ic_baseline_near_me_24,
                        R.drawable.ic_baseline_near_me_disabled_24,
                        text = "Location"
                    )
                    AppBar()
//                    LayoutHello(text = "Hi There")
                    //        ScaffoldText()
//                    ScrollLayoutList()
                }
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

