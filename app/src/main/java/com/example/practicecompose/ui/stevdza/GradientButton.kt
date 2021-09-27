package com.example.practicecompose.ui.stevdza

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.ui.theme.gradientColor1
import com.example.practicecompose.ui.theme.gradientColor2
import com.example.practicecompose.ui.theme.gradientColor3

/**
 * @description
 * @author: Created jiangjiwei in 2021/9/27 1:09 下午
 */
@Composable
fun GradientButton(
    text: String = "Simple Gradient Button",
    textColor: Color = Color.White,
    colors: List<Color> = listOf(
        gradientColor1,
        gradientColor2,
        gradientColor3
    ),
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(colors = colors))
                .padding(16.dp, 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton {

    }
}