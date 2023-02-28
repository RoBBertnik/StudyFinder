package com.example.studyfinder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyfinder.model.User
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.MyDrawer
import com.example.studyfinder.view.Screen
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun UserScreen(navController: NavHostController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {

    val profileOwner: User = userViewModel.otherUser!!

    val scope = rememberCoroutineScope()


    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    StudyFinderTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Row() {
                            Text(
                                text = "${profileOwner.username}'s Profil",
                                Modifier.padding(top = 10.dp)
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                )
            },
            drawerContent = {
                MyDrawer(onDestinationClicked = { route ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(route)
                })
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = profileOwner.firstName + " " + profileOwner.lastName)
                    Text(text = profileOwner.email)
                    profileOwner.courses.forEach{ course ->
                        Text(text = course, modifier = Modifier.clickable {
                            courseViewModel.changeCurrentCourse(courseViewModel.getCourseByString(course))
                            navController.navigate(Screen.CourseScreen.route)
                        })
                }
            }
    })
}
}