package com.shivamjsr18.workouttraker.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shivamjsr18.workouttraker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    openSetting: ()->Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val snackbarHostState = remember {SnackbarHostState()}

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(title = { Text(text = "Login")})
        }
    ){paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 54.dp),
                value = viewModel.email,
                onValueChange ={viewModel.onEmailChange(it)},
                singleLine = true,
                label = { Text(text = "Email")},
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            var isVisible by remember { mutableStateOf(false) }
            val icon =
                if(isVisible) painterResource(id = R.drawable.baseline_visibility_24)
                else painterResource(id = R.drawable.baseline_visibility_off_24)
            val visualTransformation =
                if(isVisible) VisualTransformation.None
                else PasswordVisualTransformation()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 54.dp),
                value = viewModel.password,
                onValueChange ={viewModel.onPasswordChange(it)},
                singleLine = true,
                label = { Text(text = "Password")},
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password") },
                trailingIcon = { IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(painter=icon, contentDescription="Show/Hide Password")
                }},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password),
                visualTransformation = visualTransformation
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { viewModel.onForgetPassClick(openSetting, snackbarHostState) },
                    modifier = Modifier.padding(end=44.dp)) {
                    Text(text = "Forgot Password", textDecoration = TextDecoration.Underline)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.onLoginClick(openSetting,snackbarHostState) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 54.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun LoginPreview(){
    LoginScreen({})
}