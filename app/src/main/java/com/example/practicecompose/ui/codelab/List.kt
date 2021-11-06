package com.example.practicecompose.ui.codelab

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.R
import kotlinx.coroutines.launch

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/6 1:30 下午
 */

@Preview
@Composable
fun ListPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ScrollLayoutList()
    }
}

@Composable
fun ScrollLayoutList() {
    val dataList: MutableList<String> = MutableList(100) {
        return@MutableList "OnItem $it"
    }
    val lazyScrollSate = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            val next = if (lazyScrollSate.firstVisibleItemIndex < (dataList.size / 2)) {
                dataList.size - 1
            } else {
                0
            }
            lazyScrollSate.animateScrollToItem(next)
        }
    }) {
        Text(text = "Scroll with Animation")
    }
    Button(onClick = {
        coroutineScope.launch {
            val next = if (lazyScrollSate.firstVisibleItemIndex < (dataList.size / 2)) {
                dataList.size - 1
            } else {
                0
            }
            lazyScrollSate.firstVisibleItemIndex
            lazyScrollSate.scrollToItem(next)
        }
    }) {
        Text(text = "Scroll with fast")
    }
    LazyColumn(state = lazyScrollSate) {
        items(items = dataList) { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                val clickState = remember { mutableStateOf(false) }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "icon", modifier = Modifier.size(50.dp)
                )
                Text(text = "on RecyclerView $item", Modifier.clickable {
                    clickState.value = !clickState.value
                    coroutineScope.launch {
                        lazyScrollSate.scrollToItem(0, 0)
                    }
                }, color = if (clickState.value) Color.Blue else Color.Red)
            }
        }
    }
}