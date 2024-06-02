package com.shivamjsr18.workouttraker.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shivamjsr18.workouttraker.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    openLoginScreen: ()->Unit,
    openSignUpScreen: ()->Unit,
    restartSplash: ()->Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Settings")})
        }
    ) {paddingValues->
        Column(
            modifier = Modifier
                .padding((paddingValues))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            val currentUser by viewModel.currentUser.collectAsState(initial = User())
            if(currentUser.isAnonymous){
                LoginCard { viewModel.onLoginClick(openLoginScreen) }
                SignupCard {viewModel.onSingUpClick(openSignUpScreen)}
            }
            else{
                SignOutCard { viewModel.onSignOutClick(restartSplash)}
                DeleteAccCard { viewModel.onDeleteClick(restartSplash)}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginCard(onLoginClick: ()->Unit){
    Card(onClick = { onLoginClick() },
        modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ){
                Text(text = "Login to your account", fontSize = 24.sp)
            }
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Login")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupCard(onSignupClick: ()->Unit){
    Card(onClick = { onSignupClick() },
        modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ){
                Text(text = "Create an account", fontSize = 24.sp)
            }
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Signup")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutCard(onSignOutClick: ()->Unit){

    var isShowingAlert by remember { mutableStateOf(false) }

    Card(onClick = { isShowingAlert=true },
        modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ){
                Text(text = "Sign-Out from my account", fontSize = 24.sp)
            }
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Signout")
        }
    }

    if(isShowingAlert)
    {
        AlertDialog(
            text = { Text(text = "Are you sure you want to Sign Out?")},
            confirmButton = { TextButton(onClick = { onSignOutClick() }) {
                Text(text = "Sign Out")
            } },
            dismissButton = { TextButton(onClick = { isShowingAlert=false }) {
                Text(text = "Cancel")
            }},
            onDismissRequest = { isShowingAlert=false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccCard(onDeleteClick: ()->Unit){
    var isShowingAlert by remember { mutableStateOf(false) }

    Card(onClick = { isShowingAlert=true },
        modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ){
                Text(text = "Delete my account", fontSize = 24.sp)
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }

    if(isShowingAlert)
    {
        AlertDialog(
            text = { Text(text = "Are you sure you want to Delete your account?")},
            confirmButton = { TextButton(onClick = { onDeleteClick() }) {
                Text(text = "Delete")
            } },
            dismissButton = { TextButton(onClick = { isShowingAlert=false }) {
                Text(text = "Cancel")
            }},
            onDismissRequest = { isShowingAlert=false })
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        SignOutCard({})
        DeleteAccCard({})
    }
}