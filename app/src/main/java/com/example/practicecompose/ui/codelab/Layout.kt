package com.example.practicecompose.ui.codelab

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicecompose.R
import com.example.practicecompose.page.LayoutActivity

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/6 1:22 下午
 */
@Composable
fun ButtonWithIcon(
    checkIconId: Int = R.drawable.ic_baseline_favorite_24,
    unCheckIconId: Int = R.drawable.ic_baseline_favorite_border_24,
    iconTintColor: Color = Color.Unspecified,
    text: String = "Like",
    onClick: (() -> Unit)? = null
) {
    var likeChecked by remember { mutableStateOf(false) }
    var iconResId by remember { mutableStateOf(unCheckIconId) }
    Button(onClick = {
        likeChecked = !likeChecked
        iconResId = if (likeChecked) {
            checkIconId
        } else {
            unCheckIconId
        }
        Log.d("IconButton", "onClick --> IconButton $likeChecked")
        onClick?.invoke()
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                tint = iconTintColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, fontSize = 14.sp)
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
@Preview(showBackground = false)
@Composable
fun LayoutPreview() {
    Column {
        ButtonWithIcon()
        UserInfoCard()
    }
}