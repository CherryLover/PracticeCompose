package com.example.practicecompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.ui.theme.PracticeComposeTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeComposeTheme {
                GreetingTheme(name = "Hello World")
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    PracticeComposeTheme {
        Surface(color = Color.Cyan) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: MutableList<String> = mutableListOf("Android", "iOS", "Windows")) {
    repeat(1000) {
        names.add("mock item with index ${it + 1}")
    }
    val countState = remember { mutableStateOf(-1) }
    Column(modifier = Modifier.fillMaxHeight()) {
        NameList(names = names, modifier = Modifier.weight(1F))
        Divider(color = Color.Transparent, startIndent = 10.dp)

        Counter(countState.value) { newCount ->
            countState.value = newCount
        }
    }
}

@Composable
fun NameList(names: List<String>,modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names, itemContent = { name ->
            Greeting(name = name)
            Divider()
        })
    }
}

@Composable
fun Counter(count: Int = 0, onCountUpdate:(newCount: Int) -> Unit) {
    Button(onClick = { onCountUpdate(count + 1) },
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (count > 5) Color.Green else Color.White
            )
    ) {
        Text(text = "Click count $count")
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember{ mutableStateOf(false) }
    val bgColor by animateColorAsState(if (isSelected) Color.Red else Color.White)
    Text(text = "Hello $name!"
        , modifier = Modifier
            .padding(24.dp)
            .background(color = bgColor)
            .clickable {
                isSelected = !isSelected
            })
}

@Composable
fun GreetingTheme(name: String) {
    Text(text = "Hello $name!"
        , modifier = Modifier.padding(24.dp)
        , color = MaterialTheme.colors.primary
        , style = MaterialTheme.typography.h1)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}