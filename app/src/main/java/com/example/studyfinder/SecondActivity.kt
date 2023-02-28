package com.example.studyfinder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.model.Connectivity
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.Screen
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel

class SecondActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    private val userViewModel = UserViewModel()
    private val courseViewModel = CourseViewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel.setEmail(intent.getStringExtra("Email")!!)
        userViewModel.setFirstName(intent.getStringExtra("FirstName")!!)
        userViewModel.setLastName(intent.getStringExtra("LastName")!!)
        userViewModel.setUsername(intent.getStringExtra("Username")!!)
        userViewModel.computeCurrentUser()
        userViewModel.currentUser!!.courses = (intent.getStringArrayListExtra("Courses")!!.toMutableList())
        userViewModel.setOtherUser(userViewModel.currentUser!!)




        setObserver()

        Log.e("ViewModelSecondFragment", userViewModel.currentUser!!.firstName)

        setContent{
            StudyFinderTheme {
                navController = rememberNavController()
                SetupNavGraph2(navController = navController, userViewModel = userViewModel, courseViewModel)

            }
        }

    }

    override fun onBackPressed() {

    }

    fun setObserver(){
        courseViewModel.users.observe(this){ it ->
            let {
                Log.e("ObserverForUsers", "1")
                navController.navigate(Screen.CourseScreen.route) }
        }
    }
}

