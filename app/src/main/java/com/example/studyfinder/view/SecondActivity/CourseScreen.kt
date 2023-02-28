package com.example.studyfinder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.model.Course
import com.example.studyfinder.model.User
import com.example.studyfinder.model.testPerson
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.MyDrawer
import com.example.studyfinder.view.Screen
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.*

@Composable
fun CourseScreen(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {

    val scope = rememberCoroutineScope()

    val coroutine1 = Job()
    val coroutineScope1 = CoroutineScope(coroutine1 + Dispatchers.Main)


    val thisCourse: Course = courseViewModel.currentCourse!!

    var joinedCourse = userViewModel.partOfCourse(thisCourse)

    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    StudyFinderTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopAppBar(title = { Row() {
                Text(text = thisCourse.name, Modifier.padding(top = 10.dp))
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = courseViewModel.getPictureForCourse()),
                    contentDescription = "Kurssymbol",
                    modifier = Modifier.padding(top = 15.dp, end =15.dp))
            }
            },
                backgroundColor = MaterialTheme.colors.primary) },
            drawerContent = {
                MyDrawer(onDestinationClicked = { route ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(route)
                })
            },
            content = { padding -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Text(text = "Current Members:", fontSize = 20.sp, color = MaterialTheme.colors.surface)
                courseViewModel.users.value!!.forEach { member ->
                    Text(text = member.username, fontSize = 15.sp, color = MaterialTheme.colors.surface, modifier = Modifier.clickable {
                        userViewModel.setOtherUser(member)
                        navController.navigate(Screen.UserScreen.route)
                    })
                }

                Column {
                    Button(onClick = {
                        if(joinedCourse){
                            userViewModel.viewModelScope.launch {
                                userViewModel.removeCourse(thisCourse)
                            }
                        }
                        else{
                            userViewModel.viewModelScope.launch {
                                userViewModel.addCourse(thisCourse)
                            }
                        }
                        coroutineScope1.launch {
                            courseViewModel.getCourseMembers()
                        }
                        joinedCourse = joinedCourse.not()
                    }) {
                        if(joinedCourse){
                            Text(text = "Leave?")
                        }
                        else{
                            Text(text = "Join!")
                        }

                    }
                }
            }
            }
        )
    }
}


@Composable
@Preview
fun CourseScreenPreview() {
    val previewCourseViewModel = CourseViewModel()
    previewCourseViewModel.changeCurrentCourse(Course.MoCo)
    val previewUserViewModel = UserViewModel()
    previewUserViewModel.setCurrentUser(testPerson)
    CourseScreen(
        navController = rememberNavController(),
        userViewModel = previewUserViewModel,
        courseViewModel = previewCourseViewModel
    )
}