package com.shivamjsr18.workouttraker.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamjsr18.workouttraker.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {
    val currentUser=accountService.currentUser

    fun onLoginClick(openLoginScreen: ()->Unit){
        openLoginScreen()
    }
    fun onSingUpClick(openSignUpScreen: ()->Unit){
        openSignUpScreen()
    }
    fun onDeleteClick(restartSplash: ()->Unit){
        viewModelScope.launch {
            accountService.deleteAccount()
            restartSplash()
        }
    }
    fun onSignOutClick(restartSplash: ()->Unit){
        viewModelScope.launch {
            accountService.signOut()
            restartSplash()
        }
    }
}