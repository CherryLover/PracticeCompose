package com.example.practicecompose.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.practicecompose.ui.navigation.TabLayoutScreen

/**
 * @description Compose Component 的实验场，后续可继续新增更多组件的测试
 * @author: Created jiangjiwei in 2021/12/20 8:49 下午
 */
class ComponentActivity: AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ComponentActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabLayoutScreen()
        }
    }
}