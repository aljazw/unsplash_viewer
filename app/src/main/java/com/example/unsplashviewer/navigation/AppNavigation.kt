package com.example.unsplashviewer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unsplashviewer.viewmodel.AuthViewModel
import com.example.unsplashviewer.screens.LoginScreen
import com.example.unsplashviewer.screens.MainScreen
import com.example.unsplashviewer.screens.SignupScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginScreen(navController, authViewModel)
        }
        composable("signup"){
            SignupScreen(navController, authViewModel)
        }
        composable("main"){
            MainScreen(navController, authViewModel)
        }
    })
}