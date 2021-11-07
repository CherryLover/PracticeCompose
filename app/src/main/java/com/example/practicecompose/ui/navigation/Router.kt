package com.example.practicecompose.ui.navigation

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:23 下午
 */
sealed class Router(val router: String) {

    companion object {
        object Main : Router("Main")
        object Feed : Router("Feed")
        object Profile : Router("Profile")
    }
}
