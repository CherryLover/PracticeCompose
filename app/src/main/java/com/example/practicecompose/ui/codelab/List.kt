package com.example.practicecompose.ui.codelab

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
        HorizontalPureList()
        Row {
            VerticalPureList()
            VerticalLazyList()
        }
    }
}

@Composable
fun HorizontalPureList() {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
            .height(48.dp)
    ) {
        repeat(100) {
            ScrollItem(it, item = "Horizontal List Item $it")
        }
    }
}

@Composable
fun VerticalPureList() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(Color.LightGray)
    ) {
        repeat(100) {
            ScrollItem(it, item = "Vertical List Item $it", maxWidth = false)
        }
    }
}

@Composable
fun VerticalLazyList() {
    val dataList: MutableList<String> = MutableList(100) {
        return@MutableList "Vertical LazyList $it"
    }
    val lazyScrollSate = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth()) {
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
        Spacer(modifier = Modifier.height(10.dp))
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
        LazyColumn(state = lazyScrollSate, modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(dataList) { i, item ->
                ScrollItem(i, item, true)
            }
        }
    }
}

val colors = mutableListOf(Color.White, Color.LightGray)

@Composable
private fun ScrollItem(position: Int, item: String, maxWidth: Boolean = false, itemClick: (() -> Unit)? = null) {
    val clickState = remember { mutableStateOf(false) }
    val modifier = if (maxWidth) {
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(if (position % 2 == 0) colors[1] else colors[0])
            .clickable {
                clickState.value = !clickState.value
                itemClick?.invoke()
            }
    } else {
        Modifier
            .height(48.dp)
            .background(if (position % 2 == 0) colors[1] else colors[0])
            .clickable {
                clickState.value = !clickState.value
                itemClick?.invoke()
            }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "icon", modifier = Modifier.size(20.dp)
        )
        Text(text = item, color = if (clickState.value) Color.Blue else Color.Red)
    }
}