package com.example.studyfinder.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.studyfinder.viewModel.UserViewModel

class TextFieldWithHeadline() {
    private lateinit var myViewModel: UserViewModel

    fun setViewModel(viewModel: UserViewModel){
        myViewModel = viewModel
    }

    @Composable
    fun TextFieldWithHeadlineAndSpacer(
        headline: String,
        modifier: Modifier = Modifier,
        password: Boolean = false
    ) {
        var input by remember {
            mutableStateOf("")
        }
        Column(modifier) {
            Text(text = headline, color = MaterialTheme.colors.surface)
            if (password)
                OutlinedTextField(
                    value = input, onValueChange = { text ->
                        input = text
                    }, visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )
            else {
                OutlinedTextField(value = input, onValueChange = { text ->
                    input = text
                }, colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface))
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
        if(headline.equals("E-Mail Adresse")){
            myViewModel.setEmail(input)
        }
        if(headline.equals("Password")){
            myViewModel.setPassword(input)
        }
        if(headline.equals("Password bestätigen")){
            myViewModel.setConfirmedPassword(input)
        }
        if(headline.equals("Vorname")){
            myViewModel.setFirstName(input)
        }
        if(headline.equals("Nachname")){
            myViewModel.setLastName(input)
        }
        if(headline.equals("Username")){
            myViewModel.setUsername(input)
        }
    }
}