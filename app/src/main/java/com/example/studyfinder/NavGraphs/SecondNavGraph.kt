package com.example.studyfinder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studyfinder.view.Screen
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel


@Composable
fun SetupNavGraph2(
    navController: NavHostController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
) {
    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController, userViewModel, courseViewModel)
        }
        composable(Screen.CourseScreen.route){
            CourseScreen(navController, userViewModel, courseViewModel)
        }
        composable(Screen.CourseSelection.route){
            CourseSelectionScreen(navController, userViewModel, courseViewModel)
        }
        composable(Screen.UserScreen.route){
            UserScreen(navController, userViewModel, courseViewModel)
        }
    }
}