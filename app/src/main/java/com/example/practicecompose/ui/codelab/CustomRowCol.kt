package com.example.practicecompose.ui.codelab

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/22 3:06 下午
 */
@Composable
fun MyColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier = modifier) { measureable, constraints ->
        var maxWidth = 0
        var allHeight = 0
        val placeables = measureable.map {
            val element = it.measure(constraints = constraints)
            if (element.width > maxWidth) {
                maxWidth = element.width
            }
            allHeight += element.height
            element
        }
        var y = 0
        layout(maxWidth, allHeight) {
            placeables.forEach {
                it.placeRelative(0, y)
                y += it.height
            }
        }
    }
}

@Composable
fun MyRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier = modifier) { measureables, constraints ->
        var maxHeight = 0
        var allWidth = 0
        val map = measureables.map {
            val element = it.measure(constraints = constraints)
            if (element.height > maxHeight) {
                maxHeight = element.height
            }
            allWidth += element.width
            element
        }
        layout(allWidth, maxHeight) {
            var x = 0
            map.forEach {
                it.placeRelative(x, 0)
                x += it.width
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
fun StaggeredGridLayout(modifier: Modifier = Modifier, rows: Int = 3, content: @Composable() () -> Unit) {
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
fun TagItem(modifier: Modifier = Modifier, text: String = "Hi Compose") {
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
