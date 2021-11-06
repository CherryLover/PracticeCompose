package com.example.practicecompose.ui.codelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.R

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/6 1:18 下午
 */
@Preview
@Composable
fun Preview() {
    ScaffoldText()
}

@Composable
fun ScaffoldText(
    contentLine: List<String> = MutableList(10) {
        return@MutableList "line number $it"
    }
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Scaffold Tab")
            }, modifier = Modifier
                .fillMaxWidth(), actions = {
                IconButton(onClick = {
                    Log.d("onClick", " --> Scaffold Tab Icon")
                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                }
            }, navigationIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_menu_24), contentDescription = "menu")
            })
        }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "This is a Scaffold Text Line !!!")
            contentLine.forEach { lineText ->
                Text(text = lineText)
            }
        }
    }
}