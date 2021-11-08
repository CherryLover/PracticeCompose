package com.example.practicecompose.ui.navigation

/**
 * @description
 * @author: Created jiangjiwei in 2021/11/7 6:23 下午
 */
sealed class Router(val router: String) {

    open fun finalRouter(): String {
        return router
    }

    open fun argRouter(count: Int): String {
        return router
    }

    companion object {
        object Main : Router("Main")
        object Feed : Router("Feed") {
            internal const val KEY_NECESSARY_COUNT = "necessary_count"
            override fun finalRouter(): String {
                return "$router/{$KEY_NECESSARY_COUNT}"
            }

            override fun argRouter(count: Int): String {
                return "$router/$count"
            }
        }

        object Profile : Router("Profile") {
            internal const val KEY_OPTIONAL_COUNT = "optional_count"
            override fun finalRouter(): String {
                return "$router?$KEY_OPTIONAL_COUNT={$KEY_OPTIONAL_COUNT}"
            }

            override fun argRouter(count: Int): String {
                return "$router?$KEY_OPTIONAL_COUNT=$count"
            }
        }
    }
}
