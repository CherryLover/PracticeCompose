package com.example.practicecompose.ui.codelab

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.practicecompose.R

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/22 8:20 下午
 */

@Preview
@Composable
fun LoginPreview() {
    LoginChoose(false)
}

@Composable
fun LoginChoose(useChain: Boolean = true, guideSpace: Dp = 16.dp) {
    ConstraintLayout {
        val (ivFacebook, ivGoogle, ivPhone) = createRefs()
        FacebookButton(modifier = Modifier.constrainAs(ivFacebook) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        if (useChain) {
            PlatformLogo(R.drawable.ic_google_logo, "Google", modifier = Modifier.constrainAs(ivGoogle) {
                start.linkTo(ivFacebook.start)
                top.linkTo(ivFacebook.bottom, 8.dp)
                end.linkTo(ivPhone.start)
            })
            PlatformLogo(R.drawable.ic_logo_android_phone, "Phone", modifier = Modifier.constrainAs(ivPhone) {
                top.linkTo(ivGoogle.top)
                end.linkTo(ivFacebook.end)
                start.linkTo(ivGoogle.end)
            })
        } else {
            val gl = createGuidelineFromStart(0.5F)
            PlatformLogo(R.drawable.ic_google_logo, "Google", modifier = Modifier.constrainAs(ivGoogle) {
                end.linkTo(gl, guideSpace)
                top.linkTo(ivFacebook.bottom, 8.dp)
            })
            PlatformLogo(R.drawable.ic_logo_android_phone, "Phone", modifier = Modifier.constrainAs(ivPhone) {
                start.linkTo(gl, guideSpace)
                top.linkTo(ivGoogle.top)
            })
        }
    }
}

@Composable
fun PlatformLogo(@DrawableRes resId: Int = R.drawable.ic_google_logo, contentDesc: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = resId), contentDescription = contentDesc, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun FacebookButton(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
            .background(color = Color(0xFF527BFF), RoundedCornerShape(50))
            .padding(horizontal = 19.dp, vertical = 8.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_logo_facebook), contentDescription = "Facebook", modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "Sign in with Facebook", color = Color.White)
    }
}
