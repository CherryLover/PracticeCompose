package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practicecompose.ui.stevdza.GoogleSignUpButton
import com.example.practicecompose.ui.stevdza.GradientButton

class YouTubeSampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GoogleSignUpActivity"
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, YouTubeSampleActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                GoogleSignUpButton()
                Spacer(modifier = Modifier.height(4.dp))
                GradientButton {
                    Log.d(TAG, "Click Gradient Button")
                }
            }
        }
    }
}