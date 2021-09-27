package com.example.practicecompose.ui.stevdza

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.R

/**
 * @description
 * @author: Created jiangjiwei in 2021/9/26 9:17 下午
 */
@Composable
fun GoogleSignUpButton() {
    var clicked by remember { mutableStateOf(false) }
    Surface(shape = RoundedCornerShape(50), border = BorderStroke(0.5.dp, Color.Gray)) {
        Row(
            Modifier
                .padding(12.dp, 12.dp)
                .clickable {
                    clicked = !clicked
                }.animateContentSize(tween(300, easing = LinearEasing)),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Logo",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) "Create Account..." else "SignUp With Google")
            if (clicked) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
            }
        }
    }

}

@Preview
@Composable
fun GoogleSignUpButtonPreview() {
    GoogleSignUpButton()
}