package com.example.studyfinder

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyfinder.ui.theme.StudyFinderTheme
import com.example.studyfinder.view.TextFieldWithHeadline
import com.example.studyfinder.viewModel.UserViewModel
import kotlinx.coroutines.launch


private var viewModel: UserViewModel = UserViewModel()
@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel? = null) {

    if (userViewModel != null) {
        viewModel = userViewModel
    }
    val textGenerator = TextFieldWithHeadline()
    textGenerator.setViewModel(viewModel)

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    var intent = Intent(context, SecondActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    StudyFinderTheme {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 10.dp, title = {
                    Row() {
                        Text(text = "Registrieren", Modifier.padding(top = 10.dp))
                    }
                }, backgroundColor = MaterialTheme.colors.primary)
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "E-Mail Adresse")
                    textGenerator.TextFieldWithHeadlineAndSpacer(
                        headline = "Password",
                        Modifier,
                        true
                    )
                    textGenerator.TextFieldWithHeadlineAndSpacer(
                        headline = "Password bestätigen",
                        Modifier,
                        true
                    )
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "Vorname")
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "Nachname")
                    textGenerator.TextFieldWithHeadlineAndSpacer(headline = "Username")

                    Button(
                        modifier = Modifier.height(40.dp),
                        onClick = { register(context, intent) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant))
                    {
                        Text(text = "Account erstellen!", fontSize = 16.sp)
                    }
                }
            },
        )
    }
}

fun register(context: Context, intent: Intent){
    Log.e("SIGNUPSTART", "YES")
    viewModel.computeCurrentUser()
    viewModel.viewModelScope.launch {
        val valid = viewModel.signUp()
        if(valid) {
            viewModel.goToMainMenuActivity(context, intent)
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    RegisterScreen(
        navController = rememberNavController(),
        null
    )

}