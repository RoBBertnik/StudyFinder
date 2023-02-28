package com.example.studyfinder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.model.courseList
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.MyDrawer
import com.example.studyfinder.viewModel.CourseViewModel
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private var viewModel: UserViewModel = UserViewModel()

@Composable
fun CourseSelectionScreen(navController: NavController, userViewModel: UserViewModel? = null, courseViewModel: CourseViewModel) {

    if (userViewModel != null) {
        viewModel = userViewModel
    }

    val scope = rememberCoroutineScope()

    val coroutine1 = Job()
    val coroutineScope1 = CoroutineScope(coroutine1 + Dispatchers.Main)

    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    StudyFinderTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopAppBar(title = { Row() {
                Text(text = "Wählbare Kurse", Modifier.padding(top = 10.dp))
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
                courseList.forEach { course ->
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = course.name,
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.h4,
                        fontSize = 30.sp,
                        modifier = Modifier.clickable {
                                courseViewModel.changeCurrentCourse(course)
                                coroutineScope1.launch {
                                    courseViewModel.getCourseMembers()
                            }
                        }
                    )
                 }
            }
            }
        )
    }
}

@Composable
@Preview
fun CourseSelectionScreenPreview() {
    CourseSelectionScreen(navController = rememberNavController(), courseViewModel = CourseViewModel())

}