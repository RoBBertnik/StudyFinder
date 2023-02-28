package com.example.studyfinder

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.Screen
import com.example.studyfinder.view.TextFieldWithHeadline
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.launch


private var viewModel: UserViewModel = UserViewModel()
@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel? = null) {
    if (userViewModel != null) {
        viewModel = userViewModel
    }

    val textGenerator = TextFieldWithHeadline()
    textGenerator.setViewModel(viewModel)

    val context = LocalContext.current
    val intent = Intent(context, SecondActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


    StudyFinderTheme {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 10.dp, title = {
                    Row() {
                        Text(text = "StudyFinder", Modifier.padding(top = 10.dp))
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = { login(context, intent) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant),
                            modifier = Modifier.fillMaxHeight()) {
                            Text(text = "Login", fontSize = 15.sp)

                        }
                    }
                }, backgroundColor = MaterialTheme.colors.primary)
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "E-Mail Adresse")
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "Password", Modifier, true)
                    Button(
                        modifier = Modifier.height(40.dp),
                        onClick = { navController.navigate(route = Screen.Register.route) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant)) {
                        Text(text = "Registrieren?", fontSize = 16.sp, color = MaterialTheme.colors.surface)
                    }
                }
            },
        )
    }
}

fun login(context: Context, intent: Intent){
    viewModel.viewModelScope.launch {
        val valid = viewModel.checkValid()
        if(valid) {
            Log.e("AlmostAtMainMenu", viewModel.currentUser!!.firstName)
            viewModel.goToMainMenuActivity(context, intent)
        }
    }
}



@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        userViewModel = null
    )
}