package com.example.practicecompose.ui.codelab

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/22 1:36 下午
 */
fun Modifier.margin(left: Dp = 0.dp, top: Dp = 0.dp, right: Dp = 0.dp, bottom: Dp = 0.dp) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val ml = left.roundToPx()
        val mt = top.roundToPx()
        val mr = right.roundToPx()
        val mb = bottom.roundToPx()

        val height = placeable.height + mt + mb
        val width = placeable.width + ml + mr
        layout(width = width, height = height) {
            placeable.placeRelative(ml, mt)
        }
    }
)