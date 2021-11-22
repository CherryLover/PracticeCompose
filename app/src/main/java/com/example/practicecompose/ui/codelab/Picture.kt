package com.example.practicecompose.ui.codelab

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/10 9:28 下午
 */
const val imageUrl = "http://monster-image-backup.oss-cn-shanghai.aliyuncs.com/dev_/img/avatar/avatar_square.png"

@Composable
fun RemoteImage(url: String, size: Dp = 70.dp, context: Context? = null) {
    Image(painter = rememberImagePainter(data = url,
        builder = {
            placeholder(ColorDrawable(android.graphics.Color.BLACK))
            error(ColorDrawable(android.graphics.Color.RED))
            transformations(CircleCropTransformation())
            if (context != null) {
                transformations(BlurTransformation(context))
            }
            crossfade(true)
        }
    ), contentDescription = "RemoteImage", modifier = Modifier.size(size))
}

@Composable
fun ImageListItem() {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RemoteImage(url = imageUrl, size = 60.dp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Github Avatar", fontSize = 12.sp, color = Color(0xFF333333))
    }
}

@Composable
@Preview
fun PicturePreview() {
    ImageListItem()
}
