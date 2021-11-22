package com.example.practicecompose.ui.codelab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/22 9:21 下午
 */
@Composable
fun BasicLayoutScreen(name: String, backgroundColor: Color = Color.White, content: @Composable ColumnScope.() -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(backgroundColor)) {
        Text(
            text = "This Screen for show $name by Use Compose",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                content()
            }
        }
    }
}