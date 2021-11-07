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
import androidx.compose.ui.unit.dp
import com.example.practicecompose.page.LayoutActivity
import com.example.practicecompose.page.NavigationActivity
import com.example.practicecompose.page.YouTubeSampleActivity

class EntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SampleItem("GoogleSignUpButton") { YouTubeSampleActivity.start(this@EntranceActivity) }
                Spacer(modifier = Modifier.height(10.dp))
                SampleItem("CodeLab Layout") { LayoutActivity.start(this@EntranceActivity) }
                Spacer(modifier = Modifier.height(10.dp))
                SampleItem("Navigation") { NavigationActivity.start(this@EntranceActivity) }
            }
        }
    }
}

@Composable
fun SampleItem(text: String = "", onClicked: () -> Unit) {
    Button(onClick = onClicked) {
        Text(text = text)
    }
}