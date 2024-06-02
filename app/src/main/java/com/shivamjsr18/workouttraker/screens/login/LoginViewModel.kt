package com.shivamjsr18.workouttraker.screens.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamjsr18.workouttraker.isValidEmail
import com.shivamjsr18.workouttraker.isValidPassword
import com.shivamjsr18.workouttraker.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val accountService: AccountService
): ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set


    fun onEmailChange(newEmail:String){
        email= newEmail
    }
    fun onPasswordChange(newPassword:String){
        password= newPassword
    }

    fun onLoginClick(openSettings: ()->Unit, snackbarHostState: SnackbarHostState){
        if(email.isEmpty()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Email is Empty")
            }
        }
        else if(!email.isValidEmail()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Invalid Email")
            }
        }
        else if(password.isEmpty()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Password is Empty")
            }
        }
        else if(!password.isValidPassword()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Invalid Password")
            }
        }
        else{
            viewModelScope.launch {
                accountService.authenticate(email, password)
                openSettings()
            }
        }
    }

    fun onForgetPassClick(openSettings: ()->Unit, snackbarHostState: SnackbarHostState){
        if(email.isEmpty()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Email is Empty")
            }
        }
        else if(!email.isValidEmail()){
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Invalid Email")
            }
        }
        else{
            viewModelScope.launch {
                accountService.sendRecoveryEmail(email)
                openSettings()
            }
        }
    }
}