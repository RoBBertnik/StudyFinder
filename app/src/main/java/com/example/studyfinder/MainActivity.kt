package com.example.studyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyFinderTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }

    }


}



