package com.example.practicecompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import com.example.practicecompose.page.*
import com.example.practicecompose.ui.codelab.margin

class EntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                SampleItem("GoogleSignUpButton") { YouTubeSampleActivity.start(this@EntranceActivity) }
                SampleItem("CodeLab Layout") { LayoutActivity.start(this@EntranceActivity) }
                SampleItem("Animation") { AnimationActivity.start(this@EntranceActivity) }
                SampleItem("Navigation") { NavigationActivity.start(this@EntranceActivity) }
                SampleItem("Component") { ComponentActivity.start(this@EntranceActivity) }
            }
        }
    }
}

@Composable
fun SampleItem(text: String = "", modifier: Modifier = Modifier.margin(top = 10.dp),onClicked: () -> Unit) {
    Button(onClick = onClicked, modifier = modifier) {
        Text(text = text)
    }
}