package com.example.studyfinder.view

sealed class Screen(val route: String){
    object Login: Screen(route = "login_screen")
    object Register: Screen(route = "register_screen")
    object MainMenu: Screen(route = "mainmenu_screen")
    object CourseScreen: Screen(route = "course_screen")
    object CourseSelection: Screen(route = "courseselection_screen")
    object UserScreen: Screen(route = "user_screen")
}
