package com.example.studyfinder.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studyfinder.R
import com.example.studyfinder.ui.theme.StudyFinderTheme

sealed class DrawerScreens(val title: String, val screen: Screen){

    object Home: DrawerScreens("Home", Screen.MainMenu)
    object CourseSelection: DrawerScreens("Kurse", Screen.CourseSelection)
    object UserScreen: DrawerScreens("Profil", Screen.UserScreen)


}

private val drawerScreens = listOf(
    DrawerScreens.Home,
    DrawerScreens.CourseSelection,
    DrawerScreens.UserScreen
)

@Composable
fun MyDrawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    StudyFinderTheme {
        Column(
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(start = 24.dp, top = 48.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_android_black_24dp),
                contentDescription = "App icon"
            )
            drawerScreens.forEach { drawerScreen ->
                Spacer(Modifier.height(24.dp))
                Text(
                    text = drawerScreen.title,
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.clickable {
                        onDestinationClicked(drawerScreen.screen.route)
                    }
                )
            }
        }
    }
}