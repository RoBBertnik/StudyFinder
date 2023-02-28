package com.example.studyfinder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studyfinder.view.Screen
import com.example.studyfinder.viewModel.UserViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(Screen.Login.route){
            LoginScreen(navController, viewModel)
        }
        composable(Screen.Register.route){
            RegisterScreen(navController, viewModel)
        }
    }
}