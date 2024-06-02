package com.shivamjsr18.workouttraker.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamjsr18.workouttraker.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
):ViewModel() {
    fun onAppStart(openHomeScreen: ()->Unit){
        viewModelScope.launch {
            if(!accountService.hasUser) accountService.createAnonymousAccount()
            openHomeScreen()
        }
    }
}