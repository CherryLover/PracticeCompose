package com.example.practicecompose.ui.stevdza

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.practicecompose.ui.theme.priceColor

/**
 * @description
 * @author: Created jiangjiwei in 2021/9/28 6:49 下午
 */
@Composable
fun SpanString() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = priceColor)) {
                append("$")
                withStyle(style = SpanStyle(fontSize = 30.sp)) {
                    append("19.")
                }
                append("99")
            }
        }
    )
}

@Composable
fun TextLook() {
    Row(modifier = Modifier.background(Color.White)) {
        // 可选择
        SelectionContainer {
            Column {
                Text(
                    text = "Hello Compose ".repeat(30),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                DisableSelection {
                    Text(text = "I Hate no memory for Android Studio")
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomTextPreview() {
    Column {
        SpanString()
        TextLook()
    }
}