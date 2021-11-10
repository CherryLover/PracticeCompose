package com.example.practicecompose.ui.navigation

import com.example.practicecompose.R

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

    object NewsContainer : Router("NewsContainer")
    object News : Router("News")
    object Explore : Router("Explore")
    object Favorite : Router("Favorite")

    object LayoutMain: Router("Layout_Main")
    object LayoutUserInfo: Router("Layout_UserInfo")
    object LayoutCheckedButtonWithIcon: Router("Layout_CheckedButtonWithIcon")
    object LayoutSlot: Router("Layout_Slot")
    object LayoutList: Router("Layout_List")

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

class NewsContainerBottomNavigateItem(
    val name: String,
    val normalIconId: Int,
    val router: String,
) {
    companion object {
        val list = mutableListOf(
            NewsContainerBottomNavigateItem("News", R.drawable.ic_baseline_android_24, Router.News.finalRouter()),
            NewsContainerBottomNavigateItem("Explore", R.drawable.ic_baseline_near_me_24, Router.Explore.finalRouter()),
            NewsContainerBottomNavigateItem("Favorite", R.drawable.ic_baseline_favorite_24, Router.Favorite.finalRouter())
        )
    }
}
