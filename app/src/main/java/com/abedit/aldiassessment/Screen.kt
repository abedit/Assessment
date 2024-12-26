package com.abedit.aldiassessment

/*For the 2 screens, more can be added if necessary*/
sealed class Screen(val route: String) {
    data object List: Screen("list_screen")
    data object Details: Screen("detail_screen")
}