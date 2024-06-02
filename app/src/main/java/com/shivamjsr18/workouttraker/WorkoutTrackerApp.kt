package com.shivamjsr18.workouttraker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shivamjsr18.workouttraker.screens.ScreenRoutes
import com.shivamjsr18.workouttraker.screens.exercise.ExerciseScreen
import com.shivamjsr18.workouttraker.screens.home.HomeScreen
import com.shivamjsr18.workouttraker.screens.login.LoginScreen
import com.shivamjsr18.workouttraker.screens.settings.SettingScreen
import com.shivamjsr18.workouttraker.screens.signup.SignUpScreen
import com.shivamjsr18.workouttraker.screens.splash.SplashScreen

@Composable
fun WorkoutTrackerApp() {
    val navHostController: NavHostController = rememberNavController()
    Scaffold {
        NavHost(navController = navHostController, startDestination = ScreenRoutes.SplashScreen, modifier = Modifier.padding(it)) {
            composable(route= ScreenRoutes.SplashScreen){
                val openHomeScreen = {
                    navHostController.navigate(ScreenRoutes.HomeScreen){
                        launchSingleTop=true
                        popUpTo(ScreenRoutes.SplashScreen){inclusive=true}
                    }
                }
                SplashScreen(openHomeScreen = openHomeScreen)
            }
            composable(route = ScreenRoutes.HomeScreen){
                val openSettingScreen = {
                    navHostController.navigate(ScreenRoutes.SettingScreen) {
                        launchSingleTop = true
                    }
                }
                val onMuscleGrpCardClick: (String)->Unit = {muscleGrpName->
                    navHostController.navigate(ScreenRoutes.ExerciseScreen+"/${muscleGrpName}")
                }
                HomeScreen(onMuscleGrpCardClick = onMuscleGrpCardClick, openSetting = openSettingScreen)
            }
            composable(
                route=ScreenRoutes.ExerciseScreen+"/{muscleGrpName}",
                arguments = listOf(navArgument("muscleGrpName"){type= NavType.StringType})
            ){navBackStackEntry->
                val openSettingScreen = {
                    navHostController.navigate(ScreenRoutes.SettingScreen) {
                        launchSingleTop = true
                    }
                }
                val muscleGrpName =navBackStackEntry.arguments?.getString("muscleGrpName")?:"Biceps"
                ExerciseScreen(openSetting = openSettingScreen, muscleGroupName = muscleGrpName)
            }

            composable(route= ScreenRoutes.SettingScreen){
                val openLoginScreen = {
                    navHostController.navigate(ScreenRoutes.LoginScreen) {
                        launchSingleTop = true
                    }
                }
                val openSignUpScreen = {
                    navHostController.navigate(ScreenRoutes.SignUpScreen) {
                        launchSingleTop = true
                    }
                }
                val restartSplash = {
                    navHostController.navigate(ScreenRoutes.SplashScreen) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
                SettingScreen(openLoginScreen = openLoginScreen, openSignUpScreen = openSignUpScreen, restartSplash = restartSplash)
            }

            composable(route= ScreenRoutes.LoginScreen){
                val openSettingScreen = {
                    navHostController.navigate(ScreenRoutes.SettingScreen) {
                        launchSingleTop = true
                        popUpTo(ScreenRoutes.LoginScreen) { inclusive = true }
                    }
                }
                LoginScreen(openSetting = openSettingScreen)
            }

            composable(route= ScreenRoutes.SignUpScreen){
                val openSettingScreen = {
                    navHostController.navigate(ScreenRoutes.SettingScreen) {
                        launchSingleTop = true
                        popUpTo(ScreenRoutes.SignUpScreen) { inclusive = true }
                    }
                }
                SignUpScreen(openSetting = openSettingScreen)
            }
        }


    }
}