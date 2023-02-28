package com.example.studyfinder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.model.Course
import com.example.studyfinder.model.testPerson
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.MyDrawer
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MainMenuScreen(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {

    val scope = rememberCoroutineScope()

    val coroutine1 = Job()
    val coroutineScope1 = CoroutineScope(coroutine1 + Dispatchers.Main)

    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    StudyFinderTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Row() {
                            Text(
                                text = "Hallo, ${userViewModel.firstName}!",
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
                    userViewModel.setOtherUser(userViewModel.currentUser!!)
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
                    Text(text = "Deine Kurse:", fontSize = 25.sp, color = MaterialTheme.colors.surface)
                    userViewModel.currentUser!!.courses.forEach { course ->

                        val realCourse = courseViewModel.getCourseByString(course)
                        Spacer(modifier = Modifier.height(30.dp))

                            Row {
                                Button(
                                    modifier = Modifier.height(40.dp),
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant),
                                    onClick = {
                                        courseViewModel.changeCurrentCourse(realCourse)
                                        coroutineScope1.launch {
                                            courseViewModel.getCourseMembers()
                                        }
                                    }) {
                                    Text(text = course, fontSize = 16.sp)
                                }
                        }
                    }
                }
            },
        )
    }
}

@Composable
@Preview
fun MainMenuScreenPreview() {
    val previewViewModel = UserViewModel()
    previewViewModel.setCurrentUser(testPerson)
    previewViewModel.computeParameters()
    testPerson.courses.add(Course.Mathe_1.name)
    testPerson.courses.add(Course.MoCo.name)
    MainMenuScreen(
        navController = rememberNavController(),
        userViewModel = previewViewModel,
        courseViewModel = CourseViewModel()
    )
}